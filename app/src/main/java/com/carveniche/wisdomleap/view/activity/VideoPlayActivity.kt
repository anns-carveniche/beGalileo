package com.carveniche.wisdomleap.view.activity

import android.app.Dialog
import android.content.pm.ActivityInfo
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat

import com.carveniche.wisdomleap.R
import com.carveniche.wisdomleap.adapter.ChapterVideoGridAdapter
import com.carveniche.wisdomleap.contract.VideoPlayContract
import com.carveniche.wisdomleap.di.component.DaggerActivityComponent
import com.carveniche.wisdomleap.di.module.ActivityModule
import com.carveniche.wisdomleap.di.module.ContextModule
import com.carveniche.wisdomleap.di.module.SharedPreferenceModule
import com.carveniche.wisdomleap.interfaces.IChapterVideoClickListener
import com.carveniche.wisdomleap.model.ChapterVideosModel
import com.carveniche.wisdomleap.model.MySharedPreferences
import com.carveniche.wisdomleap.util.Constants
import com.carveniche.wisdomleap.util.showLoadingProgress
import com.carveniche.wisdomleap.util.showLongToast
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.TrackGroupArray

import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.ui.*
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util

import kotlinx.android.synthetic.main.activity_video_play.*
import kotlinx.android.synthetic.main.layout_progressbar.*
import javax.inject.Inject


class VideoPlayActivity : AppCompatActivity(),VideoPlayContract.View,IChapterVideoClickListener {



    @Inject lateinit var presenter : VideoPlayContract.Presenter
    @Inject lateinit var mySharedPreferences: MySharedPreferences
    private var mExoPlayerFullscreen = false

    private lateinit var mExoPlayerView : PlayerView
    private lateinit var mFullScreenButton: FrameLayout
    private lateinit var mFullScreenIcon: ImageView

    private var mResumeWindow: Int = 0
    private var mResumePosition: Long = 0
    private lateinit var mVideoSource: MediaSource

    private val STATE_RESUME_WINDOW = "resumeWindow"
    private val STATE_RESUME_POSITION = "resumePosition"
    private val STATE_PLAYER_FULLSCREEN = "playerFullscreen"
    private lateinit var mFullScreenDialog: Dialog

    private var conceptId = 0
    private var courseId = 0
    private var subConceptId = 0
    private var mVideoUrl = ""
    private var mStudentId = 0
    private var mVideoTitle = ""
    private var isVideoCompleted = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_play)
        injectDependency()
        presenter.attach(this)
        presenter.subscribe()
        if (savedInstanceState != null) {
            Log.d(Constants.LOG_TAG,"Inside Save")
            mResumeWindow = savedInstanceState.getInt(STATE_RESUME_WINDOW)
            mResumePosition = savedInstanceState.getLong(STATE_RESUME_POSITION)
            mExoPlayerFullscreen = savedInstanceState.getBoolean(STATE_PLAYER_FULLSCREEN)
        }


    }
    override fun onChapterClick(conceptId: Int, subconceptId: Int, videoUrl: String, videoTitle: String) {

    }
    override fun onSaveInstanceState(outState: Bundle?) {
        Log.d(Constants.LOG_TAG,"Outside Save $mResumeWindow -- $mResumePosition")
        outState!!.putInt(STATE_RESUME_WINDOW, mResumeWindow)
        outState.putLong(STATE_RESUME_POSITION, mResumePosition)
        outState.putBoolean(STATE_PLAYER_FULLSCREEN, mExoPlayerFullscreen)
        super.onSaveInstanceState(outState)
    }


    override fun showProgress(show: Boolean) {
        showLoadingProgress(progressBar,show)
    }
    private fun injectDependency() {
        val activityComponent = DaggerActivityComponent.builder()
            .activityModule(ActivityModule(this))
            .sharedPreferenceModule(SharedPreferenceModule())
            .contextModule(ContextModule(this))
            .build()
        activityComponent.inject(this)
    }
    override fun onStart() {
        super.onStart()
        conceptId = intent.getIntExtra(Constants.CONCEPT_ID,0)
        courseId = intent.getIntExtra(Constants.COURSE_ID,0)
        subConceptId = intent.getIntExtra(Constants.SUB_CONCEPT_ID,0)
        mVideoUrl = intent.getStringExtra(Constants.VIDEO_URL)
        mStudentId = mySharedPreferences.getIntData(Constants.STUDENT_ID)
        mVideoTitle = intent.getStringExtra(Constants.VIDEO_TITLE)
        presenter.loadChapterVideos(mStudentId,conceptId)

    }



    override fun onChapterLoadSuccess(chapterVideosModel: ChapterVideosModel) {
        gv_chapter_videos.adapter = ChapterVideoGridAdapter(this,chapterVideosModel.sub_concept_details,this)
        Log.d(Constants.LOG_TAG,chapterVideosModel.toString())
    }

    override fun onChapterLoadFailed(error: String) {
        Log.d(Constants.LOG_TAG,"Error $error")
    }

    /*override fun onPause() {
        super.onPause()
        Log.d(Constants.LOG_TAG,"${player.currentPosition} -- ${player.currentWindowIndex} -- $isVideoCompleted")

        presenter.updateVideoStatus(mStudentId,courseId,conceptId,subConceptId,isVideoCompleted,player.currentPosition)
        if(Util.SDK_INT <= 23)
             releasePlayer()
    }*/




   private fun initFullscreenDialog() {

       mFullScreenDialog = object : Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
           override fun onBackPressed() {
               if (mExoPlayerFullscreen)
                   closeFullscreenDialog()
               super.onBackPressed()
           }
       }
   }

    private fun initFullscreenButton() {

        val controlView = mExoPlayerView.findViewById<PlayerControlView>(R.id.exo_controller)
        mFullScreenIcon = controlView.findViewById(R.id.exo_fullscreen_icon)
        mFullScreenButton = controlView.findViewById(R.id.exo_fullscreen_button)
        mFullScreenButton.setOnClickListener(View.OnClickListener {
            if (!mExoPlayerFullscreen)
                openFullscreenDialog()
            else
                closeFullscreenDialog()
        })
        var backButton = controlView.findViewById<ImageButton>(R.id.exo_ib_back)
        backButton.setOnClickListener {
            onBackPressed()
        }
        var tvVideoTitle = controlView.findViewById<TextView>(R.id.exo_video_title)
        tvVideoTitle.text = mVideoTitle

    }
    private fun openFullscreenDialog() {

        (mExoPlayerView.parent as ViewGroup).removeView(mExoPlayerView)
        mFullScreenDialog.addContentView(
            mExoPlayerView,
            ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        )
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_fullscreen_skrink))
        mExoPlayerFullscreen = true


        mFullScreenDialog.show()
    }
    private fun closeFullscreenDialog() {

        (mExoPlayerView.parent as ViewGroup).removeView(mExoPlayerView)
        (findViewById<FrameLayout>(R.id.main_media_frame)).addView(mExoPlayerView)
        mExoPlayerFullscreen = false
        mFullScreenDialog.dismiss()
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_fullscreen_expand))
    }

    override fun onPause() {
        super.onPause()
        if(::mExoPlayerView.isInitialized)
        {
            mResumeWindow  = mExoPlayerView.player.currentWindowIndex
            mResumePosition  = Math.max(0,mExoPlayerView.player.contentPosition)
            presenter.updateVideoStatus(mStudentId,courseId,conceptId,subConceptId,isVideoCompleted,mResumePosition)
            mExoPlayerView.player.release()
        }

      if(::mFullScreenDialog.isInitialized)
            mFullScreenDialog.dismiss()
    }


    private fun initExoPlayer() {

        val bandwidthMeter = DefaultBandwidthMeter()
        val videoTrackSelectionFactory = AdaptiveTrackSelection.Factory(bandwidthMeter)
        val trackSelector = DefaultTrackSelector(videoTrackSelectionFactory)
        val loadControl = DefaultLoadControl()
        val player = ExoPlayerFactory.newSimpleInstance(DefaultRenderersFactory(this), trackSelector, loadControl)


        val haveResumePosition = mResumeWindow != C.INDEX_UNSET



        player.prepare(mVideoSource,haveResumePosition,false)
        player.playWhenReady = true
        if (haveResumePosition) {
            player.seekTo(mResumeWindow, mResumePosition)

        }
        player.addListener(object : Player.EventListener{
            override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {

            }

            override fun onSeekProcessed() {

            }

            override fun onTracksChanged(trackGroups: TrackGroupArray?, trackSelections: TrackSelectionArray?) {

            }

            override fun onPlayerError(error: ExoPlaybackException?) {

            }

            override fun onLoadingChanged(isLoading: Boolean) {

            }

            override fun onPositionDiscontinuity(reason: Int) {

            }

            override fun onRepeatModeChanged(repeatMode: Int) {

            }

            override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {

            }

            override fun onTimelineChanged(timeline: Timeline?, manifest: Any?, reason: Int) {

            }

            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                when(playbackState){
                    ExoPlayer.STATE_READY->{
                        showVideoProgress(false)
                    }
                    ExoPlayer.STATE_BUFFERING->{
                        showVideoProgress(true)
                    }
                    ExoPlayer.STATE_ENDED->{
                        isVideoCompleted = true
                    }
                }
            }

        })
        mExoPlayerView.player = player


    }
    fun showVideoProgress(show: Boolean)
    {
        showLoadingProgress(video_progress_bar,show)
    }

    override fun onResume() {
        super.onResume()
        if(!::mExoPlayerView.isInitialized)
        {
            mExoPlayerView = exoplayer
            initFullscreenDialog()
            initFullscreenButton()
            val streamUrl = "http://qthttp.apple.com.edgesuite.net/1010qwoeiuryfg/sl.m3u8"
            val userAgent = Util.getUserAgent(this, applicationContext.applicationInfo.packageName)
            val httpDataSourceFactory = DefaultHttpDataSourceFactory(
                userAgent,
                null,
                DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,
                DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS,
                true
            )
            val dataSourceFactory = DefaultDataSourceFactory(this, null, httpDataSourceFactory)
            val daUri = Uri.parse(mVideoUrl)

            mVideoSource = HlsMediaSource(daUri, dataSourceFactory, 1, null, null)
        }
        initExoPlayer()
       if(mExoPlayerFullscreen)
        {
            (mExoPlayerView.parent as ViewGroup).removeView(mExoPlayerView)
            mFullScreenDialog.addContentView(
                mExoPlayerView,
                ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            )
            mFullScreenIcon.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_fullscreen_skrink
                )
            )
            mFullScreenDialog.show()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }


}

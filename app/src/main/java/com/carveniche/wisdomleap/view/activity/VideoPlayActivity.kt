package com.carveniche.wisdomleap.view.activity

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log

import com.carveniche.wisdomleap.R
import com.carveniche.wisdomleap.contract.VideoPlayContract
import com.carveniche.wisdomleap.di.component.DaggerActivityComponent
import com.carveniche.wisdomleap.di.module.ActivityModule
import com.carveniche.wisdomleap.di.module.ContextModule
import com.carveniche.wisdomleap.di.module.SharedPreferenceModule
import com.carveniche.wisdomleap.model.MySharedPreferences
import com.carveniche.wisdomleap.util.Constants
import com.carveniche.wisdomleap.util.showLoadingProgress
import com.carveniche.wisdomleap.util.showLongToast
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.TrackGroupArray

import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

import kotlinx.android.synthetic.main.activity_video_play.*
import kotlinx.android.synthetic.main.layout_progressbar.*
import javax.inject.Inject


class VideoPlayActivity : AppCompatActivity(),VideoPlayContract.View {

    @Inject lateinit var presenter : VideoPlayContract.Presenter
    @Inject lateinit var mySharedPreferences: MySharedPreferences
    private lateinit var player : ExoPlayer
    private var conceptId = 0
    private var courseId = 0
    private var subConceptId = 0
    private  var mVideoUrl : String =""
    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition: Long = 0
    private var mStudentId = 0
    private var isVideoCompleted = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_play)
        injectDependency()
        presenter.attach(this)
        presenter.subscribe()
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
        if(!mVideoUrl.isEmpty())
            displayVideo()
        else
        {
            showLongToast("Unable to Play video something went wrong",this)
            super.onBackPressed()
        }
    }


    private fun displayVideo()
    {
        var adaptiveTrackSelection  = AdaptiveTrackSelection.Factory(DefaultBandwidthMeter())

        player = ExoPlayerFactory.newSimpleInstance(DefaultRenderersFactory(this),DefaultTrackSelector(adaptiveTrackSelection),DefaultLoadControl())

        player_view.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT


        player_view.player = player
        var defaultBandwidthMeter = DefaultBandwidthMeter()
        Log.d(Constants.LOG_TAG,defaultBandwidthMeter.toString())
        var dataSourceFactory = DefaultDataSourceFactory(this,Util.getUserAgent(this,"Exo2"),defaultBandwidthMeter)

        var hlsUrl = "https://s3.amazonaws.com/wisdomleap-hls-playback/grade10/Maths/Large_numbers.m3u8"
        Log.d(Constants.LOG_TAG,mVideoUrl)

        var uri = Uri.parse(mVideoUrl)
        var mainHandler = Handler()

        var mediaSource = HlsMediaSource(uri,dataSourceFactory,mainHandler,null)
        player.prepare(mediaSource)

        player.playWhenReady = playWhenReady

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
                        showProgress(false)
                    }
                    ExoPlayer.STATE_BUFFERING->{
                        showProgress(true)
                    }
                    ExoPlayer.STATE_ENDED->{
                        isVideoCompleted = true
                    }
                }
            }



        })
        player.seekTo(currentWindow,playbackPosition)
        player.prepare(mediaSource,true,false)
    }

    private fun releasePlayer()
    {
       if(player!=null)
       {
           playbackPosition = player.currentPosition
           currentWindow = player.currentWindowIndex
           playWhenReady = player.playWhenReady
           player.release()
       }
    }

    override fun onPause() {
        super.onPause()
        Log.d(Constants.LOG_TAG,"${player.currentPosition} -- ${player.currentWindowIndex} -- $isVideoCompleted")

        presenter.updateVideoStatus(mStudentId,courseId,conceptId,subConceptId,isVideoCompleted,player.currentPosition)
        if(Util.SDK_INT <= 23)
             releasePlayer()
    }

    override fun onStop() {
        super.onStop()
        if(Util.SDK_INT >= 23)
            releasePlayer()
    }
}

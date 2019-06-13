package com.carveniche.wisdomleap.view.fragment


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager

import com.carveniche.wisdomleap.R
import com.carveniche.wisdomleap.adapter.ChapterListAdapter
import com.carveniche.wisdomleap.adapter.DashboardRecentQuizListAdapter
import com.carveniche.wisdomleap.adapter.DashboardSubjectListAdapter
import com.carveniche.wisdomleap.contract.DashboardContract
import com.carveniche.wisdomleap.di.component.DaggerFragmentComponent
import com.carveniche.wisdomleap.di.module.ContextModule
import com.carveniche.wisdomleap.di.module.FragmentModule
import com.carveniche.wisdomleap.di.module.SharedPreferenceModule
import com.carveniche.wisdomleap.interfaces.IChapterClickListener
import com.carveniche.wisdomleap.interfaces.IQuizClickListener
import com.carveniche.wisdomleap.model.*
import com.carveniche.wisdomleap.util.Constants
import com.carveniche.wisdomleap.util.URL
import com.carveniche.wisdomleap.util.isFirstLaunchToday
import com.carveniche.wisdomleap.util.showLoadingProgress
import com.carveniche.wisdomleap.view.activity.ConceptQuizActivity
import com.carveniche.wisdomleap.view.activity.SubjectActivity
import com.carveniche.wisdomleap.view.activity.VideoPlayActivity
import com.google.android.libraries.places.internal.i
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.layout_progressbar.*
import org.jsoup.Jsoup
import javax.inject.Inject


class DashboardFragment : Fragment(),DashboardContract.View,IChapterClickListener,IQuizClickListener {



    private lateinit var rootView : View
    @Inject lateinit var presenter : DashboardContract.Presenter
    @Inject lateinit var mySharedPreferences: MySharedPreferences
    private  var studentId =0
    private lateinit var subjectListData : SubjectListModel
    private lateinit var inprogress_practice: InprogressPractice
    private lateinit var inprogress_video:  InprogressVideo

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_dashboard, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attach(this)
        presenter.subscribe()
        initUI()
        if(isFirstLaunchToday(mySharedPreferences))
        {
            showDailyMessages()
        }
        studentId = mySharedPreferences.getIntData(Constants.STUDENT_ID)
        presenter.loadDashboardData(studentId)
    }

    private fun showDailyMessages() {
        var ft = childFragmentManager.beginTransaction()
        var prev = childFragmentManager.findFragmentByTag("dialog")
        if(prev!=null)
            ft.remove(prev)
        ft.addToBackStack(null)

        var dialogFragment = DailyMessageDialogFragment()
        dialogFragment.show(ft,"dialog")
    }

    private fun initUI() {
        btn_resume_quiz.setOnClickListener {
            resumeQuizActivity()
        }
        btn_resume_video.setOnClickListener {
            resumeVideoActivity()
        }
    }

    private fun injectDependency() {
        val fragmentComponent = DaggerFragmentComponent.builder()
            .fragmentModule(FragmentModule())
            .sharedPreferenceModule(SharedPreferenceModule())
            .contextModule(ContextModule(context!!))
            .build()
        fragmentComponent.inject(this)

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
    }

    companion object {
        const val TAG = "DashboardFragment"
    }
    override fun showProgress(show: Boolean) {
        showLoadingProgress(progressBar,show)
    }
    override fun onLoadDataSucess(subjectListModel: SubjectListModel) {
        this.subjectListData = subjectListModel
        assignSubjectDatas()
        if(!subjectListModel.recent_video.isNullOrEmpty())
        {
            displayRecentVideos(subjectListModel.recent_video)
            ll_recently_videos_container.visibility = View.VISIBLE
        }
        else
        {
            ll_recently_videos_container.visibility = View.GONE
        }
        if(!subjectListModel.recent_practice.isNullOrEmpty())
        {
            displayRecentQuiz(subjectListModel.recent_practice)
            ll_recently_quiz_container.visibility = View.VISIBLE
        }
        else
        {
            ll_recently_quiz_container.visibility = View.GONE
        }
        if(!subjectListModel.inprogress_practice.isNullOrEmpty())
            displayInprogressQuiz(subjectListModel.inprogress_practice)
        else
            ll_in_progress_quiz_container.visibility = View.GONE
        if(!subjectListModel.inprogress_video.isNullOrEmpty())
            displayInProgressVideo(subjectListModel.inprogress_video)
        else
            ll_in_progress_video_container.visibility = View.GONE

    }

    private fun displayInProgressVideo(inprogress_video: List<InprogressVideo>) {
        this.inprogress_video = inprogress_video[0]
        ll_in_progress_video_container.visibility = View.VISIBLE
        Picasso.with(context).load(inprogress_video[0].image)
            .into(iv_video_inprogress)
        tv_video_inprogress.text = inprogress_video[0].name.replace("_"," ")
    }

    private fun displayInprogressQuiz(inprogress_practice: List<InprogressPractice>) {
        this.inprogress_practice = inprogress_practice[0]
        ll_in_progress_quiz_container.visibility = View.VISIBLE
         iv_quiz_inprogress.setImageDrawable(ContextCompat.getDrawable(context!!,R.drawable.quiz_in_progress))
         tv_quiz_inprogress.text = inprogress_practice[0].chapter_name.replace("_"," ")
    }

    override fun onQuizItemClick(recentQuiz: RecentPractice) {
        Log.d(Constants.LOG_TAG,recentQuiz.toString())
    }
    private fun displayRecentQuiz(recent_practice: List<RecentPractice>) {
        rv_recent_quiz.layoutManager =  LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rv_recent_quiz.adapter = DashboardRecentQuizListAdapter(context!!,recent_practice,this)
    }

    private fun displayRecentVideos(recent_video: List<RecentVideo>) {
        var subConceptList = mutableListOf<SubConceptDetail>()
        recent_video.forEach {
            subConceptList.add(SubConceptDetail(1,it.name,true,it.link,it.image))
        }

        val firstManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rv_recent_videos.layoutManager = firstManager
       rv_recent_videos.adapter = ChapterListAdapter(context!!,5,subConceptList,this)

    }
    override fun onChapterClick(conceptId: Int, subconceptId: Int, videoUrl: String) {

    }

    private fun assignSubjectDatas() {
        var total = subjectListData.course_details.size

        var column = 2
        var row = total / column
        gl_subject_container.alignmentMode = GridLayout.ALIGN_BOUNDS
        gl_subject_container.columnCount = column;
        gl_subject_container.rowCount = row+1
        var i = 0
        var c = 0
        var r = 0
        while (i < total)
        {
            if (c == column)
            {
                c = 0
                r++
            }
            val oImageView = ImageView(context)
            oImageView.setImageResource(R.drawable.ic_quiz_computer)
           oImageView.layoutParams = ViewGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
            var params = GridLayout.LayoutParams()
            Picasso.with(context).load(URL.WISDOM_LEAP_URL+subjectListData.course_details[i].image_url)
                .into(oImageView)
            oImageView.tag = i
            oImageView.setOnClickListener {
               var position = Integer.parseInt(it.tag.toString())
                showChapters(subjectListData.course_details[position].course_id)
            }
            params.height = GridLayout.LayoutParams.WRAP_CONTENT
            params.width = GridLayout.LayoutParams.WRAP_CONTENT
            params.setGravity(Gravity.FILL)
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED,1f)
            params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED,1f)

            gl_subject_container.addView(oImageView,params)

            i++
            c++
        }
    }

    private fun showChapters(selCourserId : Int) {

        var intent = Intent(context,SubjectActivity::class.java)
        intent.putExtra(Constants.COURSE_ID,selCourserId)
        startActivity(intent)

    }

    override fun onLoadDataFailed(msg: String) {

    }

    private fun resumeQuizActivity()
    {
        var intent = Intent(context,ConceptQuizActivity::class.java)
        intent.putExtra(Constants.STUDENT_ID,studentId)
        intent.putExtra(Constants.COURSE_ID,inprogress_practice.course_id)
        intent.putExtra(Constants.CONCEPT_ID,inprogress_practice.chapter_id)
        startActivity(intent)
    }
    private fun resumeVideoActivity()
    {
        var intent = Intent(context,VideoPlayActivity::class.java)
        intent.putExtra(Constants.STUDENT_ID,studentId)
        intent.putExtra(Constants.COURSE_ID,inprogress_video.course_id)
        intent.putExtra(Constants.CONCEPT_ID,inprogress_video.chapter_id)
        intent.putExtra(Constants.SUB_CONCEPT_ID,inprogress_video.chapter_id)
        intent.putExtra(Constants.VIDEO_URL,"https://wisdomleap-hls-playback.s3.amazonaws.com/grade1/math/grade1-math1.m3u8")
        startActivity(intent)
    }



}


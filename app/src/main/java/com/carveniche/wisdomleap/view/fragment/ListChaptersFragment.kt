package com.carveniche.wisdomleap.view.fragment


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.carveniche.wisdomleap.R
import com.carveniche.wisdomleap.adapter.ChapterListAdapter
import com.carveniche.wisdomleap.contract.ListChapterContract
import com.carveniche.wisdomleap.di.component.DaggerFragmentComponent
import com.carveniche.wisdomleap.di.module.ContextModule
import com.carveniche.wisdomleap.di.module.FragmentModule
import com.carveniche.wisdomleap.di.module.SharedPreferenceModule
import com.carveniche.wisdomleap.model.ChapterConcept
import com.carveniche.wisdomleap.model.ChapterListModel
import com.carveniche.wisdomleap.model.MySharedPreferences
import com.carveniche.wisdomleap.util.Constants
import com.carveniche.wisdomleap.util.showLoadingProgress
import com.carveniche.wisdomleap.util.showLongToast
import com.carveniche.wisdomleap.view.activity.SubjectActivity
import com.carveniche.wisdomleap.view.activity.VideoPlayActivity
import kotlinx.android.synthetic.main.fragment_list_chapters.*
import kotlinx.android.synthetic.main.layout_progressbar.*
import javax.inject.Inject
import androidx.core.content.ContextCompat.getSystemService
import com.carveniche.wisdomleap.view.activity.ConceptQuizActivity


class ListChaptersFragment : Fragment(),ListChapterContract.View {

    lateinit var rootView: View
    @Inject lateinit var presenter : ListChapterContract.Presenter
    lateinit var subjectActivity : SubjectActivity
    private lateinit var chapterConceptList: List<ChapterConcept>
    @Inject lateinit var mySharedPreferences: MySharedPreferences
    private var courseId = 0
    private var studentId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
        subjectActivity = activity as SubjectActivity
        courseId = arguments!!.getInt(Constants.COURSE_ID)
        studentId = mySharedPreferences.getIntData(Constants.STUDENT_ID)
    }
    private fun injectDependency() {
        val fragmentComponent = DaggerFragmentComponent.builder()
            .fragmentModule(FragmentModule())
            .sharedPreferenceModule(SharedPreferenceModule())
            .contextModule(ContextModule(context!!))
            .build()
        fragmentComponent.inject(this)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_list_chapters, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        presenter.attach(this)
        presenter.subscribe()
        presenter.loadData(studentId,courseId)
    }

    companion object {
        const val TAG = "ListChapterFragment"
    }

    override fun showProgress(show: Boolean) {
        showLoadingProgress(progressBar,show)
    }
    override fun onLoadDataSucess(chapterList: ChapterListModel) {
        this.chapterConceptList = chapterList.chapter_concepts
        chapterConceptList.forEach {
            displayData(it)
        }

    }

    private fun displayData(chapterConcept : ChapterConcept) {
        val inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var customView = inflater.inflate(R.layout.list_item_chapter_video,null)

        var rvChapter = customView.findViewById<RecyclerView>(R.id.recyclerView)
        var tvHeader = customView.findViewById<TextView>(R.id.tvHeader)
        var btnTakeTest = customView.findViewById<Button>(R.id.btnTakeTest)

        tvHeader.text = chapterConcept.chapter_name

        val firstManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvChapter.layoutManager = firstManager
        rvChapter.adapter = ChapterListAdapter(context!!,chapterConcept.chapter_id,chapterConcept.sub_concept_details,this)


        if(chapterConcept.questions)
            btnTakeTest.visibility = View.VISIBLE
        else
            btnTakeTest.visibility = View.GONE


        btnTakeTest.setOnClickListener {
            startQuizActivity(chapterConcept.chapter_id)
        }

        lvContainer.addView(customView)
        Log.d(Constants.LOG_TAG,"$courseId - $studentId - ${chapterConcept.chapter_id}")

    }


    private fun startQuizActivity(conceptId : Int)
    {
        var intent  =  Intent(context,ConceptQuizActivity::class.java)
        intent.putExtra(Constants.STUDENT_ID,studentId)
        intent.putExtra(Constants.COURSE_ID,courseId)
        intent.putExtra(Constants.CONCEPT_ID,conceptId)
        startActivity(intent)
    }

    override fun onLoadDataError(msg: String) {
        showLongToast(msg,context!!)
    }

    override fun onChapterClick(conceptId: Int, subconceptId: Int, videoUrl: String) {
        var intent = Intent(context,VideoPlayActivity::class.java)
        intent.putExtra(Constants.VIDEO_URL,videoUrl)
        intent.putExtra(Constants.COURSE_ID,courseId)
        intent.putExtra(Constants.CONCEPT_ID,conceptId)
        intent.putExtra(Constants.SUB_CONCEPT_ID,subconceptId)
        startActivity(intent)
    }
}

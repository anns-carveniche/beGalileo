package com.carveniche.wisdomleap.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.carveniche.wisdomleap.R
import com.carveniche.wisdomleap.adapter.RecentlyViewedPagerAdapter
import com.carveniche.wisdomleap.api.ApiInterface
import com.carveniche.wisdomleap.di.module.ActivityLogModel
import com.carveniche.wisdomleap.di.module.QuizData
import com.carveniche.wisdomleap.di.module.VideoData
import com.carveniche.wisdomleap.util.Constants
import com.carveniche.wisdomleap.util.showLoadingProgress
import com.carveniche.wisdomleap.util.showShortToast
import com.carveniche.wisdomleap.view.fragment.RecentViewedQuizFragment
import com.carveniche.wisdomleap.view.fragment.RecentViewedVideosFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_recently_viewed.*
import kotlinx.android.synthetic.main.layout_progressbar.*




class RecentlyViewedActivity : AppCompatActivity(),AdapterView.OnItemSelectedListener {


    private var api = ApiInterface.create()
    private var disposable = CompositeDisposable()
    private var studentId = 0
    private var search = "all"
    private lateinit var activityLogModel : ActivityLogModel
    private lateinit var monthList : Array<String>
    val adapter = RecentlyViewedPagerAdapter(supportFragmentManager)
    private var isFirstLoad = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recently_viewed)
        monthList = resources.getStringArray(R.array.month_arrays)
        studentId = intent.getIntExtra(Constants.STUDENT_ID,0)
        spinner_month.onItemSelectedListener = this

    }

    fun showProgress(state : Boolean)
    {
        showLoadingProgress(progressBar,state)
    }

    fun getVideoData() : List<VideoData>
    {
        return activityLogModel.video_data
    }
    fun getQuizData() : List<QuizData>
    {
        return activityLogModel.quiz_data
    }


    private fun getActivityDatas() {
        showProgress(true)
        Log.d(Constants.LOG_TAG,studentId.toString())
        var activityObservable = api.getActivityLog(studentId,search)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                showProgress(false)
                if(it.status)
                    onDataLoadSuccess(it)
                else
                    onDataLoadFailed(it.message)
            },{
                showProgress(false)
                onDataLoadFailed(it.localizedMessage)
            })
        disposable.add(activityObservable)
    }

    fun onDataLoadSuccess(activityLogModel: ActivityLogModel)
    {
        this.activityLogModel = activityLogModel
        if(isFirstLoad)
        {
            showTabFragements()
            isFirstLoad = false
        }
        else
          refreshTabFragments()

    }

    private fun refreshTabFragments() {
        val adapter = viewPager.adapter as RecentlyViewedPagerAdapter
        var videoFragment = adapter.getFragment(0) as RecentViewedVideosFragment
        videoFragment.loadDatas()
        var quizFragment = adapter.getFragment(1) as RecentViewedQuizFragment
        quizFragment.loadDatas()

    }

    private fun showTabFragements() {

        adapter.addFragment(RecentViewedVideosFragment(),"Recent Videos")
        adapter.addFragment(RecentViewedQuizFragment(),"Recent Quiz")
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
    }

    fun onDataLoadFailed(msg :String)
    {
        showShortToast(msg,this)
    }

    override fun onPause() {
        super.onPause()
        disposable.clear()
    }
    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        search = monthList[position].toLowerCase()
        getActivityDatas()
    }

}


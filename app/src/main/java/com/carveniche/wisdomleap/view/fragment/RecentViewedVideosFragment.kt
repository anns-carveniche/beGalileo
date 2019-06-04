package com.carveniche.wisdomleap.view.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.carveniche.wisdomleap.R
import com.carveniche.wisdomleap.adapter.LogVideoAdapter
import com.carveniche.wisdomleap.adapter.MainTabAdapter
import com.carveniche.wisdomleap.contract.RecentViewedVideosContract
import com.carveniche.wisdomleap.di.component.DaggerFragmentComponent
import com.carveniche.wisdomleap.di.module.ContextModule
import com.carveniche.wisdomleap.di.module.FragmentModule
import com.carveniche.wisdomleap.di.module.SharedPreferenceModule
import com.carveniche.wisdomleap.di.module.VideoData
import kotlinx.android.synthetic.main.fragment_recent_viewed_videos.*
import javax.inject.Inject
import com.carveniche.wisdomleap.view.activity.MainActivity
import com.carveniche.wisdomleap.view.activity.RecentlyViewedActivity


class RecentViewedVideosFragment() : Fragment(),RecentViewedVideosContract.View {

    @Inject lateinit var presenter : RecentViewedVideosContract.Presenter
    private lateinit var rootView : View
    private lateinit var videoData : List<VideoData>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_recent_viewed_videos, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attach(this)
        presenter.subscribe()
        initUI()
    }

    private fun initUI() {
        loadDatas()
    }
    fun loadDatas()
    {
        videoData = (activity as RecentlyViewedActivity).getVideoData()
        rv_video_log.layoutManager =  LinearLayoutManager(context)
        rv_video_log.adapter = LogVideoAdapter(videoData)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        injectDependency()
    }
    private fun injectDependency() {
        val fragmentComponent = DaggerFragmentComponent.builder()
            .fragmentModule(FragmentModule())
            .sharedPreferenceModule(SharedPreferenceModule())
            .contextModule(ContextModule(context!!))
            .build()
        fragmentComponent.inject(this)

    }
    override fun showProgress(show: Boolean) {

    }


}

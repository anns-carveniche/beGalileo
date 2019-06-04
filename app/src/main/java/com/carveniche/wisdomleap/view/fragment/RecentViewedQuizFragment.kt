package com.carveniche.wisdomleap.view.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.carveniche.wisdomleap.R
import com.carveniche.wisdomleap.adapter.LogQuizAdapter
import com.carveniche.wisdomleap.contract.RecentViewedQuizContract
import com.carveniche.wisdomleap.di.component.DaggerFragmentComponent
import com.carveniche.wisdomleap.di.module.ContextModule
import com.carveniche.wisdomleap.di.module.FragmentModule
import com.carveniche.wisdomleap.di.module.QuizData
import com.carveniche.wisdomleap.di.module.SharedPreferenceModule
import com.carveniche.wisdomleap.view.activity.RecentlyViewedActivity
import kotlinx.android.synthetic.main.fragment_recent_viewed_quiz.*
import javax.inject.Inject


class RecentViewedQuizFragment : Fragment(),RecentViewedQuizContract.View {


    @Inject lateinit var presenter : RecentViewedQuizContract.Presenter
    private lateinit var rootView : View
    private lateinit var quizDataList : List<QuizData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_recent_viewed_quiz, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()

    }

     fun loadDatas() {
        quizDataList = (activity as RecentlyViewedActivity).getQuizData()
        rv_quiz_log.layoutManager = LinearLayoutManager(context)
        rv_quiz_log.adapter = LogQuizAdapter(quizDataList)

    }

    private fun initUI() {
        presenter.attach(this)
        presenter.subscribe()
        loadDatas()
    }
    override fun showProgress(show: Boolean) {

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

}

package com.carveniche.wisdomleap.view.fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.carveniche.wisdomleap.R
import com.carveniche.wisdomleap.contract.DashboardContract
import com.carveniche.wisdomleap.di.component.DaggerFragmentComponent
import com.carveniche.wisdomleap.di.module.ContextModule
import com.carveniche.wisdomleap.di.module.FragmentModule
import com.carveniche.wisdomleap.di.module.SharedPreferenceModule
import com.carveniche.wisdomleap.util.showLoadingProgress
import com.carveniche.wisdomleap.view.activity.SubjectActivity
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.layout_progressbar.*
import javax.inject.Inject


class DashboardFragment : Fragment(),DashboardContract.View {


    private lateinit var rootView : View
    @Inject lateinit var presenter : DashboardContract.Presenter

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
    }

    private fun initUI() {
        btnShowAllCourses.setOnClickListener {
            var intent = Intent(context,SubjectActivity::class.java)
            startActivity(intent)
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

}

package com.carveniche.wisdomleap.view.fragment


import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.carveniche.wisdomleap.R
import com.carveniche.wisdomleap.contract.QuizResultContract
import com.carveniche.wisdomleap.di.component.DaggerFragmentComponent
import com.carveniche.wisdomleap.di.module.ContextModule
import com.carveniche.wisdomleap.di.module.FragmentModule
import com.carveniche.wisdomleap.di.module.SharedPreferenceModule
import javax.inject.Inject

class QuizResultFragment : Fragment(),QuizResultContract.View {


    @Inject lateinit var presenter : QuizResultContract.Presenter
    private lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_quiz_result, container, false)
        return rootView
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
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attach(this)
    }
    override fun showProgress(show: Boolean) {

    }
    companion object {
        const val TAG = "QuizResultFragment"
    }
}

package com.carveniche.wisdomleap.view.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.carveniche.wisdomleap.R
import com.carveniche.wisdomleap.contract.ProfileHomeContract
import com.carveniche.wisdomleap.di.component.DaggerFragmentComponent
import com.carveniche.wisdomleap.di.module.ContextModule
import com.carveniche.wisdomleap.di.module.FragmentModule
import com.carveniche.wisdomleap.di.module.SharedPreferenceModule
import javax.inject.Inject


class ProfileHomeFragment : Fragment(),ProfileHomeContract.View {

    private lateinit var rootView : View
    @Inject lateinit var presenter: ProfileHomeContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_profile_home, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attach(this)
        presenter.subscribe()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
    }
    override fun showProgress(show: Boolean) {

    }

    companion object {
        const val TAG = "ProfileHomeContract"
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

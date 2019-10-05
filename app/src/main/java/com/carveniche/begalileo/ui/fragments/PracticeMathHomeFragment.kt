package com.carveniche.begalileo.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.carveniche.begalileo.R
import com.carveniche.begalileo.contract.PracticeMathHomeContract
import com.carveniche.begalileo.di.component.DaggerFragmentComponent
import com.carveniche.begalileo.di.module.ContextModule
import com.carveniche.begalileo.di.module.Fragmentmodule
import com.carveniche.begalileo.util.Constants
import kotlinx.android.synthetic.main.fragment_practice_math_home.*
import javax.inject.Inject

class PracticeMathHomeFragment : Fragment(),PracticeMathHomeContract.View,View.OnClickListener {

    @Inject lateinit var presenter : PracticeMathHomeContract.Presenter
    private lateinit var rootView : View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_practice_math_home,container,false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attach(this)
        presenter.subscribe()
        initUI()
    }

    private fun initUI() {

    }

    private fun injectDependency() {
        val fragmentComponent = DaggerFragmentComponent.builder()
            .fragmentmodule(Fragmentmodule())
            .contextModule(ContextModule(context!!))
            .build()
        fragmentComponent.inject(this)
    }
    override fun showProgress(boolean: Boolean) {

    }
    companion object {
        const val TAG = "PracticeMathHomeFragment"
    }
    override fun onClick(v: View?) {



    }




}
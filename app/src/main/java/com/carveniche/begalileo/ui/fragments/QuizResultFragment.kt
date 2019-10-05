package com.carveniche.begalileo.ui.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.carveniche.begalileo.R
import com.carveniche.begalileo.contract.QuizResultContract
import com.carveniche.begalileo.di.component.DaggerFragmentComponent
import com.carveniche.begalileo.di.module.ContextModule
import com.carveniche.begalileo.di.module.Fragmentmodule
import com.carveniche.begalileo.ui.activities.QuizActivity
import kotlinx.android.synthetic.main.fragment_quiz_result.*
import kotlinx.android.synthetic.main.fragment_quiz_result.view.*
import javax.inject.Inject

class QuizResultFragment : Fragment(),QuizResultContract.View {


    @Inject lateinit var presenter : QuizResultContract.Presenter
    private lateinit var rootView : View
    private lateinit var quizActivity : QuizActivity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
        quizActivity = activity as QuizActivity
    }

    private fun injectDependency() {
        val fragmentComponent = DaggerFragmentComponent.builder()
            .fragmentmodule(Fragmentmodule())
            .contextModule(ContextModule(context!!))
            .build()
        fragmentComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_quiz_result, container, false)
        initUI()
        return rootView
    }

    private fun initUI() {
        rootView.iv_image.setImageBitmap(quizActivity.verifiedImage)
    }

    override fun showProgress(boolean: Boolean) {

    }
    companion object {
        var TAG = "QuizResultFragment"
    }


}

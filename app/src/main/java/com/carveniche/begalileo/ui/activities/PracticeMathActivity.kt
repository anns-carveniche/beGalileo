package com.carveniche.begalileo.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.carveniche.begalileo.R
import com.carveniche.begalileo.contract.PracticeMathContract
import com.carveniche.begalileo.di.component.DaggerActivityComponent
import com.carveniche.begalileo.di.module.ActivityModule
import com.carveniche.begalileo.di.module.ContextModule
import com.carveniche.begalileo.models.PracticeQuizQuestionModel
import com.carveniche.begalileo.ui.fragments.ShowSolutionDialogFragment
import com.carveniche.begalileo.ui.fragments.question_template.ChoiceTypeQuestionFragment
import com.carveniche.begalileo.ui.fragments.question_template.DragDropTypeQuestionFragment
import com.carveniche.begalileo.ui.fragments.question_template.KeyingTypeQuestionFragment
import com.carveniche.begalileo.util.Constants
import com.carveniche.begalileo.util.showLoadingProgress
import kotlinx.android.synthetic.main.activity_practice_math.*
import kotlinx.android.synthetic.main.layout_progressbar.*
import javax.inject.Inject

class PracticeMathActivity : AppCompatActivity(),PracticeMathContract.View,ISolutionListener {



    @Inject
    lateinit var presenter : PracticeMathContract.Presenter
    var questionNumber  = 34
     lateinit var practiceQuizQuestionModel: PracticeQuizQuestionModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practice_math)
        injectDependency()
        presenter.attach(this)
        presenter.subscribe()
        initUI()


    }

    private fun initUI() {
            getPracticeQuizQuestion()
    }

     fun getPracticeQuizQuestion() {
        questionNumber++
        presenter.getPracticeQuizQuestions(questionNumber)
    }


    private fun showChoicTypeQuestionFragment()
    {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.frame,
                ChoiceTypeQuestionFragment(),
                ChoiceTypeQuestionFragment.TAG)
            .commit()
    }
    private fun showKeyTypeQuestionFragment()
    {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.frame,
                KeyingTypeQuestionFragment(),
                KeyingTypeQuestionFragment.TAG)
            .commit()
    }
    private fun showDragDropTypeQuestionFragment()
    {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.frame,
                DragDropTypeQuestionFragment(),
                DragDropTypeQuestionFragment.TAG)
            .commit()
    }

    override fun onSolutionModelClosed() {
        getPracticeQuizQuestion()
    }

    private fun injectDependency() {
        val activityComponent = DaggerActivityComponent.builder()
            .activityModule(ActivityModule(this))
            .contextModule(ContextModule(this))
            .build()
        activityComponent.inject(this)
    }

    override fun showProgress(show: Boolean) {
      showLoadingProgress(progressBar,show)
    }


    override fun onPracticeQuizQuestionLoadSuccess(practiceModel: PracticeQuizQuestionModel) {
        /*Log.d(Constants.LOG_TAG,questionNumber.toString())
        Log.d(Constants.LOG_TAG,practiceModel.toString())*/
        this.practiceQuizQuestionModel = practiceModel
        when(practiceQuizQuestionModel.question_data.choiceType){
            Constants.QUESTION_TYPE_DRAG_DROP -> showChoicTypeQuestionFragment()
            Constants.QUESTION_TYPE_KEYING -> showKeyTypeQuestionFragment()
            Constants.QUESTION_TYPE_SELECT_CHOICE -> showChoicTypeQuestionFragment()

        }
    }

     fun openShowDialogFragment(value : String)
    {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val prev = supportFragmentManager.findFragmentByTag("dialog")
        if(prev!=null)
        {
            fragmentTransaction.remove(prev)
        }
        fragmentTransaction.addToBackStack(null)
        val dialogFragment = ShowSolutionDialogFragment(value,this)
        dialogFragment.show(fragmentTransaction,"dialog")

    }

    override fun onPracticeQuizQuestionLoadFailed(errorMsg: String) {
        Log.e(Constants.LOG_TAG,"Error : $errorMsg")
    }

    override fun onBackPressed() {

    }
}
 interface ISolutionListener{
    public fun onSolutionModelClosed()
}


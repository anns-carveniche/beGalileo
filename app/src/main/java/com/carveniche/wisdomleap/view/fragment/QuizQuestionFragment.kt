package com.carveniche.wisdomleap.view.fragment


import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import android.widget.RadioButton
import android.widget.TextView
import androidx.fragment.app.Fragment

import com.carveniche.wisdomleap.R
import com.carveniche.wisdomleap.contract.QuizQuestionContract
import com.carveniche.wisdomleap.di.component.DaggerFragmentComponent
import com.carveniche.wisdomleap.di.module.ContextModule
import com.carveniche.wisdomleap.di.module.FragmentModule
import com.carveniche.wisdomleap.di.module.SharedPreferenceModule
import com.carveniche.wisdomleap.model.QuizQuestionModel
import com.carveniche.wisdomleap.util.Constants
import com.carveniche.wisdomleap.util.showLoadingProgress
import com.carveniche.wisdomleap.util.showLongToast
import com.carveniche.wisdomleap.view.activity.QuizActivity
import kotlinx.android.synthetic.main.fragment_quiz_question.*
import kotlinx.android.synthetic.main.layout_progressbar.*
import org.jsoup.Jsoup
import java.util.concurrent.TimeUnit

import javax.inject.Inject


class QuizQuestionFragment : Fragment(),QuizQuestionContract.View {

    @Inject lateinit var presenter : QuizQuestionContract.Presenter
    private lateinit var rootView : View
    private lateinit var quizQuestionModel: QuizQuestionModel
    var mCurrentQuestion = ""
    var mCurrentOptions = mutableListOf<String>()
    var currentQuestionNumber = 0
    var mCorrectAnswer = ""
    private var categoryId = 0
    private var mLevel = ""
    private var playerScore = 0
    private lateinit var quizActivity : QuizActivity
    var timeWhenStopped: Long = 0
    private var timeTaken : Long = 0
    private lateinit var mChronometer: Chronometer
    private var isResume = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_quiz_question, container, false)
        return rootView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
        categoryId = arguments!!.getInt(Constants.QUIZ_CATEGORY)
        mLevel = arguments!!.getString(Constants.QUIZ_LEVEL)
        quizActivity = activity as QuizActivity

    }
    private fun injectDependency() {
        val fragmentComponent = DaggerFragmentComponent.builder()
            .fragmentModule(FragmentModule())
            .sharedPreferenceModule(SharedPreferenceModule())
            .contextModule(ContextModule(context!!))
            .build()
        fragmentComponent.inject(this)

    }
    override fun loadQuestionSucess(quizQuestionModel: QuizQuestionModel) {
        this.quizQuestionModel = quizQuestionModel
        startTimer()
        updateQuestion()
    }

    override fun loadFailed(msg: String) {
        Log.d(Constants.LOG_TAG,msg)
    }


    fun startTimer()
    {
        mChronometer.base = SystemClock.elapsedRealtime() - timeWhenStopped
        mChronometer.start()
    }

    fun stopTimer()
    {
        timeWhenStopped =  mChronometer.base - SystemClock.elapsedRealtime()
        mChronometer.stop()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attach(this)
        presenter.subscribe()
        presenter.loadQuizQuestions(15,categoryId,mLevel,Constants.QUIZ_TYPE)
        mChronometer = chronometerTimer
        btnCheckAnswer.setOnClickListener {
            if(rgOptions.checkedRadioButtonId==-1)
                showLongToast("Please choose an option",context!!)
            else{
                showAnswerStatus(isValidAnswer())
                if(isValidAnswer())
                    playerScore++
                if(currentQuestionNumber<=14)
                {
                    updateQuestion()
                }
                else
                {
                    endQuiz()
                }
            }

        }
        btnQuit.setOnClickListener {
           endQuiz()
        }

    }

    private fun endQuiz()
    {
        timeTaken = SystemClock.elapsedRealtime() - mChronometer.base
        Log.d(Constants.LOG_TAG,TimeUnit.MILLISECONDS.toSeconds(timeTaken).toString())
        quizActivity.showQuizResultFragment(playerScore,timeTaken.toInt())
    }


    private fun isValidAnswer(): Boolean {
        var selectedId = rgOptions.checkedRadioButtonId
        var radioButton = rootView.findViewById<RadioButton>(selectedId)
        return radioButton.text == mCorrectAnswer
    }

    override fun showProgress(status: Boolean) {
        showLoadingProgress(progressBar,status)
    }

    override fun showAnswerStatus(isCorrect: Boolean) {

        when(currentQuestionNumber)
        {
            1->changeQuestionNumberStatus(tvNum1,isCorrect)
            2->changeQuestionNumberStatus(tvNum2,isCorrect)
            3->changeQuestionNumberStatus(tvNum3,isCorrect)
            4->changeQuestionNumberStatus(tvNum4,isCorrect)
            5->changeQuestionNumberStatus(tvNum5,isCorrect)
            6->changeQuestionNumberStatus(tvNum6,isCorrect)
            7->changeQuestionNumberStatus(tvNum7,isCorrect)
            8->changeQuestionNumberStatus(tvNum8,isCorrect)
            9->changeQuestionNumberStatus(tvNum9,isCorrect)
            10->changeQuestionNumberStatus(tvNum10,isCorrect)
            11->changeQuestionNumberStatus(tvNum11,isCorrect)
            12->changeQuestionNumberStatus(tvNum12,isCorrect)
            13->changeQuestionNumberStatus(tvNum13,isCorrect)
            14->changeQuestionNumberStatus(tvNum14,isCorrect)
            15->changeQuestionNumberStatus(tvNum15,isCorrect)

        }
    }

    private fun changeQuestionNumberStatus(textView : TextView, isCorrect: Boolean)
    {
        textView.isSelected = isCorrect
        textView.isEnabled = isCorrect
    }

    override fun updateQuestion() {
        tvQuestionNumber.text = context!!.getString(R.string.QuestionNumber,currentQuestionNumber+1,15)
        rgOptions.clearCheck()
        mCurrentOptions.clear()
        var result = quizQuestionModel.results[currentQuestionNumber]
        mCurrentQuestion = Jsoup.parse(result.question).text()
        mCorrectAnswer =  Jsoup.parse(result.correct_answer+".").text()
        Log.d(Constants.LOG_TAG,mCurrentQuestion)
        Log.d(Constants.LOG_TAG,mCorrectAnswer)
        mCurrentOptions.add(mCorrectAnswer)
        result.incorrect_answers.forEach {
            mCurrentOptions.add(Jsoup.parse(it).text())
        }
        tvQuestion.text = mCurrentQuestion

        mCurrentOptions.shuffle()

        radioButton1.text = mCurrentOptions[0]
        radioButton2.text = mCurrentOptions[1]
        radioButton3.text = mCurrentOptions[2]
        radioButton4.text = mCurrentOptions[3]
        currentQuestionNumber++
    }


    companion object {
        const val TAG = "QuizQuestionFragment"
    }

    override fun onPause() {
        Log.d(Constants.LOG_TAG,"ON PAUSE")
        super.onPause()
        stopTimer()
        isResume = true
        presenter.unSubscribe()
    }

    override fun onResume() {
        super.onResume()
       if(isResume)
       {
           Log.d(Constants.LOG_TAG,"Start Timer Resume")
           startTimer()
           isResume = false
       }
    }



}
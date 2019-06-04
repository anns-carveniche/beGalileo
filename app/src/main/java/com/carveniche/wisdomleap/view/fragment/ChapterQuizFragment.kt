package com.carveniche.wisdomleap.view.fragment


import android.graphics.Color
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment

import com.carveniche.wisdomleap.R
import com.carveniche.wisdomleap.contract.ChapterQuizContract
import com.carveniche.wisdomleap.di.component.DaggerFragmentComponent
import com.carveniche.wisdomleap.di.module.ContextModule
import com.carveniche.wisdomleap.di.module.FragmentModule
import com.carveniche.wisdomleap.di.module.SharedPreferenceModule
import com.carveniche.wisdomleap.model.ChapterQuizModel
import com.carveniche.wisdomleap.model.QuizData

import com.carveniche.wisdomleap.model.ResultData
import com.carveniche.wisdomleap.util.Constants
import com.carveniche.wisdomleap.util.showLoadingProgress
import com.carveniche.wisdomleap.util.showLongToast
import com.carveniche.wisdomleap.view.activity.ConceptQuizActivity
import kotlinx.android.synthetic.main.dialog_concept_quiz_option_result.view.*
import kotlinx.android.synthetic.main.fragment_chapter_quiz.*
import kotlinx.android.synthetic.main.layout_progressbar.*
import org.jsoup.Jsoup

import javax.inject.Inject


class ChapterQuizFragment : Fragment(),ChapterQuizContract.View {


    @Inject lateinit var presenter : ChapterQuizContract.Presenter
    private lateinit var rootView : View
    private var studentId = 0
    private var courseId = 0
    private var chapterId = 0
    private var totalQuestion = 15
    private var mSelectedOptionIndex= 0
    private var isCompleted = false
    private var questionCount = 0
    private var userCorrectAnswer = 0
    private lateinit var quizDataList: List<QuizData>
    private lateinit var resultData: List<ResultData>
    private  var quizId = 0
    private var rbList = mutableListOf<RadioButton>()
    private var imageOptionList = mutableListOf<ImageView>()
    private lateinit var conceptQuizActivity: ConceptQuizActivity
    var timeWhenStopped: Long = 0
    private lateinit var mChronometer : Chronometer
    private var questionStartTime : Long = 0
    private var timeTaken : Long = 0
    private var currentQuestion = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_chapter_quiz, container, false)
        return rootView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
        studentId = arguments!!.getInt(Constants.STUDENT_ID)
        courseId = arguments!!.getInt(Constants.COURSE_ID)
        chapterId = arguments!!.getInt(Constants.CONCEPT_ID)
        conceptQuizActivity = activity as ConceptQuizActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        presenter.attach(this)
        presenter.subscribe()
        presenter.loadQuestionDatas(studentId,courseId,chapterId)
    }

    private fun initUI() {

        mChronometer = chronometerTimer

        rbList.add(radioButton1)
        rbList.add(radioButton2)
        rbList.add(radioButton3)
        rbList.add(radioButton4)
        rlHeaderContainer.bringToFront()

        imageOptionList.add(rdImage1)
        imageOptionList.add(rdImage2)
        imageOptionList.add(rdImage3)
        imageOptionList.add(rdImage4)


        radioButton1.setOnClickListener {
            radioButton1.isChecked = true
            radioButton2.isChecked = false
            radioButton3.isChecked = false
            radioButton4.isChecked = false
        }
        radioButton2.setOnClickListener{
            radioButton1.isChecked = false
            radioButton2.isChecked = true
            radioButton3.isChecked = false
            radioButton4.isChecked = false
        }
        radioButton3.setOnClickListener {
            radioButton1.isChecked = false
            radioButton2.isChecked = false
            radioButton3.isChecked = true
            radioButton4.isChecked = false
        }
        radioButton4.setOnClickListener {
            radioButton1.isChecked = false
            radioButton2.isChecked = false
            radioButton3.isChecked = false
            radioButton4.isChecked = true
        }
        btnCheckAnswer.setOnClickListener {

            if(optionClicked())
            {
                showAnswerStatus()
            }
            else
            {
                showLongToast("Please select any option to continue",context!!)
            }


        }
    }

    override fun submitAnswer()
    {

        resetUI()
        isOptionCorrect()
        questionCount++
        currentQuestion++
        if(!isCompleted)
            updateQuestion(quizDataList[questionCount])
        else
        {
            conceptQuizActivity.showChapterQuizResultFragment(userCorrectAnswer,totalQuestion)
        }
        if(currentQuestion == totalQuestion)
            isCompleted = true
    }



    private fun showAnswerStatus()
    {
        timeTaken = (SystemClock.elapsedRealtime()-mChronometer.base)-questionStartTime
        Log.d(Constants.LOG_TAG,"Time Taken $timeTaken")
        stopTimer()
        var isAnswerCorrect = quizDataList[questionCount].choices_data[mSelectedOptionIndex].correct_answer
        var correctAnswer = ""
        var imageUrl = ""
        quizDataList[questionCount].choices_data.forEach {
            if(it.correct_answer)
            {
                correctAnswer = it.options
                imageUrl = it.image
            }
        }

        var ft = childFragmentManager.beginTransaction()
        var prev = childFragmentManager.findFragmentByTag("dialog")
        if(prev!=null)
            ft.remove(prev)
        ft.addToBackStack(null)

        var dialogFragment = QuizOptionResultDialogFragment(this,correctAnswer,isAnswerCorrect,imageUrl)
        dialogFragment.show(ft,"dialog")
    }

    private fun isOptionCorrect() {
        val isAnswerCorrect = quizDataList[questionCount].choices_data[mSelectedOptionIndex].correct_answer
        if(isAnswerCorrect)
            userCorrectAnswer++
        var currentQuizData = quizDataList[questionCount]
        var currentChoiceData = currentQuizData.choices_data[mSelectedOptionIndex]
        presenter.saveQuiz(studentId,quizId,currentQuizData.question_id,currentQuestion,currentChoiceData.choice_id,timeTaken.toInt(),isAnswerCorrect,isCompleted)
        showAnswerStatus(isAnswerCorrect)
    }

    private fun optionClicked(): Boolean {
        var isChecked = false
        rbList.forEachIndexed { index, radioButton ->
            if(radioButton.isChecked)
            {
                isChecked = true
                mSelectedOptionIndex = index
            }
        }
        return isChecked
    }

    private fun resetUI() {
        rbList.forEach {
            it.visibility = View.GONE
            it.isChecked = false
        }
        imageOptionList.forEach {
            it.visibility = View.GONE
        }
        ivQuestionImage.visibility = View.GONE
    }

    private fun injectDependency() {
        val fragmentComponent = DaggerFragmentComponent.builder()
            .fragmentModule(FragmentModule())
            .sharedPreferenceModule(SharedPreferenceModule())
            .contextModule(ContextModule(context!!))
            .build()
        fragmentComponent.inject(this)

    }
    companion object {
        const val TAG = "ChapterQuizFragment"
    }
    override fun showProgress(show: Boolean) {
        showLoadingProgress(progressBar,show)
    }
    override fun onQuestionLoadSucess(chapterQuizModel: ChapterQuizModel) {
        Log.d(Constants.LOG_TAG,chapterQuizModel.toString())
        this.quizId = chapterQuizModel.quiz_id
        this.quizDataList = chapterQuizModel.quiz_data
        this.resultData = chapterQuizModel.result_data
        this.currentQuestion = chapterQuizModel.current
        if(!resultData.isEmpty())
        {
           // questionCount += chapterQuizModel.current
            resultData.forEach {
                if(it.correct)
                    userCorrectAnswer++
            }
        }
        this.quizDataList.forEach {
            updateQuestion(it)
        }

      //  updateQuestion(questionCount)
    }

    private fun startTimer()
    {

        mChronometer.base = SystemClock.elapsedRealtime() + timeWhenStopped
        mChronometer.start()
    }
    private fun stopTimer()
    {
        timeWhenStopped = mChronometer.base - SystemClock.elapsedRealtime()
        mChronometer.stop()
    }

    private fun changeQuestionNumberStatus(textView : TextView, isCorrect: Boolean)
    {
        textView.isSelected = isCorrect
        textView.isEnabled = isCorrect
    }
    private fun showAnswerStatus(isCorrect: Boolean) {

        when(questionCount)
        {
            0->changeQuestionNumberStatus(tvNum1,isCorrect)
            1->changeQuestionNumberStatus(tvNum2,isCorrect)
            2->changeQuestionNumberStatus(tvNum3,isCorrect)
            3->changeQuestionNumberStatus(tvNum4,isCorrect)
            4->changeQuestionNumberStatus(tvNum5,isCorrect)
            5->changeQuestionNumberStatus(tvNum6,isCorrect)
            6->changeQuestionNumberStatus(tvNum7,isCorrect)
            7->changeQuestionNumberStatus(tvNum8,isCorrect)
            8->changeQuestionNumberStatus(tvNum9,isCorrect)
            9->changeQuestionNumberStatus(tvNum10,isCorrect)
            10->changeQuestionNumberStatus(tvNum11,isCorrect)
            11->changeQuestionNumberStatus(tvNum12,isCorrect)
            12->changeQuestionNumberStatus(tvNum13,isCorrect)
            13->changeQuestionNumberStatus(tvNum14,isCorrect)
            14->changeQuestionNumberStatus(tvNum15,isCorrect)

        }
    }

    override fun onQuestionLoadFailed(msg: String) {
        Log.d(Constants.LOG_TAG,msg)
    }

    override fun updateQuestion(quizData: QuizData)
    {
        startTimer()
        questionStartTime = SystemClock.elapsedRealtime() - mChronometer.base
        tvQuestionNumber.text = context!!.getString(R.string.QuestionNumber,currentQuestion,totalQuestion)
        tvQuestion.text = Jsoup.parse(quizData.question_text).text()
        if(!quizData.question_image.isEmpty())
        {
            ivQuestionImage.visibility = View.VISIBLE
        }
        quizData.choices_data.forEachIndexed { index, choicesData ->
            rbList[index].visibility = View.VISIBLE
            rbList[index].text = Jsoup.parse(choicesData.options).text()
            if(!choicesData.image.isEmpty())
            {
                imageOptionList[index].visibility = View.VISIBLE
            }
        }

    }

   /* override fun updateQuestion(qNumber: Int) {

        Log.d(Constants.LOG_TAG,quizDataList.toString())
        startTimer()
        questionStartTime = SystemClock.elapsedRealtime() - mChronometer.base
        Log.d(Constants.LOG_TAG,questionStartTime.toString())
        tvQuestionNumber.text = context!!.getString(R.string.QuestionNumber,questionCount+1,15)
        var quizData = quizDataList[qNumber]
        tvQuestion.text = Jsoup.parse(quizData.question_text).text()
        if(!quizData.question_image.isEmpty())
        {
            ivQuestionImage.visibility = View.VISIBLE
        }
        quizData.choices_data.forEachIndexed { index, choicesData ->
            rbList[index].visibility = View.VISIBLE
            rbList[index].text = Jsoup.parse(choicesData.options).text()


            if(!choicesData.image.isEmpty())
            {
                imageOptionList[index].visibility = View.VISIBLE
            }
        }
    }*/

}
class QuizOptionResultDialogFragment(var chapterQuizView : ChapterQuizContract.View,var correctAnswer : String,var isCorrect : Boolean,var image_url : String) : DialogFragment(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.dialog_concept_quiz_option_result,container,false)
        view.btnNext.setOnClickListener {
            dialog.dismiss()
        }
        view.tvCorrectAnswer.text = Jsoup.parse(correctAnswer).text()
        if(isCorrect)
        {
            view.tvAnswerHeader.text = "Correct Answer"
            view.tvAnswerHeader.setBackgroundColor(Color.GREEN)
        }
        else
        {
            view.tvAnswerHeader.text = "InCorrect Answer"
            view.tvAnswerHeader.setBackgroundColor(Color.RED)
        }
        if(!image_url.isEmpty())
        {
            view.ivCorrectImage.visibility = View.VISIBLE
        }
        else
            view.ivCorrectImage.visibility = View.GONE

        return view

    }

    override fun onPause() {
        super.onPause()
        Log.d(Constants.LOG_TAG,"Paused")
        chapterQuizView.submitAnswer()
    }

    override fun onStart() {
        super.onStart()
        dialog.window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
    }

}


package com.carveniche.wisdomleap.view.fragment


import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.core.animation.addListener

import com.carveniche.wisdomleap.R
import com.carveniche.wisdomleap.contract.MultiplayerPlayQuizContract
import com.carveniche.wisdomleap.di.component.DaggerFragmentComponent
import com.carveniche.wisdomleap.di.module.ContextModule
import com.carveniche.wisdomleap.di.module.FragmentModule
import com.carveniche.wisdomleap.di.module.SharedPreferenceModule
import com.carveniche.wisdomleap.model.QuizQuestionModel
import com.carveniche.wisdomleap.util.Constants
import com.carveniche.wisdomleap.util.showLoadingProgress
import kotlinx.android.synthetic.main.fragment_mutliplayer_quiz_play.*
import kotlinx.android.synthetic.main.layout_progressbar.*
import org.jsoup.Jsoup
import javax.inject.Inject

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.Color
import android.view.animation.Animation

import android.widget.TextView
import androidx.core.content.ContextCompat
import com.airbnb.lottie.LottieAnimationView
import com.carveniche.wisdomleap.model.MySharedPreferences
import com.carveniche.wisdomleap.view.activity.MultiPlayerQuizActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.Observables
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_multiplayer_search.*
import java.util.*
import java.util.concurrent.TimeUnit


class MultiplayerQuizPlayFragment : Fragment(),MultiplayerPlayQuizContract.View,View.OnClickListener {

    private lateinit var rootView : View
    @Inject
    lateinit var presenter : MultiplayerPlayQuizContract.Presenter
    var disposable = CompositeDisposable()

    @Inject
    lateinit var mySharedPreferences: MySharedPreferences

    private lateinit var quizQuestionModel: QuizQuestionModel
    var mCurrentOptions = mutableListOf<String>()
    var currentQuestionNumber  = -1
    var mCorrectAnswer  = ""
    private lateinit var progressAnimator : ObjectAnimator
    private lateinit var multiPlayerQuizActivity: MultiPlayerQuizActivity
    private var userScore = 0
    private var opponentScore = 0
    private var isUserClickEnabled = true
    private var mPlayerName =  ""
    private var mOpponentName = ""
    private lateinit var rightAnswerAnimator : ObjectAnimator
    private var opponentAvatar = 0
    private var avatarList = Constants.getAvatarList()
    private var questionDuration : Long = 5000
    private var quizCategory = 0
    private var quizLevel = Constants.EASY
    private lateinit var opponentAnswerAnimator : ObjectAnimator
    private lateinit var animationView : LottieAnimationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
        multiPlayerQuizActivity = activity as MultiPlayerQuizActivity
        mOpponentName = arguments!!.getString(Constants.OPPONENT_NAME)
        mPlayerName = mySharedPreferences.getString(Constants.FIRST_NAME)+" "+mySharedPreferences.getString(Constants.LAST_NAME)
        opponentAvatar = arguments!!.getInt(Constants.OPPONENT_AVATAR)
        quizCategory = arguments!!.getInt(Constants.QUIZ_CATEGORY)
        quizLevel = arguments!!.getString(Constants.QUIZ_LEVEL)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_mutliplayer_quiz_play, container, false)
        return rootView
    }
    override fun questionLoadSuccess(quizQuestionModel: QuizQuestionModel) {
        this.quizQuestionModel = quizQuestionModel
        animationView.cancelAnimation()
    }

    override fun questionLoadFailed(msg: String) {
        Log.d(Constants.LOG_TAG,msg)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        updateUserScore()
        updateOpponentScore()
    }

    private fun initUI() {
        animationView = animation_view

        initLoadQuestionAnimation()
        initProgressBarAnimation()

        presenter.attach(this)
        presenter.subscribe()
        presenter.loadQuizQuestions(15,quizCategory,quizLevel,"multiple")


        tv_option_1.setOnClickListener {
            if(isUserClickEnabled)
            {
                presenter.verifyAnswer(it,tv_option_1.text.toString(),mCorrectAnswer)
                disableOptions()
            }
        }
        tv_option_2.setOnClickListener {
            if(isUserClickEnabled)
            {
                presenter.verifyAnswer(it,tv_option_2.text.toString(),mCorrectAnswer)
                disableOptions()
            }
        }
        tv_option_3.setOnClickListener {
            if(isUserClickEnabled)
            {
                presenter.verifyAnswer(it,tv_option_3.text.toString(),mCorrectAnswer)
                disableOptions()
            }
        }
        tv_option_4.setOnClickListener {
            if(isUserClickEnabled)
            {
                presenter.verifyAnswer(it,tv_option_4.text.toString(),mCorrectAnswer)
                disableOptions()
            }
        }

        tv_oppoent_name.text = mOpponentName
        if(!mPlayerName.isBlank())
            tv_user_name.text = mPlayerName
        else
            tv_user_name.text = "Guest Player"

        iv_opponent_avatar.setBackgroundResource(avatarList[opponentAvatar])
    }

    private fun disableOptions()
    {
       isUserClickEnabled = false
    }

    override fun showAnswerStatus(tvOption: View, isCorrect: Boolean) {
            if(isCorrect)
            {
                tvOption.setBackgroundColor(Color.GREEN)
                userScore++
                updateUserScore()
            }
        else
            {
                tvOption.setBackgroundColor(Color.RED)
                markRightAnswer()
            }
    }

    private fun markRightAnswer()
    {
        when (mCorrectAnswer) {
            tv_option_1.text.toString() -> highLightRightAnswer(tv_option_1)
            tv_option_2.text.toString() -> highLightRightAnswer(tv_option_2)
            tv_option_3.text.toString() -> highLightRightAnswer(tv_option_3)
            tv_option_4.text.toString() -> highLightRightAnswer(tv_option_4)
        }

    }

    private fun highLightRightAnswer(textView: TextView)
    {
         rightAnswerAnimator = ObjectAnimator.ofInt(textView,"backgroundColor",Color.GREEN,android.R.color.transparent,Color.GREEN)
        rightAnswerAnimator.duration = 1000
        rightAnswerAnimator.setEvaluator(ArgbEvaluator())
        rightAnswerAnimator.repeatMode = ValueAnimator.REVERSE
        rightAnswerAnimator.repeatCount = Animation.INFINITE
        rightAnswerAnimator.start()
    }
    private fun highLightOpponentAnswer(isCorrect : Boolean)
    {
        var highColor = if(isCorrect)
            Color.GREEN
        else
            Color.RED
         opponentAnswerAnimator = ObjectAnimator.ofInt(tv_opponent_score,"backgroundColor",highColor,android.R.color.transparent,highColor)
        opponentAnswerAnimator.duration = 1000
        opponentAnswerAnimator.setEvaluator(ArgbEvaluator())
        opponentAnswerAnimator.repeatMode = ValueAnimator.REVERSE
        opponentAnswerAnimator.repeatCount = 2

        opponentAnswerAnimator.start()
    }


    private fun updateUserScore() {
        tv_user_score.text = "Score $userScore"
    }
    private fun updateOpponentScore()
    {
        tv_opponent_score.text =  "Score $opponentScore"
    }

    private fun injectDependency() {
        val fragmentComponent = DaggerFragmentComponent.builder()
            .fragmentModule(FragmentModule())
            .sharedPreferenceModule(SharedPreferenceModule())
            .contextModule(ContextModule(context!!))
            .build()
        fragmentComponent.inject(this)

    }
     fun updateQuestion() {

         resetViews()
         currentQuestionNumber++
         Log.d(Constants.LOG_TAG,"Ques number $currentQuestionNumber")
         if(currentQuestionNumber>14)
         {
             disposable.clear()
             stopQuizPlay()
             return
         }
         mCurrentOptions.clear()
         var result = quizQuestionModel.results[currentQuestionNumber]
        tv_question.text = Jsoup.parse(result.question).text()
         mCorrectAnswer =  Jsoup.parse(result.correct_answer+".").text()
         mCurrentOptions.add(mCorrectAnswer)
         result.incorrect_answers.forEach {
             mCurrentOptions.add(Jsoup.parse(it).text())
         }
         mCurrentOptions.shuffle()
         tv_option_1.text = mCurrentOptions[0]
         tv_option_2.text = mCurrentOptions[1]
         tv_option_3.text = mCurrentOptions[2]
         tv_option_4.text = mCurrentOptions[3]
         machinePlay()
    }

    private fun resetViews() {
        isUserClickEnabled = true
        tv_option_1.setBackgroundColor(ContextCompat.getColor(context!!,R.color.primaryDarkColor))
        tv_option_2.setBackgroundColor(ContextCompat.getColor(context!!,R.color.primaryDarkColor))
        tv_option_3.setBackgroundColor(ContextCompat.getColor(context!!,R.color.primaryDarkColor))
        tv_option_4.setBackgroundColor(ContextCompat.getColor(context!!,R.color.primaryDarkColor))
        if(::rightAnswerAnimator.isInitialized && rightAnswerAnimator.isRunning)
            rightAnswerAnimator.cancel()

        if(::opponentAnswerAnimator.isInitialized)
            opponentAnswerAnimator.cancel()
        tv_opponent_score.setBackgroundColor(resources.getColor(R.color.primaryLightColor))

    }

    private fun stopQuizPlay() {
        var isPlayerWin = userScore > opponentScore
        multiPlayerQuizActivity.showMultiplayerQuizResult(mOpponentName,isPlayerWin,opponentAvatar)
    }




    fun initProgressBarAnimation()
    {
        progressAnimator = ObjectAnimator.ofInt(pb_loading,"progress",100,0)
        progressAnimator.duration = questionDuration
        progressAnimator.interpolator = DecelerateInterpolator()
        progressAnimator.addListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                startLoadQuestionAnimatiom()
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationStart(animation: Animator?) {

            }

        })

    }

    fun initLoadQuestionAnimation()
    {
        animationView.setAnimation("anim_load_data.json")
        animationView.loop(true)
        animationView.addAnimatorListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                animationView.visibility = View.GONE
                updateQuestion()
                progressAnimator.start()
            }

            override fun onAnimationCancel(animation: Animator?) {
                animationView.visibility = View.GONE
                updateQuestion()
                progressAnimator.start()
            }

            override fun onAnimationStart(animation: Animator?) {

            }

        })

    }

    private fun machinePlay() {
        val answerDelay = kotlin.random.Random.nextLong(2000,questionDuration-2000)

       var sub =  Observable.timer(answerDelay, TimeUnit.MILLISECONDS)
            .map<Boolean> {
                return@map Random().nextBoolean()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {

                if(it)
                {
                    opponentScore++
                    tv_opponent_score.text = "Score $opponentScore"
                }
                highLightOpponentAnswer(it)
            }
        disposable.add(sub)

    }

    override fun onClick(v: View?) {

    }
    override fun showProgress(show: Boolean) {
       if(show)
       {
           animationView.visibility = View.VISIBLE
           animationView.bringToFront()
           animationView.playAnimation()
       }
    }
    companion object {
        const val TAG = "MultiplayerQuizPlayFragment"
    }


    fun stopLoadQuestionAnimation()
    {
        animationView.cancelAnimation()
    }
    fun startLoadQuestionAnimatiom()
    {
        animationView.repeatCount = 0
        animationView.visibility = View.VISIBLE
        animationView.bringToFront()
        animationView.playAnimation()
    }

}

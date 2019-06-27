package com.carveniche.wisdomleap.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.carveniche.wisdomleap.R
import com.carveniche.wisdomleap.api.ApiInterface
import com.carveniche.wisdomleap.model.ChapterQuizModel
import com.carveniche.wisdomleap.model.QuizData
import com.carveniche.wisdomleap.util.Constants
import com.carveniche.wisdomleap.util.showtMathView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_referral.*

class ReferralActivity : AppCompatActivity() {

    val numbers: IntArray = intArrayOf(2577, 2578, 2579, 2583, 2584, 2586, 2587, 2588, 2589, 2591, 2592, 2594, 2595, 2596, 2597, 2598, 2600, 2601, 2606, 2609, 2612, 2613, 2614, 2616, 2617, 2618, 2619, 2620, 2670, 2678, 2683, 2705, 2706, 2708, 2714, 2726, 2788, 2790, 2791, 2792, 2793, 2794, 2799, 2800, 2801, 2802, 2804, 2806, 2807, 2808, 2809, 2810, 2811, 2812, 2815, 4954)
    var qCount = 0
    private var api = ApiInterface.create()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_referral)
        initUI()
    }

    private fun initUI() {
        getTestQuestion()
        btn_next.setOnClickListener {
            qCount++
            getTestQuestion()
        }
    }
    fun getTestQuestion()
    {
        api.getTestQuestions(numbers[qCount]).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                displayValues(it.quiz_data[0])
            },{
                Log.d(Constants.LOG_TAG,"Err  ${it.localizedMessage}")
            })
    }

    private fun displayValues(quizData: QuizData) {
        Log.d(Constants.LOG_TAG,quizData.toString())
       showtMathView(wv_question,quizData.question_text)
        showtMathView(wv_option_1,quizData.choices_data[0].options)
        showtMathView(wv_option_2,quizData.choices_data[1].options)
        showtMathView(wv_option_3,quizData.choices_data[2].options)
        showtMathView(wv_option_4,quizData.choices_data[3].options)
    }

}

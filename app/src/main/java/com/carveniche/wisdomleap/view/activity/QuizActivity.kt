package com.carveniche.wisdomleap.view.activity


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.carveniche.wisdomleap.R
import com.carveniche.wisdomleap.view.fragment.QuizQuestionFragment
import com.carveniche.wisdomleap.view.fragment.QuizResultFragment

class QuizActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        showQuizQuestionFragment()
    }

    fun showQuizQuestionFragment()
    {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.frame,QuizQuestionFragment(),QuizQuestionFragment.TAG)
            .commit()
    }
    fun showQuizResultFragment()
    {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.frame,QuizResultFragment(),QuizResultFragment.TAG)
            .commit()
    }
}

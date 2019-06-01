package com.carveniche.wisdomleap.view.activity


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.carveniche.wisdomleap.R
import com.carveniche.wisdomleap.util.Constants
import com.carveniche.wisdomleap.view.fragment.QuizQuestionFragment
import com.carveniche.wisdomleap.view.fragment.QuizResultFragment

class QuizActivity : AppCompatActivity() {

    private var categoryId =0
    private lateinit var level : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        categoryId = intent.getIntExtra(Constants.QUIZ_CATEGORY,0)
        level = intent.getStringExtra(Constants.QUIZ_LEVEL)
        showQuizQuestionFragment()

    }

    fun showQuizQuestionFragment()
    {
        var ldf = QuizQuestionFragment()
        var args = Bundle()
        args.putInt(Constants.QUIZ_CATEGORY,categoryId)
        args.putString(Constants.QUIZ_LEVEL,level)
        ldf.arguments = args
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.frame,ldf,QuizQuestionFragment.TAG)
            .commit()
    }
    fun showQuizResultFragment(score : Int,timeTaken : Int)
    {
        var ldf = QuizResultFragment()
        var args = Bundle()
        args.putInt(Constants.QUIZ_SCORE,score)
        args.putInt(Constants.QUIZ_CATEGORY,categoryId)
        args.putString(Constants.QUIZ_LEVEL,level)
        args.putInt(Constants.TIME_TAKEN,timeTaken)
        ldf.arguments = args
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.frame,ldf,QuizResultFragment.TAG)
            .commit()
    }

    override fun onBackPressed() {
        Log.d(Constants.LOG_TAG,supportFragmentManager.backStackEntryCount.toString())
        var count = supportFragmentManager.backStackEntryCount
       when(count)
       {
           1->{
               exitQuiz()
           }
           2->{
               goToMainActivity()
           }
       }
    }

    private fun exitQuiz()
    {
        var builder = AlertDialog.Builder(this)
        builder.setTitle("Exit Quiz")
        builder.setMessage("are you sure want to exit the quiz right now?")
        builder.setPositiveButton("Yes"){dialog, which ->
            goToMainActivity()
        }
        builder.setNegativeButton("No"){dialog, which ->
            dialog.dismiss()
        }
        val dialog : AlertDialog = builder.create()
        dialog.show()
    }
    private fun goToMainActivity()
    {
        var intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }


}

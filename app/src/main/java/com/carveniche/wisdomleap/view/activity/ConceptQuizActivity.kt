package com.carveniche.wisdomleap.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.carveniche.wisdomleap.R
import com.carveniche.wisdomleap.util.Constants
import com.carveniche.wisdomleap.view.fragment.ChapterQuizFragment
import com.carveniche.wisdomleap.view.fragment.ChapterQuizResultFragment
import com.carveniche.wisdomleap.view.fragment.QuizHomeFragment

class ConceptQuizActivity : AppCompatActivity() {

    private var studentId = 0
    private var courseId = 0
    private var chapterId = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_concept_quiz)
        studentId = intent.getIntExtra(Constants.STUDENT_ID,0)
        courseId = intent.getIntExtra(Constants.COURSE_ID,0)
        chapterId = intent.getIntExtra(Constants.CONCEPT_ID,0)
        showChapterQuizFragment()

    }

     private fun showChapterQuizFragment()
    {
        var ldf = ChapterQuizFragment()
        var args = Bundle()
        args.putInt(Constants.STUDENT_ID,studentId)
        args.putInt(Constants.COURSE_ID,courseId)
        args.putInt(Constants.CONCEPT_ID,chapterId)
        ldf.arguments = args
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.container,ldf,ChapterQuizFragment.TAG)
            .commit()
    }
    public fun showChapterQuizResultFragment(correctAnswer : Int,totalQuestion : Int)
    {
        var ldf = ChapterQuizResultFragment()
        var args = Bundle()
        args.putInt(Constants.TOTAL_QUIZ_QUESTION,totalQuestion)
        args.putInt(Constants.QUIZ_SCORE,correctAnswer)
        ldf.arguments  =args
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.container,ldf,ChapterQuizResultFragment.TAG)
            .commit()
    }

    override fun onBackPressed() {
        var count = supportFragmentManager.backStackEntryCount
        when(count)
        {
            1->{
                exitQuiz()
            }
            2->{
                goToSubjectActivity()
            }
        }
    }
    private fun exitQuiz()
    {
        var builder = AlertDialog.Builder(this)
        builder.setTitle("Exit Quiz")
        builder.setMessage("are you sure want to exit the quiz right now?")
        builder.setPositiveButton("Yes"){dialog, which ->
            goToSubjectActivity()
        }
        builder.setNegativeButton("No"){dialog, which ->
            dialog.dismiss()
        }
        val dialog : AlertDialog = builder.create()
        dialog.show()
    }
    private fun goToSubjectActivity()
    {
        var intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }
}

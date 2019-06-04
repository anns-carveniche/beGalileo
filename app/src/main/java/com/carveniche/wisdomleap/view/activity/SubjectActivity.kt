package com.carveniche.wisdomleap.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.carveniche.wisdomleap.R
import com.carveniche.wisdomleap.util.Constants
import com.carveniche.wisdomleap.view.fragment.ListChaptersFragment
import com.carveniche.wisdomleap.view.fragment.ListSubjectFragment

class SubjectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subject)
        var courseId = intent.getIntExtra(Constants.COURSE_ID,0)
        //showListSubjectFragment()
        showListChapterFragment(courseId)

    }

     fun showListSubjectFragment() {

        supportFragmentManager.beginTransaction()
            .addToBackStack(ListSubjectFragment.TAG)
            .replace(R.id.frame,ListSubjectFragment(),ListSubjectFragment.TAG)
            .commit()
    }
    fun showListChapterFragment(courseId : Int)
    {
        var ldf = ListChaptersFragment()
        var arguments = Bundle()
        arguments.putInt(Constants.COURSE_ID,courseId)
        ldf.arguments = arguments
        supportFragmentManager.beginTransaction()
            .addToBackStack(ListChaptersFragment.TAG)
            .replace(R.id.frame,ldf,ListChaptersFragment.TAG)
            .commit()
    }

    override fun onBackPressed() {
        var count = supportFragmentManager.backStackEntryCount
        when(count)
        {
            1->{
                var intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
            }
            2->{
               super.onBackPressed()
            }
        }
    }

}

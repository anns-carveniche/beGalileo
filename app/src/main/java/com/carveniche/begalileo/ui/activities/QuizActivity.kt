package com.carveniche.begalileo.ui.activities

import android.Manifest
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.carveniche.begalileo.R
import com.carveniche.begalileo.ui.fragments.QuizHomeFragment
import com.carveniche.begalileo.ui.fragments.QuizPlayFragment
import com.carveniche.begalileo.ui.fragments.QuizResultFragment
import pub.devrel.easypermissions.EasyPermissions

class QuizActivity : AppCompatActivity() {
     var listRoughBitmap = mutableListOf<Bitmap>()
    lateinit var verifiedImage : Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        showQuizPlayFragment()
    }

     fun showQuizPlayFragment() {
        supportFragmentManager.beginTransaction()
            .addToBackStack(QuizPlayFragment.TAG)
            .replace(R.id.frame,
                QuizPlayFragment(),
                QuizPlayFragment.TAG)
            .commit()
    }
     fun showQuizHomeFragment() {
        supportFragmentManager.beginTransaction()
            .addToBackStack(QuizHomeFragment.TAG)
            .replace(R.id.frame,
                QuizHomeFragment(),
                QuizHomeFragment.TAG)
            .commit()
    }

    fun showQuizResultFragment() {
        supportFragmentManager.beginTransaction()
            .addToBackStack(QuizResultFragment.TAG)
            .replace(R.id.frame,
                QuizResultFragment(),
                QuizResultFragment.TAG)
            .commit()
    }

    override fun onBackPressed() {
        finish()
    }


}

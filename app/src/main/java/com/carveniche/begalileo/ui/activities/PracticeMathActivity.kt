package com.carveniche.begalileo.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.carveniche.begalileo.R
import com.carveniche.begalileo.ui.fragments.PracticeMathHomeFragment

class PracticeMathActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practice_math)
        showPracticeMathHomeFragment()
    }

    private fun showPracticeMathHomeFragment() {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.frame,PracticeMathHomeFragment(),PracticeMathHomeFragment.TAG)
            .commit()
    }
}

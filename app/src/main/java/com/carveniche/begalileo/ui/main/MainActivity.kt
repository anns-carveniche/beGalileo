package com.carveniche.begalileo.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.carveniche.begalileo.R
import com.carveniche.begalileo.di.component.DaggerActivityComponent

import com.carveniche.begalileo.di.module.ActivityModule
import com.carveniche.begalileo.di.module.ContextModule
import com.carveniche.begalileo.di.module.SharedPreferenceModule
import com.carveniche.begalileo.ui.home.HomeFragment
import com.carveniche.begalileo.ui.practical.PracticeFragment

import javax.inject.Inject


class MainActivity : AppCompatActivity(),MainContract.View {



    @Inject lateinit var presenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        injectDependency()
        presenter.attach(this)
    }

    private fun injectDependency() {
     var activityComponent = DaggerActivityComponent.builder()
         .activityModule(ActivityModule(this))
         .sharedPreferenceModule(SharedPreferenceModule())
         .contextModule(ContextModule(this))
         .build()
        activityComponent.inject(this)
    }

    override fun showHomeFragment() {
            supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.frame,HomeFragment().newInstance(),HomeFragment.TAG)
                .commit()
    }
    override fun showPracticeFragment() {

        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.frame,PracticeFragment().newInstance(),PracticeFragment.TAG)
            .commit()

    }
    override fun showProgress(boolean: Boolean) {

    }

}

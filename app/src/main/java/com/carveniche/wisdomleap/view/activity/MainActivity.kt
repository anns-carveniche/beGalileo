package com.carveniche.wisdomleap.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.carveniche.wisdomleap.R
import com.carveniche.wisdomleap.contract.MainContract
import com.carveniche.wisdomleap.di.component.DaggerActivityComponent
import com.carveniche.wisdomleap.di.module.ActivityModule
import com.carveniche.wisdomleap.di.module.ContextModule
import com.carveniche.wisdomleap.di.module.SharedPreferenceModule
import com.carveniche.wisdomleap.util.showLoadingProgress
import com.carveniche.wisdomleap.view.fragment.DashboardFragment
import com.carveniche.wisdomleap.view.fragment.ProfileHomeFragment
import com.carveniche.wisdomleap.view.fragment.QuizHomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_progressbar.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(),MainContract.View{

    @Inject lateinit var presenter : MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState==null)
            showDashboardFragment()
        injectDependency()
        initUI()
    }

    private fun initUI() {
        bottomNavigationView.itemIconTintList = null
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun showProgress(status: Boolean) {
        showLoadingProgress(progressBar,status)
    }
    private fun injectDependency() {
        val activityComponent = DaggerActivityComponent.builder()
            .activityModule(ActivityModule(this))
            .sharedPreferenceModule(SharedPreferenceModule())
            .contextModule(ContextModule(this))
            .build()
        activityComponent.inject(this)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
        when(it.itemId)
        {
            R.id.main_nav_home->{
                showDashboardFragment()
                return@OnNavigationItemSelectedListener true
            }
            R.id.main_nav_quiz->{
                showQuizFragment()
                return@OnNavigationItemSelectedListener true
            }
            R.id.main_nav_profile->{
                showProfileFragment()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
    override fun showDashboardFragment() {
       supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.container,DashboardFragment(),DashboardFragment.TAG)
           .commit()
    }

    override fun showQuizFragment() {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.container,QuizHomeFragment(),QuizHomeFragment.TAG)
            .commit()
    }

    override fun showProfileFragment() {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.container,ProfileHomeFragment(),ProfileHomeFragment.TAG)
            .commit()
    }


}

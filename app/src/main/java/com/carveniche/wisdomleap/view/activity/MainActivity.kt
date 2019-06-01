package com.carveniche.wisdomleap.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import com.carveniche.wisdomleap.R
import com.carveniche.wisdomleap.contract.MainContract
import com.carveniche.wisdomleap.di.component.DaggerActivityComponent
import com.carveniche.wisdomleap.di.module.ActivityModule
import com.carveniche.wisdomleap.di.module.ContextModule
import com.carveniche.wisdomleap.di.module.SharedPreferenceModule
import com.carveniche.wisdomleap.model.MySharedPreferences
import com.carveniche.wisdomleap.util.Constants
import com.carveniche.wisdomleap.util.DailyQuotes
import com.carveniche.wisdomleap.util.showLoadingProgress
import com.carveniche.wisdomleap.view.fragment.DashboardFragment
import com.carveniche.wisdomleap.view.fragment.ProfileHomeFragment
import com.carveniche.wisdomleap.view.fragment.QuizHomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_progressbar.*
import kotlinx.android.synthetic.main.nav_header.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(),MainContract.View{

    @Inject lateinit var presenter : MainContract.Presenter
    @Inject lateinit var mySharedPreferences: MySharedPreferences
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var mNavigationView : NavigationView
    private lateinit var headerView : View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState==null)
            showDashboardFragment()
        injectDependency()
        initUI()
        setDrawerHeaderDetails()
    }

    private fun initUI() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        toolbar.title = ""
        bottomNavigationView.itemIconTintList = null
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        toggle = ActionBarDrawerToggle(this,drawer_layout,toolbar,R.string.nav_drawer_open,R.string.nav_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        toggle.isDrawerIndicatorEnabled = true

        mNavigationView = navigationView
        headerView = navigationView.getHeaderView(0)


    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
        toggle.syncState()
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


    fun setDrawerHeaderDetails()
    {
        var imageHeaderView = headerView.findViewById<ImageView>(R.id.iv_header_profile_image)
        var tvUserName = headerView.findViewById<TextView>(R.id.tv_header_user_name)
        var tvUserEmail = headerView.findViewById<TextView>(R.id.tv_header_user_email)
        var imageId = mySharedPreferences.getIntData(Constants.AVATAR_IMAGE_ID)
        var userName = mySharedPreferences.getString(Constants.FIRST_NAME)+" "+mySharedPreferences.getString(Constants.LAST_NAME)
        var userEmail = mySharedPreferences.getString(Constants.EMAIL)
        tvUserName.text = userName
        tvUserEmail.text = userEmail
        if(imageId==0)
        {
            imageHeaderView.setImageDrawable(getDrawable(R.drawable.ic_profile))
        }
        else
        {
            imageHeaderView.setImageDrawable(getDrawable(imageId))
        }
    }


    override fun onBackPressed() {

    }


}
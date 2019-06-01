package com.carveniche.wisdomleap.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.carveniche.wisdomleap.R
import com.carveniche.wisdomleap.di.component.DaggerActivityComponent
import com.carveniche.wisdomleap.di.module.ActivityModule
import com.carveniche.wisdomleap.di.module.ContextModule
import com.carveniche.wisdomleap.di.module.SharedPreferenceModule
import com.carveniche.wisdomleap.model.MySharedPreferences
import com.carveniche.wisdomleap.util.Constants
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    private var mDelayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 3000 //3 seconds

    @Inject
    lateinit var mySharedPreferences: MySharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        injectDependency()
        mDelayHandler = Handler()
        mDelayHandler!!.postDelayed(mRunnable,SPLASH_DELAY)
    }

    private val mRunnable : Runnable = Runnable {
        var isFirstTimeLaunch = mySharedPreferences.getBoolean(Constants.IS_FIRST_TIME_LAUNCH,true)
        if(isFirstTimeLaunch)
            openWelcomeActivity()
        else
            checkIsLoggedIn()
    }

    private fun openWelcomeActivity() {
        mySharedPreferences.putBoolean(Constants.IS_FIRST_TIME_LAUNCH,false)
        var intent = Intent(this,WelcomeActivity::class.java)
        intent.putExtra(Constants.LOGGED_IN,mySharedPreferences.getBoolean(Constants.LOGGED_IN,false))
        startActivity(intent)
    }

    private fun injectDependency() {
        val activityComponent = DaggerActivityComponent.builder()
            .activityModule(ActivityModule(this))
            .sharedPreferenceModule(SharedPreferenceModule())
            .contextModule(ContextModule(this))
            .build()
        activityComponent.inject(this)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if(hasFocus)
            hideSystemUI()
    }

    private fun hideSystemUI() {
        val decorView = window.decorView
        decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                    )
    }

    private fun checkIsLoggedIn() {
        val isLoggedIn  = mySharedPreferences.getBoolean(Constants.LOGGED_IN,false)
        val email = mySharedPreferences.getString(Constants.EMAIL)
        if(isLoggedIn)
        {
            if(email.isEmpty())
            {
                val intent = Intent(this,RegisterActivity::class.java)
                startActivity(intent)
            }
            else
            {
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
            }

        }
        else
        {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}

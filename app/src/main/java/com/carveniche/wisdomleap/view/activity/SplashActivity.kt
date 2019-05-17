package com.carveniche.wisdomleap.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.carveniche.wisdomleap.R
import com.carveniche.wisdomleap.di.component.DaggerActivityComponent
import com.carveniche.wisdomleap.di.module.ActivityModule
import com.carveniche.wisdomleap.di.module.ContextModule
import com.carveniche.wisdomleap.di.module.SharedPreferenceModule
import com.carveniche.wisdomleap.model.MySharedPreferences
import com.carveniche.wisdomleap.util.Constants
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var mySharedPreferences: MySharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        injectDependency()
        checkIsLoggedIn()
    }
    private fun injectDependency() {
        val activityComponent = DaggerActivityComponent.builder()
            .activityModule(ActivityModule(this))
            .sharedPreferenceModule(SharedPreferenceModule())
            .contextModule(ContextModule(this))
            .build()
        activityComponent.inject(this)
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

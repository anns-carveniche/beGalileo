package com.carveniche.begalileo.ui.activities


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.carveniche.begalileo.util.Constants
import com.carveniche.begalileo.R
import com.carveniche.begalileo.di.component.DaggerActivityComponent
import com.carveniche.begalileo.di.module.ActivityModule
import com.carveniche.begalileo.di.module.ContextModule
import com.carveniche.begalileo.di.module.SharedPreferenceModule
import com.carveniche.begalileo.models.MySharedPreferences
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    @Inject lateinit var mySharedPreferences: MySharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        injectDependency()
        checkIsLoggedIn()
    }

    private fun checkIsLoggedIn() {
        val isLoggedIn  = mySharedPreferences.getBoolean(Constants.LOGGED_IN,false)
        if(isLoggedIn)
        {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        else
        {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun injectDependency() {
        val activityComponent = DaggerActivityComponent.builder()
            .activityModule(ActivityModule(this))
            .sharedPreferenceModule(SharedPreferenceModule())
            .contextModule(ContextModule(this))
            .build()
        activityComponent.inject(this)
    }
}

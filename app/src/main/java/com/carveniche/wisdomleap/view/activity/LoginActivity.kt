package com.carveniche.wisdomleap.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.carveniche.wisdomleap.R
import com.carveniche.wisdomleap.model.MySharedPreferences
import com.carveniche.wisdomleap.model.RegisterModel
import com.carveniche.wisdomleap.util.Constants
import com.carveniche.wisdomleap.view.fragment.EnterEmailFragment
import com.carveniche.wisdomleap.view.fragment.EnterOtpFragment
import com.carveniche.wisdomleap.view.fragment.MobileNumberFragment
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        showMobileNumberFragment()
    }

    private fun showMobileNumberFragment() {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.frame,MobileNumberFragment(),MobileNumberFragment.TAG)
            .commit()
    }

    fun showEnterOtpFragment(registerModel: RegisterModel)
    {
        val ldf = EnterOtpFragment()
        var args =Bundle()
        args.putString(Constants.OTP,registerModel.otp.toString())
        args.putString(Constants.MOBILE_NUMBER,registerModel.mobile)
        args.putString(Constants.EMAIL,registerModel.email)
        args.putInt(Constants.STUDENT_ID,registerModel.student_id)
        ldf.arguments  =args

        supportFragmentManager.beginTransaction()
            .addToBackStack(MobileNumberFragment.TAG)
            .replace(R.id.frame,ldf,EnterOtpFragment.TAG)
            .commit()
    }
    fun showEnterEmailScreen() {
        val ldf = EnterEmailFragment()


        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.frame,ldf,EnterEmailFragment.TAG)
            .commit()
    }

    override fun onBackPressed() {
        var count = supportFragmentManager.backStackEntryCount
        when(count)
        {
            1->exitApplication()
            2->super.onBackPressed()
        }
    }

    private fun exitApplication() {

    }


}

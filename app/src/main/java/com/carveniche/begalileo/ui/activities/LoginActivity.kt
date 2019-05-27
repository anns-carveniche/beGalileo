package com.carveniche.begalileo.ui.activities



import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.carveniche.begalileo.util.Constants
import com.carveniche.begalileo.R
import com.carveniche.begalileo.di.component.DaggerActivityComponent
import com.carveniche.begalileo.di.module.ActivityModule
import com.carveniche.begalileo.di.module.ContextModule
import com.carveniche.begalileo.di.module.SharedPreferenceModule
import com.carveniche.begalileo.models.MySharedPreferences
import com.carveniche.begalileo.models.RegisterModel
import com.carveniche.begalileo.ui.fragments.EnterEmailFragment
import com.carveniche.begalileo.ui.fragments.EnterOtpFragment
import com.carveniche.begalileo.ui.fragments.SignInFragment
import com.carveniche.begalileo.contract.LoginContract
import javax.inject.Inject




class LoginActivity : AppCompatActivity(), LoginContract.View {



    private lateinit var registerModel: RegisterModel
    @Inject lateinit var presenter: LoginContract.Presenter
    @Inject lateinit var mySharedPreferences: MySharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        injectDependency()
        presenter.attach(this)
        presenter.showFragment(mySharedPreferences)
    }

    private fun injectDependency() {
        val activityComponent = DaggerActivityComponent.builder()
            .activityModule(ActivityModule(this))
            .sharedPreferenceModule(SharedPreferenceModule())
            .contextModule(ContextModule(this))
            .build()
        activityComponent.inject(this)
    }
    override fun showProgress(boolean: Boolean) {

    }
    override fun showSignInScreen() {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.frame, SignInFragment().newInstance(),
                SignInFragment.TAG)
            .commit()
    }

    override fun showRegisterScreen() {

    }


    override fun showEnterOtpScreen(registerModel: RegisterModel) {
        this.registerModel = registerModel

        val ldf = EnterOtpFragment()
        val args = Bundle()
        args.putString(Constants.OTP,registerModel.otp.toString())
        args.putString(Constants.MOBILE_NUMBER,registerModel.mobile)
        args.putString(Constants.EMAIL,registerModel.email)
        args.putInt(Constants.PARENT_ID,registerModel.parent_id)

        ldf.arguments = args

        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.frame,ldf, EnterOtpFragment.TAG)
            .commit()
    }
    override fun showEnterEmailScreen() {
        val ldf = EnterEmailFragment()
        val args = Bundle()
        args.putString(Constants.MOBILE_NUMBER,mySharedPreferences.getString(Constants.MOBILE_NUMBER))
        args.putString(Constants.EMAIL,mySharedPreferences.getString(Constants.EMAIL))

        ldf.arguments = args

        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.frame,ldf, EnterEmailFragment.TAG)
            .commit()
    }

}

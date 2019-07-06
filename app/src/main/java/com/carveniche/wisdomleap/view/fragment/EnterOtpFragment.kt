package com.carveniche.wisdomleap.view.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.carveniche.wisdomleap.R
import com.carveniche.wisdomleap.contract.EnterOtpContract
import com.carveniche.wisdomleap.di.component.DaggerFragmentComponent
import com.carveniche.wisdomleap.di.module.ContextModule
import com.carveniche.wisdomleap.di.module.FragmentModule
import com.carveniche.wisdomleap.di.module.SharedPreferenceModule
import com.carveniche.wisdomleap.model.MySharedPreferences
import com.carveniche.wisdomleap.util.Constants
import com.carveniche.wisdomleap.util.snackBar
import com.carveniche.wisdomleap.view.activity.LoginActivity
import com.carveniche.wisdomleap.view.activity.MainActivity
import com.carveniche.wisdomleap.view.activity.RegisterActivity
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_enter_otp.*
import kotlinx.android.synthetic.main.layout_progressbar.*
import kotlinx.android.synthetic.main.number_layout.*
import javax.inject.Inject

class EnterOtpFragment : Fragment(),EnterOtpContract.View,View.OnClickListener {

    private  var mGeneratedOtp : String = ""
    private  var mMobileNumber : String = ""
    private  var mStudentId = 0
    private  var mEmail : String = ""
    private var mFirstName = ""
    private var mLastName = ""
    lateinit var rootView: View
    @Inject lateinit var presenter : EnterOtpContract.Presenter
    @Inject lateinit var sharedPreferences: MySharedPreferences
    lateinit var loginActivity: LoginActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
        loginActivity = activity as LoginActivity
    }
    override fun showProgress(show: Boolean) {
        progressBar.bringToFront()
        if(show)
            progressBar.visibility = View.VISIBLE
        else
            progressBar.visibility = View.GONE
    }

    private fun injectDependency() {
        val fragmentComponent = DaggerFragmentComponent.builder()
            .fragmentModule(FragmentModule())
            .sharedPreferenceModule(SharedPreferenceModule())
            .contextModule(ContextModule(context!!))
            .build()
        fragmentComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView =  inflater.inflate(R.layout.fragment_enter_otp,container,false)
        mGeneratedOtp = arguments!!.getString(Constants.OTP)
        mMobileNumber = arguments!!.getString(Constants.MOBILE_NUMBER)
        Log.d(Constants.LOG_TAG,mMobileNumber)
        mStudentId = arguments!!.getInt(Constants.STUDENT_ID)
        if(arguments!!.getString(Constants.EMAIL)!=null)
            mEmail = arguments!!.getString(Constants.EMAIL)
        if(arguments!!.getString(Constants.FIRST_NAME)!=null)
            mFirstName = arguments!!.getString(Constants.FIRST_NAME)
        if(arguments!!.getString(Constants.LAST_NAME)!=null)
            mLastName = arguments!!.getString(Constants.LAST_NAME)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        presenter.attach(this)
        presenter.subscribe()
    }
    override fun onClick(v: View?) {
        when(v!!.id)
        {
            tvNumOne.id->displayOtpNumber(getString(R.string.num1))
            tvNumTwo.id->displayOtpNumber(getString(R.string.num2))
            tvNumThree.id->displayOtpNumber(getString(R.string.num3))
            tvNumFour.id->displayOtpNumber(getString(R.string.num4))
            tvNumFive.id->displayOtpNumber(getString(R.string.num5))
            tvNumSix.id->displayOtpNumber(getString(R.string.num6))
            tvNumSeven.id->displayOtpNumber(getString(R.string.num7))
            tvNumEight.id->displayOtpNumber(getString(R.string.num8))
            tvNumNine.id->displayOtpNumber(getString(R.string.num9))
            tvNumZero.id->displayOtpNumber(getString(R.string.num0))
            ivClear.id->deleteOtpNumber()
            btnEditProfile.id->presenter.verifyOtp(getUserOtp(),mGeneratedOtp)
            tvResendOTP.id->presenter.resendOtp(mMobileNumber)
            tvEditMobileNumber.id->loginActivity.onBackPressed()
        }
    }


    override fun otpField4(): Observable<CharSequence> {
        return RxTextView.textChanges(tvOtpField4)
    }

    override fun updateSubmitButtonState(state: Boolean) {
        btnEditProfile.isEnabled = state
    }

    override fun showOtpError() {
        context!!.snackBar("Invalid OTP please try again",rootView)
    }

    override fun otpValidationSuccess() {
        sharedPreferences.putString(Constants.MOBILE_NUMBER,mMobileNumber)

        sharedPreferences.putIntData(Constants.STUDENT_ID,mStudentId)
        Log.d(Constants.LOG_TAG,"Student id  : $mStudentId")

        if(mEmail.isEmpty())
        {
            sharedPreferences.putBoolean(Constants.LOGGED_IN,true)
            val intent = Intent(context, RegisterActivity::class.java)
            startActivity(intent)
        }
        else
        {
            sharedPreferences.putString(Constants.FIRST_NAME,mFirstName)
            sharedPreferences.putString(Constants.LAST_NAME,mLastName)
            sharedPreferences.putString(Constants.EMAIL,mEmail)
            sharedPreferences.putBoolean(Constants.LOGGED_IN,true)
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun resendOtpResponse(status: Boolean) {
        if(status)
            context!!.snackBar("Otp sent successfully",rootView)
        else
            context!!.snackBar("Resending OTP Failed",rootView)
    }




    private fun getUserOtp(): String {
        return tvOtpField1.text.toString()+tvOtpField2.text.toString()+tvOtpField3.text.toString()+tvOtpField4.text.toString()
    }
    private fun displayOtpNumber(value: String)
    {
        when {
            tvOtpField1.text.isEmpty() -> tvOtpField1.text=value
            tvOtpField2.text.isEmpty() -> tvOtpField2.text=value
            tvOtpField3.text.isEmpty() -> tvOtpField3.text=value
            tvOtpField4.text.isEmpty() -> tvOtpField4.text=value
        }

    }
    private fun deleteOtpNumber() {

        when {
            tvOtpField4.text.isNotEmpty() -> tvOtpField4.text = ""
            tvOtpField3.text.isNotEmpty() -> tvOtpField3.text = ""
            tvOtpField2.text.isNotEmpty() -> tvOtpField2.text = ""
            tvOtpField1.text.isNotEmpty() -> tvOtpField1.text = ""
        }

    }

    private fun initUI() {
        tvNumOne.setOnClickListener(this)
        tvNumTwo.setOnClickListener(this)
        tvNumThree.setOnClickListener(this)
        tvNumFour.setOnClickListener(this)
        tvNumFive.setOnClickListener(this)
        tvNumSix.setOnClickListener(this)
        tvNumSeven.setOnClickListener(this)
        tvNumEight.setOnClickListener(this)
        tvNumNine.setOnClickListener(this)
        tvNumZero.setOnClickListener(this)
        ivClear.setOnClickListener(this)
        btnEditProfile.setOnClickListener(this)
        tvResendOTP.setOnClickListener(this)
        tvEditMobileNumber.setOnClickListener(this)
        tvMobileNumber.text = starredMobileNumber(mMobileNumber)
    }
    companion object {
        const val TAG = "EnterOtpFragment"
    }
    fun starredMobileNumber(number: String) : String{

        var starredNumber = number.substring(number.length-4,number.length)
        return "******$starredNumber"
    }
}
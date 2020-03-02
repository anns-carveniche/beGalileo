package com.carveniche.begalileo.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.carveniche.begalileo.util.Constants
import com.carveniche.begalileo.R
import com.carveniche.begalileo.di.component.DaggerFragmentComponent
import com.carveniche.begalileo.di.module.ContextModule
import com.carveniche.begalileo.di.module.Fragmentmodule
import com.carveniche.begalileo.di.module.SharedPreferenceModule
import com.carveniche.begalileo.models.MySharedPreferences
import com.carveniche.begalileo.contract.LoginContract
import com.carveniche.begalileo.contract.EnterOtpContract
import com.carveniche.begalileo.models.RegisterModel
import com.carveniche.begalileo.ui.activities.MainActivity
import com.carveniche.begalileo.util.snackBar
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_enterotp.*
import kotlinx.android.synthetic.main.layout_progressbar.*
import kotlinx.android.synthetic.main.number_layout.*
import javax.inject.Inject




class EnterOtpFragment : Fragment(), EnterOtpContract.View,View.OnClickListener {

    private lateinit var rootView: View
    @Inject lateinit var presenter: EnterOtpContract.Presenter
    lateinit var loginPresenter: LoginContract.View
    private lateinit var mGeneratedOtp : String
    private  var mMobileNumber : String = ""
    private  var mEmail : String = ""
     private  var parentId: Int = 0
    @Inject lateinit var sharedPreferences: MySharedPreferences

    fun newInstance() : EnterOtpFragment
    {
        return EnterOtpFragment()
    }
    override fun otpField4(): Observable<CharSequence> {
        return RxTextView.textChanges(tvOtpField4)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
        loginPresenter = activity as LoginContract.View
    }

    private fun injectDependency() {
        val fragmentComponent = DaggerFragmentComponent.builder()
            .fragmentmodule(Fragmentmodule())
            .sharedPreferenceModule(SharedPreferenceModule())
            .contextModule(ContextModule(context!!))
            .build()
        fragmentComponent.inject(this)
    }
    override fun showProgress(show: Boolean) {
        progressBar.bringToFront()
        if(show)
            progressBar.visibility = View.VISIBLE
        else
            progressBar.visibility = View.GONE
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       rootView = inflater.inflate(R.layout.fragment_enterotp,container,false)
        mGeneratedOtp = arguments!!.getString(Constants.OTP)
        mMobileNumber = arguments!!.getString(Constants.MOBILE_NUMBER)

        parentId = arguments!!.getInt(Constants.PARENT_ID)
        if(arguments!!.getString(Constants.EMAIL)!=null)
             mEmail = arguments!!.getString(Constants.EMAIL)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attach(this)
        initUI()
    }
    override fun updateSubmitButtonState(state: Boolean) {
        btnSubmit.isEnabled = state
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
        btnSubmit.setOnClickListener(this)
        tvResendOTP.setOnClickListener(this)
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
            btnSubmit.id->presenter.verifyOtp(getUserOtp(),mGeneratedOtp)
            tvResendOTP.id->resendOTP()
        }

    }

    private fun resendOTP(){
        clearOTPField()
        presenter.resendOtp(mMobileNumber)
    }

    private fun clearOTPField(){
        tvOtpField1.text = ""
        tvOtpField2.text = ""
        tvOtpField3.text = ""
        tvOtpField4.text = ""
    }
    private fun getUserOtp(): String {
        return tvOtpField1.text.toString()+tvOtpField2.text.toString()+tvOtpField3.text.toString()+tvOtpField4.text.toString()
    }

    private fun deleteOtpNumber() {

        when {
            tvOtpField4.text.isNotEmpty() -> tvOtpField4.text = ""
            tvOtpField3.text.isNotEmpty() -> tvOtpField3.text = ""
            tvOtpField2.text.isNotEmpty() -> tvOtpField2.text = ""
            tvOtpField1.text.isNotEmpty() -> tvOtpField1.text = ""
        }

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
    override fun showOtpError() {
        context!!.snackBar("Invalid OTP please try again",rootView)
    }
    override fun resendOtpResponse(registerModel: RegisterModel) {

        if(registerModel.status)
        {
            context!!.snackBar("Otp sent successfully",rootView)
            mGeneratedOtp = registerModel.otp.toString()
        }

        else
            context!!.snackBar("Resending OTP Failed",rootView)

    }

    override fun otpValidationSuccess() {

        sharedPreferences.putString(Constants.MOBILE_NUMBER,mMobileNumber)

        sharedPreferences.putIntData(Constants.PARENT_ID,parentId)
        if(mEmail.isEmpty())
            loginPresenter.showEnterEmailScreen()
        else
        {
            sharedPreferences.putString(Constants.EMAIL,mEmail)
            sharedPreferences.putBoolean(Constants.LOGGED_IN,true)
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
        }
    }

    companion object {
        val TAG: String = "EnterOtpFragment"
    }

}
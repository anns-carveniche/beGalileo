package com.carveniche.wisdomleap.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.carveniche.wisdomleap.R
import com.carveniche.wisdomleap.contract.MobileNumberContract
import com.carveniche.wisdomleap.di.component.DaggerFragmentComponent
import com.carveniche.wisdomleap.di.module.ContextModule
import com.carveniche.wisdomleap.di.module.FragmentModule
import com.carveniche.wisdomleap.di.module.SharedPreferenceModule
import com.carveniche.wisdomleap.model.RegisterModel
import com.carveniche.wisdomleap.util.Constants
import com.carveniche.wisdomleap.util.showLongToast
import com.carveniche.wisdomleap.view.activity.LoginActivity
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_mobile_number.*
import kotlinx.android.synthetic.main.fragment_mobile_number.view.*
import kotlinx.android.synthetic.main.layout_progressbar.*
import kotlinx.android.synthetic.main.number_layout.view.*
import javax.inject.Inject

class MobileNumberFragment : Fragment(),MobileNumberContract.View,View.OnClickListener {


    lateinit var rootView : View
    @Inject lateinit var presenter : MobileNumberContract.Presenter
    lateinit var loginActivity: LoginActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
        loginActivity = activity as LoginActivity
    }
    override fun updateVerifyMeViewState(state: Boolean) {
        tvVerifyMe.isEnabled = state
    }
    override fun showEnterOtpFragment(registerModel: RegisterModel) {
        showLongToast(registerModel.otp.toString(),context!!)
        loginActivity.showEnterOtpFragment(registerModel)
    }
    private fun injectDependency() {
        val fragmentComponent =DaggerFragmentComponent.builder()
            .fragmentModule(FragmentModule())
            .sharedPreferenceModule(SharedPreferenceModule())
            .contextModule(ContextModule(context!!))
            .build()
        fragmentComponent.inject(this)

    }

    override fun showErrorMessages(error: String) {
        Log.d(Constants.LOG_TAG,error)
    }

    override fun mobileNumber(): Observable<CharSequence> {
        return RxTextView.textChanges(edMobileNumber).skipInitialValue()
    }

    override fun showProgress(show: Boolean) {
        progressBar.bringToFront()
        if(show)
            progressBar.visibility = View.VISIBLE
        else
            progressBar.visibility = View.GONE
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_mobile_number,container,false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        presenter.attach(this)
        presenter.subscribe()
    }

    private fun initUI() {
        rootView.tvNumOne.setOnClickListener(this)
        rootView.tvNumTwo.setOnClickListener(this)
        rootView.tvNumThree.setOnClickListener(this)
        rootView.tvNumFour.setOnClickListener(this)
        rootView.tvNumFive.setOnClickListener(this)
        rootView.tvNumSix.setOnClickListener(this)
        rootView.tvNumSeven.setOnClickListener(this)
        rootView.tvNumEight.setOnClickListener(this)
        rootView.tvNumNine.setOnClickListener(this)
        rootView.tvNumZero.setOnClickListener(this)
        rootView.ivClear.setOnClickListener(this)
        rootView.tvVerifyMe.setOnClickListener(this)
        rootView.tvVerifyMe.isEnabled = false
    }
    private fun displayMobileNumber(value: String?)
    {
        val mMobileNumber = rootView.edMobileNumber.text.toString()
        if(mMobileNumber.length<=9)
            rootView.edMobileNumber.setText(mMobileNumber.plus(value))
    }
    private fun deleteLastNumber() {
        val length = rootView.edMobileNumber.length()
        if (length > 0) {
            rootView.edMobileNumber.text.delete(length-1,length)
        }
    }
    override fun onClick(v: View?) {
        when(v!!.id)
        {
            rootView.tvNumOne.id -> displayMobileNumber(getString(R.string.num1))
            rootView.tvNumTwo.id -> displayMobileNumber(getString(R.string.num2))
            rootView.tvNumThree.id -> displayMobileNumber(getString(R.string.num3))
            rootView.tvNumFour.id -> displayMobileNumber(getString(R.string.num4))
            rootView.tvNumFive.id -> displayMobileNumber(getString(R.string.num5))
            rootView.tvNumSix.id -> displayMobileNumber(getString(R.string.num6))
            rootView.tvNumSeven.id -> displayMobileNumber(getString(R.string.num7))
            rootView.tvNumEight.id -> displayMobileNumber(getString(R.string.num8))
            rootView.tvNumNine.id -> displayMobileNumber(getString(R.string.num9))
            rootView.tvNumZero.id -> displayMobileNumber(getString(R.string.num0))
            rootView.ivClear.id ->  deleteLastNumber()
            rootView.tvVerifyMe.id -> presenter.verifyNumberClick(edMobileNumber.text.toString())
        }
    }


    companion object {
        const val TAG = "MobileNumberFragment"
    }
}

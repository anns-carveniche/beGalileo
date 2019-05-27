package com.carveniche.begalileo.ui.fragments

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.carveniche.begalileo.util.Constants
import com.carveniche.begalileo.R
import com.carveniche.begalileo.di.component.DaggerFragmentComponent
import com.carveniche.begalileo.di.module.ContextModule
import com.carveniche.begalileo.di.module.Fragmentmodule
import com.carveniche.begalileo.models.RegisterModel
import com.carveniche.begalileo.contract.LoginContract
import com.carveniche.begalileo.contract.SignInContract
import com.carveniche.begalileo.util.showLongToast
import com.carveniche.begalileo.util.snackBar
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_signin.*
import kotlinx.android.synthetic.main.fragment_signin.view.*
import kotlinx.android.synthetic.main.layout_progressbar.*
import kotlinx.android.synthetic.main.number_layout.view.*
import javax.inject.Inject


class SignInFragment : Fragment(), SignInContract.View,View.OnClickListener {

    @Inject lateinit var presenter: SignInContract.Presenter
    private lateinit var rootView: View
    lateinit var loginView: LoginContract.View

    fun newInstance(): SignInFragment {
        return SignInFragment()
    }

    override fun showEnterOtpFragment(registerModel: RegisterModel) {
        Log.d(Constants.LOG_TAG,registerModel.otp.toString())
        showLongToast(registerModel.otp.toString(),context!!)
        loginView.showEnterOtpScreen(registerModel)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
        loginView = activity as LoginContract.View
    }

    override fun updateVerifyMeViewState(state: Boolean) {
            tvVerifyMe.isEnabled = state
    }

    private fun injectDependency() {
        val fragmentComponent = DaggerFragmentComponent.builder()
            .fragmentmodule(Fragmentmodule())
            .contextModule(ContextModule(context!!))
            .build()
        fragmentComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_signin,container,false)
        rootView.edMobileNumber.inputType = InputType.TYPE_NULL
        initUI()
        return rootView
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

    override fun displayMobileNumber(value: String?)
    {
            val mMobileNumber = rootView.edMobileNumber.text.toString()
            if(mMobileNumber.length<=9)
                rootView.edMobileNumber.setText(mMobileNumber.plus(value))


    }
     fun deleteLastNumber() {
         val length = rootView.edMobileNumber.length()
         if (length > 0) {
             rootView.edMobileNumber.text.delete(length-1,length)
         }
    }

    override fun showProgress(show: Boolean) {
        progressBar.bringToFront()
        if(show)
            progressBar.visibility = View.VISIBLE
        else
            progressBar.visibility = View.GONE
    }


    override fun mobileNumber(): Observable<CharSequence> {
        return RxTextView.textChanges(edMobileNumber).skipInitialValue()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attach(this)
        presenter.subscribe()

    }
    companion object {
        val TAG: String = "SignInFragment"
    }
    override fun showErrorMessages(error: String) {
        context!!.snackBar(error,rootView)
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
}

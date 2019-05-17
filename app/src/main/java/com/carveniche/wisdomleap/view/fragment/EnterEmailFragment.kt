package com.carveniche.wisdomleap.view.fragment


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.carveniche.wisdomleap.R
import com.carveniche.wisdomleap.contract.EnterEmailContract
import com.carveniche.wisdomleap.di.component.DaggerFragmentComponent
import com.carveniche.wisdomleap.di.module.ContextModule
import com.carveniche.wisdomleap.di.module.FragmentModule
import com.carveniche.wisdomleap.di.module.SharedPreferenceModule
import com.carveniche.wisdomleap.model.MySharedPreferences
import com.carveniche.wisdomleap.util.Constants
import com.carveniche.wisdomleap.util.snackBar
import com.carveniche.wisdomleap.view.activity.MainActivity
import com.carveniche.wisdomleap.view.activity.RegisterActivity
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_enter_email.*
import kotlinx.android.synthetic.main.layout_progressbar.*
import java.lang.Exception
import javax.inject.Inject


class EnterEmailFragment : Fragment(),EnterEmailContract.View {


    @Inject
    lateinit var sharedPreferences: MySharedPreferences
    private lateinit var rootView : View
    @Inject lateinit var presenter : EnterEmailContract.Presenter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_enter_email, container, false)
        return rootView
    }
    override fun showProgress(show: Boolean) {
        progressBar.bringToFront()
        if(show)
            progressBar.visibility = View.VISIBLE
        else
            progressBar.visibility = View.GONE
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
    }
    override fun updateEmailSucess() {
        sharedPreferences.putBoolean(Constants.LOGGED_IN,true)
        val intent = Intent(context, RegisterActivity::class.java)
        startActivity(intent)
    }
    private fun initUI() {
        btnSubmit.setOnClickListener {
            try {
                presenter.updateEmail(sharedPreferences.getString(Constants.MOBILE_NUMBER), edEmail.text.toString())
            }
            catch (ae: Exception)
            {
                Log.d(Constants.LOG_TAG,ae.localizedMessage)
            }

        }
    }
    override fun updateEmailFailed(message: String) {
        context!!.snackBar(message,rootView)
    }

    override fun emailObservable(): Observable<CharSequence> {
        return RxTextView.textChanges(edEmail).skipInitialValue()
    }

    override fun updateSubmitButton(state: Boolean) {
        btnSubmit.isEnabled = state
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attach(this)
        presenter.subscribe()
        initUI()
    }
    private fun injectDependency() {
        val fragmentComponent = DaggerFragmentComponent.builder()
            .fragmentModule(FragmentModule())
            .sharedPreferenceModule(SharedPreferenceModule())
            .contextModule(ContextModule(context!!))
            .build()
        fragmentComponent.inject(this)
    }

    companion object {
        const val TAG = "EnterEmailFragment"
    }

}

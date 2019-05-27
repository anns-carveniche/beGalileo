package com.carveniche.begalileo.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.carveniche.begalileo.util.Constants
import com.carveniche.begalileo.R
import com.carveniche.begalileo.api.ApiServiceInterface
import com.carveniche.begalileo.di.component.DaggerFragmentComponent
import com.carveniche.begalileo.di.module.ContextModule
import com.carveniche.begalileo.di.module.Fragmentmodule
import com.carveniche.begalileo.di.module.SharedPreferenceModule
import com.carveniche.begalileo.models.MySharedPreferences
import com.carveniche.begalileo.contract.BaseContract
import com.carveniche.begalileo.ui.activities.MainActivity
import com.carveniche.begalileo.util.isValidEmail
import com.carveniche.begalileo.util.snackBar
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_enter_email.*


import kotlinx.android.synthetic.main.layout_progressbar.*
import java.lang.Exception

import javax.inject.Inject

class EnterEmailFragment : Fragment(), EnterEmailContract.View {

    private lateinit var rootView: View
    @Inject
    lateinit var sharedPreferences: MySharedPreferences

    @Inject
    lateinit var presenter: EnterEmailContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()

    }
    override fun emailObservable(): Observable<CharSequence> {
        return RxTextView.textChanges(edEmail).skipInitialValue()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attach(this)
        initUI()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView =  inflater.inflate(R.layout.fragment_enter_email,container,false)
        return rootView
    }
    private fun injectDependency() {
        val fragmentComponent = DaggerFragmentComponent.builder()
            .fragmentmodule(Fragmentmodule())
            .sharedPreferenceModule(SharedPreferenceModule())
            .contextModule(ContextModule(context!!))
            .build()
        fragmentComponent.inject(this)
    }
    override fun updateSubmitButton(state: Boolean) {
       btnSubmit.isEnabled = state
    }

    private fun initUI() {
        btnSubmit.setOnClickListener {
            try {
                presenter.updateEmail(sharedPreferences.getString(Constants.MOBILE_NUMBER), edEmail.text.toString())
            }
            catch (ae:Exception)
            {
                Log.d(Constants.LOG_TAG,ae.localizedMessage)
            }

        }


    }

    override fun updateEmailSucess() {
        sharedPreferences.putBoolean(Constants.LOGGED_IN,true)
        val intent = Intent(context, MainActivity::class.java)
        startActivity(intent)

    }

    override fun updateEmailFailed(message: String) {
        context!!.snackBar(message,rootView)
    }
    override fun showProgress(show: Boolean) {
        progressBar.bringToFront()
        if(show)
            progressBar.visibility = View.VISIBLE
        else
            progressBar.visibility = View.GONE
    }

    companion object {
        val TAG: String = "EnterEmailFragment"
    }

}
class EnterEmailContract
{
    interface View : BaseContract.View
    {
        fun updateEmailSucess()
        fun updateEmailFailed(message: String)
        fun emailObservable() : Observable<CharSequence>
        fun updateSubmitButton(state : Boolean)
    }
    interface Presenter : BaseContract.Presenter<View>
    {
        fun updateEmail(mMobile: String,mEmail: String)
        fun validateEmail() : Function<CharSequence,Boolean>
    }
}

class EnterEmailPresenter : EnterEmailContract.Presenter
{
    override fun attach(view: EnterEmailContract.View) {
        this.view = view
        val emailObservable = view.emailObservable().map(validateEmail())
        val emailSubscribe = emailObservable.subscribe {
            view.updateSubmitButton(it)
        }
        subscriptions.add(emailSubscribe)

    }

    @Inject lateinit var view: EnterEmailContract.View
    private val subscriptions  = CompositeDisposable()
    private val apiServiceInterface: ApiServiceInterface = ApiServiceInterface.create()


    override fun subscribe() {

    }

    override fun unsubscrbe() {
        subscriptions.clear()
    }

    override fun updateEmail(mMobile: String, mEmail: String) {
        view.showProgress(true)
        val subscriber = apiServiceInterface.updateEmail(mMobile,mEmail)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.showProgress(false)
                view.updateEmailSucess()
            },
                {
                        error->
                    view.showProgress(false)
                    view.updateEmailFailed("Update Email Failed "+error.localizedMessage)

                })
        subscriptions.add(subscriber)
            }


    override fun validateEmail(): Function<CharSequence, Boolean> {
      return Function{
          it.toString().isValidEmail()
        }
    }
}







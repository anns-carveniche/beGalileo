package com.carveniche.wisdomleap.presenter

import com.carveniche.wisdomleap.api.ApiInterface
import com.carveniche.wisdomleap.contract.EnterEmailContract
import com.carveniche.wisdomleap.util.isValidEmail
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

class EnterEmailPresenter : EnterEmailContract.Presenter {

    private val apiServiceInterface: ApiInterface = ApiInterface.create()
    lateinit var view : EnterEmailContract.View
    var disposable = CompositeDisposable()

    override fun subscribe() {
        val emailObservable = view.emailObservable().map(validateEmail())
        val emailSubscribe = emailObservable.subscribe {
            view.updateSubmitButton(it)
        }
        disposable.add(emailSubscribe)
    }

    override fun unSubscribe() {
        disposable.clear()
    }

    override fun attach(view: EnterEmailContract.View) {
        this.view = view
    }
    override fun updateEmail(mMobile: String, mEmail: String) {
        view.showProgress(true)
        val subscriber = apiServiceInterface.updateEmail(mMobile,mEmail)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.showProgress(false)
                if(it.status)
                    view.updateEmailSucess()
                else
                    view.updateEmailFailed("Update email failed")
            },
                {
                        error->
                    view.showProgress(false)
                    view.updateEmailFailed("Update Email Failed "+error.localizedMessage)

                })
        disposable.add(subscriber)
    }

    override fun validateEmail(): Function<CharSequence, Boolean> {
        return Function{
            it.toString().isValidEmail()
        }
    }
}
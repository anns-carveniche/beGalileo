package com.carveniche.wisdomleap.presenter

import com.carveniche.wisdomleap.api.ApiInterface
import com.carveniche.wisdomleap.contract.EnterOtpContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

class EnterOtpPresenter : EnterOtpContract.Presenter {

    lateinit var view : EnterOtpContract.View
    var disposable = CompositeDisposable()
    private val apiServiceInterface: ApiInterface = ApiInterface.create()
    override fun subscribe() {
        val otpFieldValueObservable = view.otpField4().map(isOtpFilled())
        val otpSubscribe = otpFieldValueObservable.subscribe{
            view.updateSubmitButtonState(it)
        }
        disposable.add(otpSubscribe)
    }

    override fun unSubscribe() {
        disposable.clear()
    }

    override fun attach(view: EnterOtpContract.View) {
        this.view = view

    }
    private fun isOtpFilled(): io.reactivex.functions.Function<CharSequence, Boolean> {
        return Function { t->checkNumber(t) }
    }
    private fun checkNumber(t: CharSequence): Boolean
    {
        return t.toString() != ""
    }
    override fun verifyOtp(userOtp: String, generatedOtp: String) {
        if(userOtp == generatedOtp)
        {

            view.otpValidationSuccess()
        }
        else
        {
            view.showOtpError()

        }
    }

    override fun resendOtp(mobile: String) {
        view.showProgress(true)
        val resendOtpSubscribe = apiServiceInterface.resendOTP(mobile).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.showProgress(false)
                view.resendOtpResponse(it.status)

            }
        disposable.add(resendOtpSubscribe)
    }

}
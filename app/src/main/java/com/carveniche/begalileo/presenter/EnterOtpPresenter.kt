package com.carveniche.begalileo.presenter


import com.carveniche.begalileo.api.ApiServiceInterface
import com.carveniche.begalileo.contract.EnterOtpContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

import javax.inject.Inject
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

class EnterOtpPresenter : EnterOtpContract.Presenter
{
    private val apiServiceInterface: ApiServiceInterface = ApiServiceInterface.create()

    @Inject lateinit var view: EnterOtpContract.View


    private val subscriptions  = CompositeDisposable()

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

    override fun subscribe() {

    }

    override fun unsubscrbe() {
        subscriptions.clear()
    }

    override fun attach(view: EnterOtpContract.View) {
        this.view = view
        val otpFieldValueObservable = view.otpField4().map(isOtpFilled())
        val otpSubscribe = otpFieldValueObservable.subscribe{
            view.updateSubmitButtonState(it)
        }
        subscriptions.add(otpSubscribe)
    }

     private fun isOtpFilled(): Function<CharSequence, Boolean> {
        return Function { t -> checkNumber(t) }
    }
    private fun checkNumber(t: CharSequence): Boolean
    {
        return t.toString() != ""
    }

    override fun resendOtp(mobile: String) {
            view.showProgress(true)
        val resendOtpSubscribe = apiServiceInterface.resendOTP(mobile).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.showProgress(false)
                view.resendOtpResponse(it.status)

            }
        subscriptions.add(resendOtpSubscribe)
    }

}
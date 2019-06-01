package com.carveniche.wisdomleap.presenter

import com.carveniche.wisdomleap.api.ApiInterface
import com.carveniche.wisdomleap.contract.MobileNumberContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

class MobileNumberPresenter : MobileNumberContract.Presenter {


    lateinit var view : MobileNumberContract.View
    private val disposable = CompositeDisposable()
    private val api = ApiInterface.create()

    override fun subscribe() {
      /*  val mobileNumberObservable = view.mobileNumber().map(isMobileNumberValid())
        val mobileNumberSubscribe = mobileNumberObservable.subscribe {
            view.updateVerifyMeViewState(it)
        }
        disposable.add(mobileNumberSubscribe)*/
    }

    override fun unSubscribe() {
        disposable.clear()
    }
    override fun attach(view: MobileNumberContract.View) {
       this.view = view
    }
    override fun verifyNumberClick(countryCode : String,mobileNumber: String) {
        view.showProgress(true)
        val subscribe = api.registerMobileNumber(countryCode,mobileNumber).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.showProgress(false)
                if(it.status)
                    view.showEnterOtpFragment(it)
                else
                    view.showErrorMessages(it.message)
            },{
                view.showProgress(false)
                view.showErrorMessages(it.localizedMessage)
            })
        disposable.add(subscribe)
    }

    /*override fun isMobileNumberValid(): Function<CharSequence, Boolean> {
        return Function { t -> checkNumber(t) }
    }*/
    private fun checkNumber(t: CharSequence): Boolean
    {
        return t.length==10
    }
}
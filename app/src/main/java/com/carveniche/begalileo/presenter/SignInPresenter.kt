package com.carveniche.begalileo.presenter

import com.carveniche.begalileo.api.ApiServiceInterface
import com.carveniche.begalileo.contract.SignInContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers


class SignInPresenter : SignInContract.Presenter {



    private lateinit var view: SignInContract.View
    private val subscriptions  = CompositeDisposable()
     private val apiServiceInterface: ApiServiceInterface = ApiServiceInterface.create()


    override fun verifyNumberClick(mobileNumber: String) {
        view.showProgress(true)
        val subscribe =  apiServiceInterface.registerMobileNumber(mobileNumber).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({it->
                view.showProgress(false)
                if(it.status)
                {
                    view.showEnterOtpFragment(it)
                }
                else
                {
                    view.showErrorMessages(it.message)
                }


            },
                {
                    error->
                    view.showProgress(false)
                    view.showErrorMessages(error.localizedMessage)

                })
        subscriptions.add(subscribe)
    }

    override fun checkUser() {

    }

    override fun subscribe() {

    }

    override fun unsubscrbe() {
        subscriptions.clear()
    }

    override fun attach(view: SignInContract.View) {
      this.view = view
        val mobileNumberObservable = view.mobileNumber().map(isMobileNumberValid())
        val mobileNumberSubscribe = mobileNumberObservable.subscribe {
            view.updateVerifyMeViewState(it)
        }
        subscriptions.add(mobileNumberSubscribe)


    }

    override fun isMobileNumberValid(): Function<CharSequence, Boolean> {
       return Function { t -> checkNumber(t) }
    }
    fun checkNumber(t: CharSequence): Boolean
    {
        return t.length==10
    }
}





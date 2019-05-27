package com.carveniche.begalileo.presenter

import com.carveniche.begalileo.util.Constants
import com.carveniche.begalileo.contract.LoginContract
import com.carveniche.begalileo.models.MySharedPreferences
import io.reactivex.disposables.CompositeDisposable

class LoginPresenter : LoginContract.Presenter {
    override fun showFragment(mySharedPreferences: MySharedPreferences) {
        val mobile = mySharedPreferences.getString(Constants.MOBILE_NUMBER)
        val email  = mySharedPreferences.getString(Constants.EMAIL)
        if(mobile.isEmpty())
            view.showSignInScreen()
        else if(email.isEmpty())
            view.showEnterEmailScreen()

    }




     private lateinit var view: LoginContract.View
     private val subscriptions = CompositeDisposable()



    override fun subscribe() {

    }

    override fun unsubscrbe() {
        subscriptions.clear()
    }

    override fun attach(view: LoginContract.View) {
        this.view = view
    }
}
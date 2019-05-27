package com.carveniche.begalileo.ui.login

import com.carveniche.begalileo.models.MySharedPreferences
import com.carveniche.begalileo.models.RegisterModel
import com.carveniche.begalileo.ui.base.BaseContract

class LoginContract {
    interface View : BaseContract.View{
        fun showSignInScreen()
        fun showRegisterScreen()
        fun showEnterOtpScreen(registerModel: RegisterModel)
        fun showEnterEmailScreen()
    }
    interface Presenter : BaseContract.Presenter<LoginContract.View>
    {
        fun showFragment(mySharedPreferences: MySharedPreferences)
    }
}
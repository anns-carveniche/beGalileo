package com.carveniche.begalileo.contract

import com.carveniche.begalileo.models.MySharedPreferences
import com.carveniche.begalileo.models.RegisterModel

class LoginContract {
    interface View : BaseContract.View{
        fun showSignInScreen()
        fun showRegisterScreen()
        fun showEnterOtpScreen(registerModel: RegisterModel)
        fun showEnterEmailScreen()
    }
    interface Presenter : BaseContract.Presenter<View>
    {
        fun showFragment(mySharedPreferences: MySharedPreferences)
    }
}
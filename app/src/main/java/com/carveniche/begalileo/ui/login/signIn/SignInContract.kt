package com.carveniche.begalileo.ui.login.signIn

import android.widget.EditText
import com.carveniche.begalileo.models.RegisterModel
import com.carveniche.begalileo.ui.base.BaseContract
import io.reactivex.Observable
import com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent
import io.reactivex.functions.Function


class SignInContract  {
    interface View : BaseContract.View{
        fun displayMobileNumber(value: String?)
        fun showEnterOtpFragment(registerModel: RegisterModel)
        fun showErrorMessages(error: String)
        fun mobileNumber(): Observable<CharSequence>
        fun updateVerifyMeViewState(state: Boolean)
    }
    interface Presenter : BaseContract.Presenter<SignInContract.View>
    {
        fun checkUser()
        fun verifyNumberClick(mobileNumber: String)
        fun isMobileNumberValid(): Function<CharSequence, Boolean>
    }

}
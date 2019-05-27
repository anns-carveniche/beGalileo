package com.carveniche.begalileo.contract

import com.carveniche.begalileo.models.RegisterModel
import io.reactivex.Observable
import io.reactivex.functions.Function


class SignInContract  {
    interface View : BaseContract.View{
        fun displayMobileNumber(value: String?)
        fun showEnterOtpFragment(registerModel: RegisterModel)
        fun showErrorMessages(error: String)
        fun mobileNumber(): Observable<CharSequence>
        fun updateVerifyMeViewState(state: Boolean)
    }
    interface Presenter : BaseContract.Presenter<View>
    {
        fun checkUser()
        fun verifyNumberClick(mobileNumber: String)
        fun isMobileNumberValid(): Function<CharSequence, Boolean>
    }

}
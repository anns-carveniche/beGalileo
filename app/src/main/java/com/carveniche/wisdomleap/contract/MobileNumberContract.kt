package com.carveniche.wisdomleap.contract

import com.carveniche.wisdomleap.base.BaseContract
import com.carveniche.wisdomleap.model.RegisterModel
import io.reactivex.Observable
import io.reactivex.functions.Function

class MobileNumberContract {
    interface View : BaseContract.View {
        fun showErrorMessages(error: String)
        fun mobileNumber(): Observable<CharSequence>
        fun updateVerifyMeViewState(state: Boolean)
        fun showEnterOtpFragment(registerModel: RegisterModel)
    }
    interface Presenter : BaseContract.Presenter<MobileNumberContract.View>
    {
        fun verifyNumberClick(mobileNumber: String)
        fun isMobileNumberValid(): Function<CharSequence, Boolean>
    }
}
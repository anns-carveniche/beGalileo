package com.carveniche.wisdomleap.contract

import com.carveniche.wisdomleap.base.BaseContract
import io.reactivex.Observable

class EnterOtpContract {
    interface View : BaseContract.View{
        fun otpField4(): Observable<CharSequence>
        fun updateSubmitButtonState(state: Boolean)
        fun showOtpError()
        fun otpValidationSuccess()
        fun resendOtpResponse(status: Boolean)
    }
    interface Presenter : BaseContract.Presenter<EnterOtpContract.View>
    {
        fun verifyOtp(userOtp : String,generatedOtp : String)
        fun resendOtp(mobile : String)
    }
}
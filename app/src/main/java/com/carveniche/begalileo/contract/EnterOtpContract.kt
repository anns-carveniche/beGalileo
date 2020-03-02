package com.carveniche.begalileo.contract

import com.carveniche.begalileo.models.RegisterModel
import io.reactivex.Observable

class EnterOtpContract  {
    interface View : BaseContract.View {
        fun otpField4():Observable<CharSequence>
        fun updateSubmitButtonState(state: Boolean)
        fun showOtpError()
        fun otpValidationSuccess()
        fun resendOtpResponse(registerModel: RegisterModel)
    }
    interface Presenter : BaseContract.Presenter<View>
    {
        fun verifyOtp(userOtp : String,generatedOtp : String)
        fun resendOtp(mobile : String)

    }
}
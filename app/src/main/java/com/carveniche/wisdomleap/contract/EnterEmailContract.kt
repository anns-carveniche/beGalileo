package com.carveniche.wisdomleap.contract

import com.carveniche.wisdomleap.base.BaseContract
import io.reactivex.Observable
import io.reactivex.functions.Function

class EnterEmailContract {
    interface View : BaseContract.View{
        fun updateEmailSucess()
        fun updateEmailFailed(message: String)
        fun emailObservable() : Observable<CharSequence>
        fun updateSubmitButton(state : Boolean)
    }
    interface Presenter : BaseContract.Presenter<EnterEmailContract.View>
    {
        fun updateEmail(mMobile: String,mEmail: String)
        fun validateEmail() : Function<CharSequence, Boolean>
    }
}
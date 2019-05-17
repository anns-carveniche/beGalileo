package com.carveniche.wisdomleap.contract

import com.carveniche.wisdomleap.base.BaseContract
import com.carveniche.wisdomleap.model.GradeListModel
import io.reactivex.Observable

class RegisterContract {
    interface View : BaseContract.View
    {
        fun emailObservable() : Observable<CharSequence>
        fun updateSubmitButton(state : Boolean)
        fun gradeListLoadSucess(gradeListModel: GradeListModel)
        fun gradeListLoadFailed(msg : String)
        fun onSubmitDetailsSucess()
        fun onSubmitDetailFailed(msg : String)
    }
    interface Presenter : BaseContract.Presenter<RegisterContract.View>
    {
        fun getGradeList()
        fun submitRegisterDetails(mobileNumber : String,email: String,gradeId : Int,city : String)
    }
}
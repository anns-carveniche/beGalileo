package com.carveniche.wisdomleap.contract

import com.carveniche.wisdomleap.base.BaseContract
import com.carveniche.wisdomleap.model.BasicResponseModel
import com.carveniche.wisdomleap.model.StudentProfileModel

class ProfileHomeContract {
    interface View : BaseContract.View
    {
        fun onProfileLoadSucess(studentProfileModel: StudentProfileModel)
        fun onProfileLoadFailed(msg : String)
        fun submitProfileDataSuccess(firstName : String,lastName: String,schoolName: String)
        fun submitProfileDataFailed(msg : String)
        fun updateProfileAvatar(imageId : Int)

    }
    interface Presenter : BaseContract.Presenter<ProfileHomeContract.View>
    {
        fun loadProfileDatas(studentId : Int)
        fun submitProfileDatas(studentId : Int,firstName : String,lastName : String,schoolName : String)
    }
}
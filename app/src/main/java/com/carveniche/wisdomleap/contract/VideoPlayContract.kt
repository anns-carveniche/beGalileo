package com.carveniche.wisdomleap.contract

import com.carveniche.wisdomleap.base.BaseContract

class VideoPlayContract {
    interface View : BaseContract.View
    {

    }
    interface Presenter : BaseContract.Presenter<VideoPlayContract.View>
    {
        fun updateVideoStatus(studentId : Int,courseId : Int,conceptId : Int,subConceptId : Int,isCompleted : Boolean,duration : Long)
    }
}
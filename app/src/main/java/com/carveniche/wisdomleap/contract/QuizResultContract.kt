package com.carveniche.wisdomleap.contract

import com.carveniche.wisdomleap.base.BaseContract

class QuizResultContract {
    interface View : BaseContract.View{

    }
    interface Presenter : BaseContract.Presenter<QuizResultContract.View>
    {
        fun submitQuizResult(studentId : Int,categoryId : Int,level : String,total : Int,correct : Int,timeSpent : Int)
    }
}
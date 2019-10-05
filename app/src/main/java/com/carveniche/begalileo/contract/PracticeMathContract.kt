package com.carveniche.begalileo.contract

import com.carveniche.begalileo.models.PracticeQuizQuestionModel

class PracticeMathContract  {

    interface View : BaseContract.View{

        fun onPracticeQuizQuestionLoadSuccess(practiceQuizQuestionModel: PracticeQuizQuestionModel)
        fun onPracticeQuizQuestionLoadFailed(errorMsg : String)

    }

    interface Presenter : BaseContract.Presenter<PracticeMathContract.View>
    {
        fun getPracticeQuizQuestions(qNumber : Int)
    }
}
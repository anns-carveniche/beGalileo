package com.carveniche.begalileo.contract

import com.carveniche.begalileo.models.AnswerSheetModel

class SpeedMathContract {
    interface View: BaseContract.View{
        fun showMathWithComputerFragement(levelId : Int)
        fun showMathWithPlayerFragement()
        fun showGameLevelFragment()
        fun showGameResultActivity(data : AnswerSheetModel,robotCorrectAnswer : Int)
    }
    interface Presenter : BaseContract.Presenter<View>
    {

    }
}
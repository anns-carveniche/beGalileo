package com.carveniche.begalileo.ui.speedMath

import com.carveniche.begalileo.models.AnswerSheetModel
import com.carveniche.begalileo.ui.base.BaseContract

class SpeedMathContract {
    interface View: BaseContract.View{
        fun showMathWithComputerFragement(levelId : Int)
        fun showMathWithPlayerFragement()
        fun showGameLevelFragment()
        fun showGameResultActivity(data : AnswerSheetModel,robotCorrectAnswer : Int)
    }
    interface Presenter : BaseContract.Presenter<SpeedMathContract.View>
    {

    }
}
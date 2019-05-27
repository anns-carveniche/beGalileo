package com.carveniche.begalileo.ui.speedMath.gameResultActivity

import com.carveniche.begalileo.ui.base.BaseContract

class GameResultContractor  {
    interface View : BaseContract.View{
        fun showAnswerSummary()
    }
    interface Presenter : BaseContract.Presenter<View> {

    }
}
package com.carveniche.begalileo.contract

class GameResultContractor  {
    interface View : BaseContract.View{
        fun showAnswerSummary()
    }
    interface Presenter : BaseContract.Presenter<View> {

    }
}
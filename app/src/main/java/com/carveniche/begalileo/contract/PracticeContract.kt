package com.carveniche.begalileo.contract

class PracticeContract {
    interface View : BaseContract.View
    {

    }
    interface Presenter : BaseContract.Presenter<View>
    {

    }
}
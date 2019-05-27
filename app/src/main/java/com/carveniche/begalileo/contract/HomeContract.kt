package com.carveniche.begalileo.contract

class HomeContract  {

    interface View : BaseContract.View
    {
        fun loadMessageSuccess(message: String)
        fun onItemClick(position : Int)
    }
    interface Presenter : BaseContract.Presenter<View>
    {
        fun loadMessage()
    }
}
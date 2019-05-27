package com.carveniche.begalileo.ui.home

import com.carveniche.begalileo.ui.base.BaseContract

class HomeContract  {

    interface View : BaseContract.View
    {
        fun loadMessageSuccess(message: String)
        fun onItemClick(position : Int)
    }
    interface Presenter : BaseContract.Presenter<HomeContract.View>
    {
        fun loadMessage()
    }
}
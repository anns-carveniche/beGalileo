package com.carveniche.wisdomleap.contract

import com.carveniche.wisdomleap.base.BaseContract

class MultiplayerSearchContract {
    interface View : BaseContract.View {
        fun randomPlayerAnimation(state : Boolean)
        fun showRandomPlayer(name : String,coins : Int)
    }
    interface Presenter : BaseContract.Presenter<MultiplayerSearchContract.View>
    {
        fun searchRandomPlayer()
    }
}
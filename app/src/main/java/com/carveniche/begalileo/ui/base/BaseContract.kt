package com.carveniche.begalileo.ui.base

class BaseContract {

    interface Presenter<in T>{
        fun subscribe()
        fun unsubscrbe()
        fun attach(view:T)
    }

    interface View
    {
        fun showProgress(boolean: Boolean)
    }
}
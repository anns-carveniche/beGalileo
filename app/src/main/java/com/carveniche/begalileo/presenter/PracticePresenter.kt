package com.carveniche.begalileo.presenter

import com.carveniche.begalileo.contract.PracticeContract

class PracticePresenter : PracticeContract.Presenter {
    lateinit var view: PracticeContract.View
    override fun subscribe() {

    }

    override fun unsubscrbe() {

    }

    override fun attach(view: PracticeContract.View) {
        this.view = view
    }
}
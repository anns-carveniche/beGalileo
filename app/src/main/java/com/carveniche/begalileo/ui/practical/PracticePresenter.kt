package com.carveniche.begalileo.ui.practical

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
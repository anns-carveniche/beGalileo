package com.carveniche.begalileo.ui.home


class HomePresenter : HomeContract.Presenter {

    lateinit var View: HomeContract.View
    override fun loadMessage() {

    }

    override fun subscribe() {

    }

    override fun unsubscrbe() {

    }

    override fun attach(View: HomeContract.View) {
        this.View = View
    }
}
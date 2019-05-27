package com.carveniche.begalileo.presenter

import com.carveniche.begalileo.contract.HomeContract


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
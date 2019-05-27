package com.carveniche.begalileo.ui.main

import io.reactivex.disposables.CompositeDisposable

class MainPresenter : MainContract.Presenter {


    private lateinit var view: MainContract.View
    private val subscriptions = CompositeDisposable()

    override fun onDrawerOptionsClick() {

    }

    override fun subscribe() {

    }

    override fun unsubscrbe() {
        subscriptions.clear()

    }

    override fun attach(view: MainContract.View) {
        this.view = view
        view.showHomeFragment()
    }



}
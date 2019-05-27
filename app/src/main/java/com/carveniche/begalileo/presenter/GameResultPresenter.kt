package com.carveniche.begalileo.presenter

import com.carveniche.begalileo.api.ApiServiceInterface
import com.carveniche.begalileo.contract.GameResultContractor
import io.reactivex.disposables.CompositeDisposable

class GameResultPresenter : GameResultContractor.Presenter {


    private var subscriptions  = CompositeDisposable()
    private var apiInterface  = ApiServiceInterface.create()
    private lateinit var view : GameResultContractor.View

    override fun subscribe() {

    }

    override fun unsubscrbe() {
        subscriptions.clear()
    }

    override fun attach(view: GameResultContractor.View) {
        this.view = view
    }



}
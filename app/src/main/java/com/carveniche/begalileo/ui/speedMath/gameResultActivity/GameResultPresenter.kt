package com.carveniche.begalileo.ui.speedMath.gameResultActivity

import android.app.Dialog
import com.carveniche.begalileo.api.ApiServiceInterface
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
package com.carveniche.begalileo.ui.speedMath

import com.carveniche.begalileo.api.ApiServiceInterface
import io.reactivex.disposables.CompositeDisposable

class SpeedMathPresenter : SpeedMathContract.Presenter {

    private var subscriptions = CompositeDisposable()
    private lateinit var view : SpeedMathContract.View
    private var apiInterface : ApiServiceInterface = ApiServiceInterface.create()

    override fun subscribe() {

    }

    override fun unsubscrbe() {
        subscriptions.clear()
    }

    override fun attach(view: SpeedMathContract.View) {
        this.view = view
    }
}
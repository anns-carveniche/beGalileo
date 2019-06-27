package com.carveniche.wisdomleap.presenter

import com.carveniche.wisdomleap.api.ApiInterface
import com.carveniche.wisdomleap.contract.MultiplayerResultContract
import io.reactivex.disposables.CompositeDisposable

class MultiplayerResultPresenter : MultiplayerResultContract.Presenter {


    private lateinit var view : MultiplayerResultContract.View
    private var api = ApiInterface.create()
    private var disposable = CompositeDisposable()

    override fun subscribe() {

    }

    override fun unSubscribe() {
        disposable.clear()
    }

    override fun attach(view: MultiplayerResultContract.View) {
        this.view = view
    }
}
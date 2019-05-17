package com.carveniche.wisdomleap.presenter

import com.carveniche.wisdomleap.api.ApiInterface
import com.carveniche.wisdomleap.contract.MainContract
import io.reactivex.disposables.CompositeDisposable

class MainPresenter : MainContract.Presenter {

    private lateinit var view: MainContract.View
    private var api = ApiInterface.create()
    private var disposable = CompositeDisposable()

    override fun subscribe() {

    }

    override fun unSubscribe() {
        disposable.clear()
    }

    override fun attach(view: MainContract.View) {
        this.view = view
    }
}
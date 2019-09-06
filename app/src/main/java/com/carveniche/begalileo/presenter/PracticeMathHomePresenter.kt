package com.carveniche.begalileo.presenter

import com.carveniche.begalileo.api.ApiServiceInterface
import com.carveniche.begalileo.contract.PracticeMathHomeContract
import io.reactivex.disposables.CompositeDisposable

class PracticeMathHomePresenter : PracticeMathHomeContract.Presenter {

    private lateinit var view : PracticeMathHomeContract.View
    private var api = ApiServiceInterface.create()
    private var disposable = CompositeDisposable()

    override fun subscribe() {

    }

    override fun unsubscrbe() {
        disposable.clear()
    }

    override fun attach(view: PracticeMathHomeContract.View) {
      this.view = view
    }
}
package com.carveniche.begalileo.presenter

import com.carveniche.begalileo.api.ApiServiceInterface
import com.carveniche.begalileo.contract.QuizResultContract
import io.reactivex.disposables.CompositeDisposable

class QuizResultPresenter : QuizResultContract.Presenter {
    private lateinit var view: QuizResultContract.View
    private val disposable = CompositeDisposable()
    private var api = ApiServiceInterface.create()
    override fun subscribe() {

    }

    override fun unsubscrbe() {
       disposable.clear()
    }

    override fun attach(view: QuizResultContract.View) {
      this.view = view
    }
}
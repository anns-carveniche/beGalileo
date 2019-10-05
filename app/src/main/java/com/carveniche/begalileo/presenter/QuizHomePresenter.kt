package com.carveniche.begalileo.presenter

import com.carveniche.begalileo.api.ApiServiceInterface
import com.carveniche.begalileo.contract.QuizHomeContract
import io.reactivex.disposables.CompositeDisposable

class QuizHomePresenter : QuizHomeContract.Presenter{
    private lateinit var view : QuizHomeContract.View
    private val disposable = CompositeDisposable()
    private var api = ApiServiceInterface.create()
    override fun subscribe() {

    }

    override fun unsubscrbe() {
        disposable.clear()
    }

    override fun attach(view: QuizHomeContract.View) {
        this.view = view
    }

}
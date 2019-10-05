package com.carveniche.begalileo.presenter

import com.carveniche.begalileo.api.ApiServiceInterface
import com.carveniche.begalileo.contract.BaseContract
import com.carveniche.begalileo.contract.QuizPlayContract
import io.reactivex.disposables.CompositeDisposable

class QuizPlayPresenter : QuizPlayContract.Presenter{
    private lateinit var view: QuizPlayContract.View
    private val disposable = CompositeDisposable()
    private var api = ApiServiceInterface.create()
    override fun subscribe() {

    }

    override fun unsubscrbe() {
        disposable.clear()
    }

    override fun attach(view: QuizPlayContract.View) {
        this.view = view
    }

}
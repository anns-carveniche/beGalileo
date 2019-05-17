package com.carveniche.wisdomleap.presenter

import com.carveniche.wisdomleap.api.ApiInterface
import com.carveniche.wisdomleap.api.QuizApiInterface
import com.carveniche.wisdomleap.contract.QuizHomeContract
import io.reactivex.disposables.CompositeDisposable

class QuizHomePresenter : QuizHomeContract.Presenter {

    private lateinit var view : QuizHomeContract.View
    private var api = QuizApiInterface.create()
    private var disposable = CompositeDisposable()

    override fun subscribe() {

    }

    override fun unSubscribe() {
        disposable.clear()
    }

    override fun attach(view: QuizHomeContract.View) {
        this.view = view
    }
}
package com.carveniche.wisdomleap.presenter

import com.carveniche.wisdomleap.api.ApiInterface
import com.carveniche.wisdomleap.contract.ChapterQuizResultContract
import io.reactivex.disposables.CompositeDisposable

class ChapterQuizResultPresenter : ChapterQuizResultContract.Presenter {

    private lateinit var view: ChapterQuizResultContract.View
    private var api = ApiInterface.create()
    private var disposable = CompositeDisposable()

    override fun subscribe() {

    }

    override fun unSubscribe() {
       disposable.clear()
    }

    override fun attach(view: ChapterQuizResultContract.View) {
       this.view = view
    }
}
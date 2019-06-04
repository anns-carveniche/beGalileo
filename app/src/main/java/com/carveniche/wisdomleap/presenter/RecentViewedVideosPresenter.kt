package com.carveniche.wisdomleap.presenter

import com.carveniche.wisdomleap.contract.RecentViewedQuizContract
import com.carveniche.wisdomleap.contract.RecentViewedVideosContract
import io.reactivex.disposables.CompositeDisposable

class RecentViewedVideosPresenter : RecentViewedVideosContract.Presenter {

    private lateinit var view : RecentViewedVideosContract.View
    private var disposable =  CompositeDisposable()

    override fun subscribe() {

    }

    override fun unSubscribe() {
        disposable.clear()
    }

    override fun attach(view: RecentViewedVideosContract.View) {
        this.view = view
    }
}
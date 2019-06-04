package com.carveniche.wisdomleap.presenter

import com.carveniche.wisdomleap.contract.RecentViewedQuizContract
import com.carveniche.wisdomleap.contract.RecentViewedVideosContract
import io.reactivex.disposables.CompositeDisposable

class RecentViewedQuizPresenter : RecentViewedQuizContract.Presenter {
    private lateinit var view : RecentViewedQuizContract.View
    private var disposable = CompositeDisposable()

    override fun subscribe() {

    }

    override fun unSubscribe() {
       disposable.clear()
    }

    override fun attach(view: RecentViewedQuizContract.View) {
      this.view = view
    }
}
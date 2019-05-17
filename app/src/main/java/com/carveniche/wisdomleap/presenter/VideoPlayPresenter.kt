package com.carveniche.wisdomleap.presenter

import com.carveniche.wisdomleap.contract.VideoPlayContract
import io.reactivex.disposables.CompositeDisposable

class VideoPlayPresenter : VideoPlayContract.Presenter {

    private lateinit var view: VideoPlayContract.View
    private var disposable = CompositeDisposable()

    override fun subscribe() {

    }

    override fun unSubscribe() {
        disposable.clear()
    }

    override fun attach(view: VideoPlayContract.View) {
       this.view = view
    }
}
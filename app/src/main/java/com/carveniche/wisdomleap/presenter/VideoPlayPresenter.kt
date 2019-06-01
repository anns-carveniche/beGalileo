package com.carveniche.wisdomleap.presenter

import com.carveniche.wisdomleap.api.ApiInterface
import com.carveniche.wisdomleap.contract.VideoPlayContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class VideoPlayPresenter : VideoPlayContract.Presenter {

    private lateinit var view: VideoPlayContract.View
    private var disposable = CompositeDisposable()
    private var api = ApiInterface.create()

    override fun subscribe() {

    }

    override fun unSubscribe() {
        disposable.clear()
    }

    override fun attach(view: VideoPlayContract.View) {
       this.view = view
    }
    override fun updateVideoStatus(
        studentId: Int,
        courseId: Int,
        conceptId: Int,
        subConceptId: Int,
        isCompleted: Boolean,
        duration: Long
    ) {
        var videoStateObservable = api.videoStatus(studentId,courseId,conceptId,subConceptId,isCompleted,duration)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            },{

            })
        disposable.add(videoStateObservable)
    }

}
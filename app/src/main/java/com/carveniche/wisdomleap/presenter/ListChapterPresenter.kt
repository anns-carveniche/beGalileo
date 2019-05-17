package com.carveniche.wisdomleap.presenter

import com.carveniche.wisdomleap.api.ApiInterface
import com.carveniche.wisdomleap.contract.ListChapterContract
import com.carveniche.wisdomleap.model.ChapterListModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ListChapterPresenter : ListChapterContract.Presenter {


    lateinit var view : ListChapterContract.View
    private var disposable = CompositeDisposable()
    private var api = ApiInterface.create()

    override fun subscribe() {

    }

    override fun unSubscribe() {
        disposable.clear()
    }

    override fun attach(view: ListChapterContract.View) {
        this.view = view
    }
    override fun loadData(studentId : Int,courseId : Int) {
        view.showProgress(true)
        var chapterListSubsriber = api.getChapterList(studentId, courseId).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.showProgress(false)
                if(it.status)
                    view.onLoadDataSucess(it)
                else
                    view.onLoadDataError(it.message)
            },{
                view.showProgress(false)
                view.onLoadDataError(it.localizedMessage)
            })
        disposable.add(chapterListSubsriber)
    }
}
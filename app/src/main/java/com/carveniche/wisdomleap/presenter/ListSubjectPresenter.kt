package com.carveniche.wisdomleap.presenter

import com.carveniche.wisdomleap.api.ApiInterface
import com.carveniche.wisdomleap.contract.ListSubjctContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ListSubjectPresenter : ListSubjctContract.Presenter {


    private lateinit var view : ListSubjctContract.View
    private var disposable = CompositeDisposable()
    private var api = ApiInterface.create()

    override fun subscribe() {

    }

    override fun unSubscribe() {
        disposable.clear()
    }

    override fun attach(view: ListSubjctContract.View) {
        this.view = view
    }
    override fun loadSubjectData(studentId: Int) {
        view.showProgress(true)
        val loadSubject = api.getSubjectList(studentId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.showProgress(false)
                if(it.status)
                    view.onSubjectLoadSucess(it)
                else
                    view.onSubjectLoadFailed(it.message)
            },{
                view.showProgress(false)
                view.onSubjectLoadFailed(it.localizedMessage)
            })
        disposable.add(loadSubject)
    }
}
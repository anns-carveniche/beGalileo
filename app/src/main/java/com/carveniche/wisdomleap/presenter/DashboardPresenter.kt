package com.carveniche.wisdomleap.presenter

import android.util.Log
import com.carveniche.wisdomleap.api.ApiInterface
import com.carveniche.wisdomleap.contract.DashboardContract
import com.carveniche.wisdomleap.model.SubjectListModel
import com.carveniche.wisdomleap.util.Constants
import com.carveniche.wisdomleap.util.showShortToast
import com.google.android.libraries.places.internal.it
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

class DashboardPresenter : DashboardContract.Presenter {

    private lateinit var view: DashboardContract.View
    private var api = ApiInterface.create()
    private var disposable = CompositeDisposable()

    override fun subscribe() {

    }

    override fun unSubscribe() {
        disposable.clear()
    }

    override fun attach(view: DashboardContract.View) {
        this.view = view
    }
    override fun loadDashboardData(studentId: Int) {
        view.showProgress(true)

        val loadSubject = api.getSubjectList(studentId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
          .subscribe({
                view.showProgress(false)
                if(it.status)
                    view.onLoadDataSucess(it)
                else
                    view.onLoadDataFailed(it.message)
            },{
                view.showProgress(false)
                view.onLoadDataFailed(it.localizedMessage)
            },{

          },{

          })

        disposable.add(loadSubject)
    }

}

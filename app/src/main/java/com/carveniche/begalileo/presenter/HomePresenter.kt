package com.carveniche.begalileo.presenter

import com.carveniche.begalileo.api.ApiServiceInterface
import com.carveniche.begalileo.contract.HomeContract
import com.carveniche.begalileo.util.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class HomePresenter : HomeContract.Presenter {

    lateinit var view : HomeContract.View
    private var api = ApiServiceInterface.create()
    private var disposable = CompositeDisposable()
    override fun loadMessage() {

    }

    override fun loadDashboardDatas(parentId: Int) {
        view.showProgress(true)
        var data = api.getDashboardDataModel(parentId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.showProgress(false)
                if(it.status)
                {
                    view.onDashboardLoadSuccess(it)
                }
                else
                {
                    view.onDashboardLoadFailed(Constants.ExceptionMessage)
                }
            },{
                view.showProgress(false)
                view.onDashboardLoadFailed(it.localizedMessage)
            })
        disposable.add(data)
    }

    override fun subscribe() {

    }

    override fun unsubscrbe() {

    }

    override fun attach(View: HomeContract.View) {
        this.view = View
    }
}
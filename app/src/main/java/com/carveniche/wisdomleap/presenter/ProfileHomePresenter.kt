package com.carveniche.wisdomleap.presenter

import com.carveniche.wisdomleap.api.ApiInterface
import com.carveniche.wisdomleap.contract.ProfileHomeContract
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ProfileHomePresenter : ProfileHomeContract.Presenter {


    @Inject lateinit var presenter: ProfileHomeContract.Presenter

    private lateinit var view : ProfileHomeContract.View
    private var api = ApiInterface.create()
    private var disposable = CompositeDisposable()

    override fun subscribe() {

    }

    override fun unSubscribe() {
        disposable.clear()
    }

    override fun attach(view: ProfileHomeContract.View) {
        this.view = view
    }

}
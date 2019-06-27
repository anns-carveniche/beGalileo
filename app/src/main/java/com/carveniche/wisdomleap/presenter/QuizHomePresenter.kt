package com.carveniche.wisdomleap.presenter

import android.util.Log
import com.carveniche.wisdomleap.api.ApiInterface
import com.carveniche.wisdomleap.api.QuizApiInterface
import com.carveniche.wisdomleap.contract.QuizHomeContract
import com.carveniche.wisdomleap.util.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class QuizHomePresenter : QuizHomeContract.Presenter {

    private lateinit var view : QuizHomeContract.View
    private var api = ApiInterface.create()
    private var disposable = CompositeDisposable()

    override fun subscribe() {

    }

    override fun unSubscribe() {
        disposable.clear()
    }

    override fun attach(view: QuizHomeContract.View) {
        this.view = view
    }
    override fun loadUserCoins(studentId: Int) {
        Log.d(Constants.LOG_TAG,"Student Id $studentId")
        var coinsObs = api.getUserCoins(studentId).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.onCoinsLoad(it.coins)
            }
        disposable.add(coinsObs)
    }

}
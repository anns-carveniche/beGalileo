package com.carveniche.wisdomleap.presenter

import com.carveniche.wisdomleap.contract.QuizResultContract
import io.reactivex.disposables.CompositeDisposable

class QuizResultPresenter : QuizResultContract.Presenter {
   lateinit var view : QuizResultContract.View
   private var disposable = CompositeDisposable()
    override fun subscribe() {

    }

    override fun unSubscribe() {
        disposable.clear()
      }

    override fun attach(view: QuizResultContract.View) {
        this.view = view
     }
}
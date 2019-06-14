package com.carveniche.wisdomleap.presenter

import android.view.View
import android.widget.TextView
import com.carveniche.wisdomleap.api.ApiInterface
import com.carveniche.wisdomleap.api.QuizApiInterface
import com.carveniche.wisdomleap.contract.MultiplayerPlayQuizContract
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit

class MultiplayerQuizPlayPresenter : MultiplayerPlayQuizContract.Presenter {


    private lateinit var view : MultiplayerPlayQuizContract.View
    private var api = QuizApiInterface.create()
    private var disposable = CompositeDisposable()

    override fun subscribe() {

    }

    override fun unSubscribe() {
        disposable.clear()
    }
    override fun attach(view: MultiplayerPlayQuizContract.View) {
        this.view = view
    }
    override fun loadQuizQuestions(amount: Int, category: Int, difficulty: String, type: String) {
        view.showProgress(true)
        var loadQuesObservable = api.getQuizQuestions(amount,category,difficulty,type)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.questionLoadSuccess(it)

            },{
                view.showProgress(false)
                view.questionLoadFailed(it.localizedMessage)
            })
        disposable.add(loadQuesObservable)
    }
    override fun verifyAnswer(tvOption: View, userAnswer: String, correctAnswer: String) {
            view.showAnswerStatus(tvOption,userAnswer==correctAnswer)
    }


}
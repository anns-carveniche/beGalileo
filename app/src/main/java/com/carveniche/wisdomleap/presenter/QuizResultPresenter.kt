package com.carveniche.wisdomleap.presenter

import android.util.Log
import com.carveniche.wisdomleap.api.ApiInterface
import com.carveniche.wisdomleap.contract.QuizResultContract
import com.carveniche.wisdomleap.util.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class QuizResultPresenter : QuizResultContract.Presenter {


    lateinit var view : QuizResultContract.View
   private var disposable = CompositeDisposable()
    private var api = ApiInterface.create()
    override fun subscribe() {

    }

    override fun unSubscribe() {
        disposable.clear()
      }

    override fun attach(view: QuizResultContract.View) {
        this.view = view
     }
    override fun submitQuizResult(studentId: Int, categoryId: Int, level: String, total: Int, correct: Int,timeSpent : Int) {
        var submitQuizSubscriber = api.saveCategoryQuiz(studentId,categoryId,level,total,correct,timeSpent)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(Constants.LOG_TAG,it.toString())
            },{
                Log.d(Constants.LOG_TAG,it.localizedMessage)
            })
        disposable.add(submitQuizSubscriber)
    }
}
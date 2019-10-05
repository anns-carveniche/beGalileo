package com.carveniche.begalileo.presenter

import com.carveniche.begalileo.api.ApiServiceInterface
import com.carveniche.begalileo.contract.BaseContract
import com.carveniche.begalileo.contract.PracticeMathContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PracticeMathPresenter : PracticeMathContract.Presenter {


    lateinit var view : PracticeMathContract.View
    private var api = ApiServiceInterface.create()
    private var disposable = CompositeDisposable()

    override fun subscribe() {

    }

    override fun unsubscrbe() {
        disposable.clear()
    }

    override fun attach(view: PracticeMathContract.View) {
        this.view = view
    }

    override fun getPracticeQuizQuestions(qNumber: Int) {
         view.showProgress(true)
        var practiceQuestion = api.getPracticeQuizQuestion(qNumber)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.showProgress(false)
                if(it.status)
                {
                    view.onPracticeQuizQuestionLoadSuccess(it)
                }
                else
                {
                    view.onPracticeQuizQuestionLoadFailed("Unable to fetch data")
                }
            }, {
                view.showProgress(false)
                view.onPracticeQuizQuestionLoadFailed("Unable to fetch data"+it.localizedMessage)
            })
        disposable.add(practiceQuestion)

    }


}
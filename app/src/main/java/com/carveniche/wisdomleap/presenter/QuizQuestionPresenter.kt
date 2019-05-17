package com.carveniche.wisdomleap.presenter


import com.carveniche.wisdomleap.api.ApiInterface
import com.carveniche.wisdomleap.api.QuizApiInterface
import com.carveniche.wisdomleap.contract.QuizQuestionContract
import io.reactivex.android.schedulers.AndroidSchedulers


import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class QuizQuestionPresenter  : QuizQuestionContract.Presenter {


    lateinit var view : QuizQuestionContract.View
    private var disposable = CompositeDisposable()
    private var api = QuizApiInterface.create()


    override fun subscribe() {

    }

    override fun unSubscribe() {
        disposable.clear()
    }

    override fun attach(view: QuizQuestionContract.View) {
        this.view = view
    }
    override fun validateAnswer() {

    }

    override fun showNextQuestion() {

    }
    override fun loadQuizQuestions(amount: Int, category: Int, difficulty: String, type: String) {
        view.showProgress(true)
        var loadQuesObservable = api.getQuizQuestions(amount,category,difficulty,type)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.showProgress(false)
                view.loadQuestionSucess(it)
            },{
                view.showProgress(false)
                view.loadFailed(it.localizedMessage)
            })
        disposable.add(loadQuesObservable)
    }
}
package com.carveniche.wisdomleap.presenter

import android.util.Log
import com.carveniche.wisdomleap.api.ApiInterface
import com.carveniche.wisdomleap.contract.ChapterQuizContract
import com.carveniche.wisdomleap.util.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ChapterQuizPresenter : ChapterQuizContract.Presenter {



    private lateinit var view : ChapterQuizContract.View
    private var disposable = CompositeDisposable()
    private var api = ApiInterface.create()

    override fun subscribe() {

    }

    override fun unSubscribe() {
        disposable.clear()
    }

    override fun attach(view: ChapterQuizContract.View) {
        this.view = view
    }
    override fun loadQuestionDatas(studentId: Int, courseId: Int, chapterId: Int) {
        view.showProgress(true)
        var chapterQues = api.getChapterQuizQuestions(studentId,courseId,chapterId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.showProgress(false)
                if(it.status)
                    view.onQuestionLoadSucess(it)
                else
                    view.onQuestionLoadFailed("Something went wrong pls try again")
            },{
                    view.showProgress(false)
                    view.onQuestionLoadFailed(it.localizedMessage)
            })

        disposable.add(chapterQues)

    }
    override fun saveQuiz(
        studentId: Int,
        quizId: Int,
        questionId: Int,
        questionIndex: Int,
        choiceId: Int,
        timeSpent : Int,
        isCorrect: Boolean,
        isCompleted: Boolean
    ) {
        var saveQuizSubscriber = api.saveQuiz(studentId,quizId,questionId,questionIndex,choiceId,timeSpent,isCorrect,isCompleted)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(Constants.LOG_TAG,it.toString())
            },{
                Log.d(Constants.LOG_TAG,it.localizedMessage)
            })
        disposable.add(saveQuizSubscriber)
    }
    override fun skipQuiz(
        studentId: Int,
        quizId: Int,
        questionId: Int,
        questionIndex: Int
    ) {
        var saveQuizSubscriber = api.skipQuiz(studentId,quizId,questionId,questionIndex)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(Constants.LOG_TAG,it.toString())
            },{
                Log.d(Constants.LOG_TAG,it.localizedMessage)
            })
        disposable.add(saveQuizSubscriber)
    }
}
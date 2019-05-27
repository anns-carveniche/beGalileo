package com.carveniche.begalileo.ui.speedMath.fragments.mathWithComputer

import android.content.Context
import android.util.Log
import com.carveniche.begalileo.Constants
import com.carveniche.begalileo.R
import com.carveniche.begalileo.api.ApiServiceInterface
import com.carveniche.begalileo.models.GameQuestion
import com.carveniche.begalileo.ui.base.BaseContract
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.random.Random

class MathWithComputerPresenter : MathWithComputerContract.Presenter {
    private lateinit var view : MathWithComputerContract.View
    private var subscriptions  = CompositeDisposable()
    private var apiInterface  = ApiServiceInterface.create()

    override fun subscribe() {
        var timerSubscription = Observable.interval(0,1,TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.updateTime()
            }
        subscriptions.add(timerSubscription)
    }
    override fun startGameTimer(state: Boolean) {

    }
    override fun startGameQuestions(gameQuestion: GameQuestion) {
        val question  = gameQuestion.question
        val answer  = gameQuestion.answer
        view.updateQuestion(question,answer)
    }
    override fun nextGameQuestion(gameQuestion: GameQuestion) {


    }
    override fun unsubscrbe() {
        subscriptions.clear()
    }

    override fun attach(view: MathWithComputerContract.View
    ) {
        this.view = view
    }
    override fun loadGameQuestions(context:Context,levelId: Int, players: ArrayList<Int>) {

        view.showProgress(true)
        var questionObservable  =  apiInterface
            .getGameQuestions(levelId,Constants.COMPUTER,players)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.showProgress(false)
                if(it.status)
                {
                    view.loadDataSuccess(it)
                }
                else
                {
                    view.loadDataFailed(context.getString(R.string.unable_to_fetch_data))
                }
            },
                {
                    view.showProgress(false)
                    view.loadDataFailed(context.getString(R.string.unable_to_fetch_data)+" ${it.localizedMessage}")

                })
        subscriptions.add(questionObservable)

    }


}
package com.carveniche.begalileo.util

import android.util.Log
import com.carveniche.begalileo.models.PlayerGameStatusModel
import io.reactivex.Observable

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit



class ComputerPlayer(private var viewModelProvider: PlayerGameStatusModel) {
    private var gameScore = 0

    private var random = Random()
    var subscriptions = CompositeDisposable()
    init {
       val startObservable =  Observable.timer(3,TimeUnit.SECONDS).subscribe {
           startPlay()
       }
        subscriptions.add(startObservable)

    }

    private fun startPlay()
    {
      val sub =   Observable.defer{
          val pollDelay = intArrayOf(0)
            return@defer Observable.just(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete {
                    pollDelay[0] = getAnswerIntervalTime().toInt()
                    updateScore()
                }
                .repeatWhen{
                    it.flatMap {
                        return@flatMap Observable.timer(pollDelay[0].toLong(),TimeUnit.SECONDS)
                    }
                }
                .takeUntil {
                    gameScore>60
                }
        }.delay(5,TimeUnit.SECONDS)
       subscriptions.add(sub.subscribe())
    }

    private fun updateScore() {
        gameScore++
        viewModelProvider.updateGameScore(gameScore)
    }
     fun stopGame()
    {
        subscriptions.clear()
    }

    private fun getAnswerIntervalTime() : Long
    {
        val interval =  random.getInt(2..5)
        Log.d(Constants.LOG_TAG,"Interval $interval")
        return interval.toLong()
    }

}
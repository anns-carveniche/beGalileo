package com.carveniche.wisdomleap.presenter

import android.util.Log
import android.view.View
import com.carveniche.wisdomleap.api.ApiInterface
import com.carveniche.wisdomleap.api.RandomNameApiInterface
import com.carveniche.wisdomleap.contract.MultiplayerSearchContract
import com.carveniche.wisdomleap.util.Config
import com.carveniche.wisdomleap.util.Constants
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class MultiplayerSearchPresenter : MultiplayerSearchContract.Presenter {


    private lateinit var view : MultiplayerSearchContract.View
    private var randomNameApi = RandomNameApiInterface.create()

    private var disposable = CompositeDisposable()

    override fun subscribe() {

    }

    override fun unSubscribe() {
        disposable.clear()
    }

    override fun attach(view: MultiplayerSearchContract.View) {
        this.view = view
    }
    override fun searchRandomPlayer() {
        view.randomPlayerAnimation(true)
        Observable
            .timer(3000, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {   showRandomPlayer() }
            .subscribe()
    }

    private fun showRandomPlayer()  {

        var randomName = randomNameApi.getRandomName("india").subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.randomPlayerAnimation(false)
                view.showRandomPlayer(it.name+" "+it.surname,Config.MULTIPLAYER_BET_VALUE)
            },{
                Log.d(Constants.LOG_TAG,it.localizedMessage)
                view.randomPlayerAnimation(false)
                var user = Random.nextInt(100,999)
                view.showRandomPlayer("user$user", Config.MULTIPLAYER_BET_VALUE)
            })
        disposable.add(randomName)
    }
}
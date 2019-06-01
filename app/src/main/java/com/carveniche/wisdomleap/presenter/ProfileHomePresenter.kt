package com.carveniche.wisdomleap.presenter

import com.carveniche.wisdomleap.api.ApiInterface
import com.carveniche.wisdomleap.contract.ProfileHomeContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProfileHomePresenter : ProfileHomeContract.Presenter {


    @Inject lateinit var presenter: ProfileHomeContract.Presenter

    private lateinit var view : ProfileHomeContract.View
    private var api = ApiInterface.create()
    private var disposable = CompositeDisposable()

    override fun subscribe() {

    }

    override fun unSubscribe() {
        disposable.clear()
    }

    override fun attach(view: ProfileHomeContract.View) {
        this.view = view
    }
    override fun loadProfileDatas(studentId: Int) {
        view.showProgress(true)
        var profileLoadSubcriber = api.getStudentProfile(studentId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.showProgress(false)
                if(it.status)
                    view.onProfileLoadSucess(it)
                else
                    view.onProfileLoadFailed("Unable to fetch data please try again")

            },{
                view.showProgress(false)
                view.onProfileLoadFailed(it.localizedMessage)
            })
        disposable.add(profileLoadSubcriber)
    }
  override fun submitProfileDatas(studentId: Int, firstName: String, lastName: String, schoolName: String) {
        view.showProgress(false)
        var submitProfileSubscriber = api.updateProfile(studentId,firstName,lastName,schoolName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.showProgress(false)
                if(it.status)
                    view.submitProfileDataSuccess(firstName,lastName,schoolName)
                else
                    view.submitProfileDataFailed(it.message)
            },{
                view.showProgress(false)
               view.submitProfileDataFailed(it.localizedMessage)
            })
        disposable.add(submitProfileSubscriber)
    }
}
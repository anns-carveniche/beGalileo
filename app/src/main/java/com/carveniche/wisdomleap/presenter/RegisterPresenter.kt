package com.carveniche.wisdomleap.presenter

import android.util.Log
import android.view.View
import com.carveniche.wisdomleap.api.ApiInterface
import com.carveniche.wisdomleap.contract.RegisterContract
import com.carveniche.wisdomleap.util.Constants
import com.carveniche.wisdomleap.util.isValidEmail
import com.carveniche.wisdomleap.util.showShortToast
import com.google.android.libraries.places.internal.it
import io.reactivex.Observable
import io.reactivex.Observable.combineLatest
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function
import io.reactivex.rxkotlin.Observables
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_register.view.*
import io.reactivex.rxkotlin.Observables.combineLatest as combineLatest1

class RegisterPresenter : RegisterContract.Presenter {


    lateinit var view : RegisterContract.View
    private var disposable = CompositeDisposable()
    private val api = ApiInterface.create()

    override fun subscribe() {
        val emailObservable = view.emailObservable().map(validateEmail())
        val cityObservable   = view.cityNameObserver().map(validateCity())
        val nameObservable =view.name().map(isValidName())
        val lastNameObservable  = view.lastName().map(isValidLastName())


       /* val nameCombinedCheck  = Observables.combineLatest(
            nameObservable,
            emailObservable,
            lastNameObservable).subscribe {
            view.updateSubmitButton(it.first && it.second && it.third)
        }*/

        val nameCombinedCheck  = Observables.combineLatest(
            nameObservable,
            lastNameObservable)
        val emailCityCombinedCheck  = Observables.combineLatest(
            emailObservable,
            cityObservable)

        val totCombinedCheck  = Observables.combineLatest(
            nameCombinedCheck,
            emailCityCombinedCheck).subscribe{
            var nameCheck = it.first.first && it.second.second
            var ecCheck = it.second.first && it.second.second
            view.updateSubmitButton(nameCheck && ecCheck)
        }


        disposable.add(totCombinedCheck)

    }

    private fun validGrade(): Function<in Int, out Boolean>? {
        return Function {
            it!=-1
        }
    }


    private fun validateCity(): Function<in CharSequence, out Boolean>? {
        return Function {
            it.length > 2
        }
    }


    private fun validateEmail(): io.reactivex.functions.Function<CharSequence, Boolean> {
       return Function {
           it.toString().isValidEmail()
       }
    }
    override fun unSubscribe() {
        disposable.clear()
    }

    override fun attach(view: RegisterContract.View) {
        this.view = view

    }

    private fun isValidName(): Function<CharSequence,Boolean> {
        return Function {
            it.length >= 2
        }
    }
    private fun isValidLastName(): Function<CharSequence,Boolean> {
        return Function {
            it.isNotEmpty()
        }
    }

    override fun getGradeList() {
        view.showProgress(true)
        val gradeListObservable = api.gradeList().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.showProgress(false)
                if(it.status)
                    view.gradeListLoadSucess(it)
                else
                    view.gradeListLoadFailed(it.message)
            },{
                view.showProgress(false)
                view.gradeListLoadFailed(it.localizedMessage)
            })
        disposable.add(gradeListObservable)

    }
    override fun submitRegisterDetails(
        mobileNumber: String,
        email: String,
        firstName: String,
        lastName: String,
        gradeId: Int,
        city: String,
        referralCode : String
    ) {
        view.showProgress(true)
        var submitObservable = api.submitRegsiterDetails(mobileNumber,email,firstName,lastName,gradeId,city,referralCode)
        .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.showProgress(false)
                if(it.status)
                    view.onSubmitDetailsSucess()
                else
                    view.onSubmitDetailFailed(it.message)
            },{
                view.showProgress(false)
                view.onSubmitDetailFailed(it.localizedMessage)
            })
        disposable.add(submitObservable)
    }
    override fun validateReferralCode(userId: Int, referralCode: String) {
        view.showProgress(true)
        var submitReferral = api.validateRefferalCode(userId,referralCode)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.showProgress(false)
                view.verifyReferralState(it.status)
            },{
                view.showProgress(false)
                view.onSubmitDetailFailed(it.localizedMessage)
            })
        disposable.add(submitReferral)
    }


}
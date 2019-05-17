package com.carveniche.wisdomleap.presenter

import android.view.View
import com.carveniche.wisdomleap.api.ApiInterface
import com.carveniche.wisdomleap.contract.RegisterContract
import com.carveniche.wisdomleap.util.isValidEmail
import com.google.android.libraries.places.internal.it
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_register.view.*

class RegisterPresenter : RegisterContract.Presenter {

    lateinit var view : RegisterContract.View
    private var disposable = CompositeDisposable()
    private val api = ApiInterface.create()

    override fun subscribe() {
        val emailObservable = view.emailObservable().map(validateEmail())
        val emailSubscribe = emailObservable.subscribe {
            view.updateSubmitButton(it)
        }
        disposable.add(emailSubscribe)
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
    override fun submitRegisterDetails(mobileNumber: String, email: String, gradeId: Int, city: String) {
        view.showProgress(true)
        var submitObservable = api.submitRegsiterDetails(mobileNumber,email,gradeId,city)
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


}
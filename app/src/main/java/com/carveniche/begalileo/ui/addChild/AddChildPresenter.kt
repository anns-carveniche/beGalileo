package com.carveniche.begalileo.ui.addChild

import com.carveniche.begalileo.Constants
import com.carveniche.begalileo.api.ApiServiceInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers


class AddChildPresenter : AddChildContract.Presenter{



    private lateinit var view: AddChildContract.View
    private val subscriptions = CompositeDisposable()
    private var apiInterface = ApiServiceInterface.create()
    override fun subscribe() {

    }
    override fun loadData() {
        view.showProgress(true)
        val subscribe = apiInterface.getGradeBoardDetails().subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
            if(it.status)
            {
                view.loadDataSucess(it)
                view.showProgress(false)
            }
            else
            {
                view.showProgress(false)
                view.loadDataError(Constants.UNKNOWN_ERROR)

            }

        },{
            error->
                view.showProgress(false)
                view.loadDataError(error.localizedMessage)
            }
        )

    }
    override fun unsubscrbe() {
        subscriptions.clear()

    }
    override fun addChild(
        parentId: Int,
        firstName: String,
        lastName: String,
        gender: String,
        grade: Int,
        goal: String,
        board: Int,
        schoolName: String,
        schoolCity: String
    ) {
        view.showProgress(true)
        val subscribeAdd = apiInterface.addChild(parentId,firstName,lastName,gender,grade,board,goal,schoolName,schoolCity).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.status)
                {
                    view.showProgress(false)
                    view.addChildSuccess(it)
                }
                else
                {
                    view.showProgress(false)
                    view.addChildFailed(Constants.UNKNOWN_ERROR)
                }


            },{

                view.showProgress(false)
                view.addChildFailed(it.localizedMessage)
            }
            )


    }
//8883197479

    override fun attach(view: AddChildContract.View) {
        this.view = view
        val nameObservable  = view.name().map(isValidName())
        val nameSubscriber = nameObservable.subscribe {
            view.updateNameButtonState(it)
        }
        val schoolNameObservable = view.schoolName().map(isValidName())
        val schoolCityObservable = view.schoolCity().map(isValidName())

      val schoolSubscriber =  schoolNameObservable.mergeWith(schoolCityObservable).subscribe {
           view.updateSchoolSubmitButtonState(it)
       }

        subscriptions.add(schoolSubscriber)
        subscriptions.add(nameSubscriber)
    }

    private fun isValidName(): Function<CharSequence,Boolean> {
        return Function {
             it.length >= 4
        }
    }



}
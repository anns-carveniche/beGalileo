package com.carveniche.begalileo.presenter

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import com.carveniche.begalileo.api.ApiServiceInterface
import com.carveniche.begalileo.contract.UserLocationContractor
import com.google.android.gms.location.FusedLocationProviderClient
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.lang.Exception


class UserLocationPresenter : UserLocationContractor.Presenter {



    private lateinit var view: UserLocationContractor.View
    private var subscriptions = CompositeDisposable()
    private lateinit var context : Context
    private var apiInterface : ApiServiceInterface = ApiServiceInterface.create()

    override fun subscribe() {

    }

    override fun unsubscrbe() {
      subscriptions.clear()
    }

    override fun attach(view: UserLocationContractor.View) {
             this.view = view
    }

    override fun getNearbyCenters(
        parent_id: Int,
        address: String,
        city: String,
        state: String,
        country: String,
        latitude: Double,
        longitude: Double
    ) {
        view.showProgress(true)
        var subscriber = apiInterface.getNearbyLocation(parent_id,address,city,state,country,latitude,longitude)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                    view.showProgress(false)
                if(it.center_data.isNotEmpty())
                    view.doGetCenterDatasSucess(it)
                else
                    view.noCenterNearby()
            },{
                view.showProgress(false)
                view.showErrorMessage(it.localizedMessage)
            },{

            })
    }


    @SuppressLint("MissingPermission")
    override fun getLastLocation(fusedLocationProviderClient: FusedLocationProviderClient,geocoder: Geocoder) {
        fusedLocationProviderClient.lastLocation.addOnCompleteListener {
            task->
            run {
                if (task.isSuccessful && task.result != null) {
                    val location = task.result
                    reverseGeoCode(location!!.latitude, location!!.longitude,geocoder)
                } else {
                    view.showErrorMessage("Unable to get location")
                }
            }
        }
    }




    override fun reverseGeoCode(mLatitude:Double,mLongitude:Double,geocoder: Geocoder)
    {
        var subject : BehaviorSubject<Geocoder> = BehaviorSubject.create()
        subject.onNext(geocoder)
       var subscriber =  subject.observeOn(Schedulers.io())
            .map {
                try {
                    return@map it.getFromLocation(mLatitude,mLongitude,1)
                }
                catch (ae:Exception)
                {
                    throw ae
                }
            }
            .flatMap { return@flatMap Observable.just(it.first()) }
            .filter {
                address->address.maxAddressLineIndex >=0
            }
           .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

                view.showLocation(it)
            },{
                view.showErrorMessage(it.localizedMessage)
            },{
                subject.onComplete()
            })
        subscriptions.add(subscriber)
    }
}
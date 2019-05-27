package com.carveniche.begalileo.ui.userLocation


import android.location.Address
import android.location.Geocoder
import com.carveniche.begalileo.models.NearCenterModel
import com.carveniche.begalileo.ui.base.BaseContract
import com.google.android.gms.location.FusedLocationProviderClient
import io.reactivex.Observable

class UserLocationContractor {
    interface View:BaseContract.View
    {
        fun showLocation(address: Address)
        fun showErrorMessage(msg: String)
        fun doGetCenterDatasSucess(nearCenterModel: NearCenterModel)
        fun noCenterNearby()

    }
    interface Presenter:BaseContract.Presenter<UserLocationContractor.View>
    {
        fun reverseGeoCode(mLatitude : Double,mLongitude : Double,geocoder: Geocoder)
        fun getLastLocation(fusedLocationProviderClient: FusedLocationProviderClient,geocoder: Geocoder)
        fun getNearbyCenters(parent_id : Int,address: String,city: String,state: String,country: String,latitude: Double,longitude: Double)
    }
}
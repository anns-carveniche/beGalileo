package com.carveniche.begalileo.ui.userLocation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.carveniche.begalileo.R
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.location.Address

import android.location.Geocoder
import android.util.Log
import android.view.View

import com.carveniche.begalileo.Constants
import com.carveniche.begalileo.di.component.DaggerActivityComponent
import com.carveniche.begalileo.di.module.ActivityModule
import com.carveniche.begalileo.di.module.ContextModule
import com.carveniche.begalileo.models.MySharedPreferences
import com.carveniche.begalileo.models.NearCenterModel
import com.carveniche.begalileo.ui.nearbyCenters.NearbyCenterActivity
import com.carveniche.begalileo.util.showLongToast

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices



import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AddressComponent
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity

import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.LENGTH_INDEFINITE


import kotlinx.android.synthetic.main.activity_user_location.*
import kotlinx.android.synthetic.main.layout_progressbar.*
import java.util.*

import javax.inject.Inject


class UserLocationActivity : AppCompatActivity(),UserLocationContractor.View {



    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val REQUEST_PERMISSIONS_REQUEST_CODE = 34
    private val AUTOCOMPLETE_REQUEST_CODE = 33
    var mLatitude: Double = 0.0
    var mLongitude: Double = 0.0
    var mAddress = ""
    var mCity = ""
    var mState = ""
    var mCountry = ""
    private lateinit var geocoder : Geocoder
    @Inject lateinit var presenter: UserLocationContractor.Presenter
    @Inject lateinit var mySharedPreferences: MySharedPreferences
    private var mParentId = 0
    private var isAutoLocatedFlag = false
    lateinit var selectedPlace  : Place

    var fieldList : List<Place.Field>  = Arrays.asList(Place.Field.ID,
                                                        Place.Field.NAME,
                                                        Place.Field.ADDRESS,
                                                        Place.Field.ID,
                                                        Place.Field.LAT_LNG,
                                                        Place.Field.ADDRESS_COMPONENTS)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_location)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        geocoder = Geocoder(this)
        injectDependency()
        presenter.attach(this)
        if(!Places.isInitialized())
        {
            Places.initialize(this,getString(R.string.place_api_key))
        }
        initUI()
        tvGetCurrentLocation.setOnClickListener {
            getCurrentLocation()
        }


    }

    private fun initUI() {
        tvLocation.setOnClickListener {
            openAutoCompletePlaceActivity()
        }
        btnSubmit.setOnClickListener {
            doSubmitLocation()
        }
        tvAddressManually.setOnClickListener {
            openCustomDialogFragment()
        }

    }

    private fun openCustomDialogFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val prev = supportFragmentManager.findFragmentByTag("dialog")
        if(prev!=null)
        {
            fragmentTransaction.remove(prev)
        }
        fragmentTransaction.addToBackStack(null)
        val dialogFragment = CustomAddressDialogFragment()
        dialogFragment.show(fragmentTransaction,"dialog")
    }
    override fun doGetCenterDatasSucess(nearCenterModel: NearCenterModel) {
        var intent = Intent(this,NearbyCenterActivity::class.java)
        intent.putExtra(Constants.NEARBY_CENTERS,nearCenterModel)
        startActivity(intent)
    }
    private fun doSubmitLocation() {
        if(mLongitude.equals(0.0)||mLatitude.equals(0.0))
        {
            showLongToast("Please select any location to continue",this)
        }
        else
        {
            presenter.getNearbyCenters(mParentId,mAddress,mCity,mState,mCountry,mLatitude,mLongitude)
        }
    }

    private fun openAutoCompletePlaceActivity() {
        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN,fieldList)
            .setCountry("IN")
            .build(this)
        startActivityForResult(intent,AUTOCOMPLETE_REQUEST_CODE)
    }

    override fun noCenterNearby() {
        showLongToast("Currenlty no center available near the location",this)
    }
    private fun injectDependency() {
        val activityComponent = DaggerActivityComponent.builder()
            .activityModule(ActivityModule(this))
            .contextModule(ContextModule(this))
            .build()
        activityComponent.inject(this)
    }


    private fun getCurrentLocation()
    {
        isAutoLocatedFlag = true
        if(!checkPermission())
        {
            requestPermission()
        }
        else
        {
            presenter.getLastLocation(fusedLocationProviderClient,geocoder)
        }
    }




    private fun startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(this, arrayOf(ACCESS_COARSE_LOCATION),
            REQUEST_PERMISSIONS_REQUEST_CODE)
    }
    private fun requestPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, ACCESS_COARSE_LOCATION))
        {
            showSnackbar(R.string.permission_rationale, android.R.string.ok, View.OnClickListener {
                // Request permission
                startLocationPermissionRequest()
            })
        }
        else
        {
            startLocationPermissionRequest()
        }
    }

    private fun showSnackbar(
        snackStrId: Int,
        actionStrId: Int = 0,
        listener: View.OnClickListener? = null
    ) {
        val snackbar = Snackbar.make(findViewById(android.R.id.content), getString(snackStrId),
            LENGTH_INDEFINITE)
        if (actionStrId != 0 && listener != null) {
            snackbar.setAction(getString(actionStrId), listener)
        }
        snackbar.show()
    }

    override fun showLocation(address: Address) {
        mLatitude = address.latitude
        mLongitude = address.longitude

        mCity = address.locality
        mCountry = address.countryName
        mState = address.adminArea
        mAddress = address.getAddressLine(0)
        mParentId = mySharedPreferences.getIntData(Constants.PARENT_ID)

        if(isAutoLocatedFlag)
            tvLocation.text = mAddress
        else
        {
            val customLocation = selectedPlace.name+", "+mCity+", "+mState+", "+mCountry
            tvLocation.text = customLocation
        }

        Log.d(Constants.LOG_TAG,"$mLatitude -- $mLongitude")
        Log.d(Constants.LOG_TAG,mCity)
        Log.d(Constants.LOG_TAG,mCountry)
        Log.d(Constants.LOG_TAG,mState)
        Log.d(Constants.LOG_TAG,mAddress)
        Log.d(Constants.LOG_TAG,mParentId.toString())

    }

    override fun showProgress(boolean: Boolean) {
        if(boolean)
            progressBar.visibility = View.VISIBLE
        else
            progressBar.visibility = View.GONE
    }



    private fun checkPermission()  = ActivityCompat.checkSelfPermission(this,ACCESS_COARSE_LOCATION) == PERMISSION_GRANTED

    override fun showErrorMessage(msg: String) {
        showLongToast(msg,this)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode==AUTOCOMPLETE_REQUEST_CODE)
        {
            when (resultCode) {
                Activity.RESULT_OK -> {

                     selectedPlace = Autocomplete.getPlaceFromIntent(data!!)
                    mLatitude = selectedPlace.latLng!!.latitude
                    mLongitude = selectedPlace.latLng!!.longitude
                    isAutoLocatedFlag = false
                    presenter.reverseGeoCode(mLatitude,mLongitude,geocoder)

                }
                AutocompleteActivity.RESULT_ERROR -> {
                    val status = Autocomplete.getStatusFromIntent(data!!)
                    showLongToast(status.statusMessage!!,this)
                    Log.i(Constants.LOG_TAG,status.statusMessage)
                }
                else -> {

                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==REQUEST_PERMISSIONS_REQUEST_CODE)
        {
            when
            {
                grantResults.isEmpty()->Log.i(Constants.LOG_TAG,"User Interaction Cancelled")
                (grantResults[0]== PackageManager.PERMISSION_GRANTED)->presenter.getLastLocation(fusedLocationProviderClient,geocoder)
                else->{
//                    showSnackbar(R.string.permission_denied_explanation,R.string.settings,View.OnClickListener {
//                        val intent = Intent().apply {
//                            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
//                            data = Uri.fromParts("package", APPLICATION_ID, null)
//                            flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                        }
//                        startActivity(intent)
//                    })
                    showLongToast("Location Permission Needed to get Current location,please try again",this)
                }
            }
        }
    }

}





package com.carveniche.wisdomleap.view.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import com.carveniche.wisdomleap.R
import com.carveniche.wisdomleap.adapter.GradeListAdapter
import com.carveniche.wisdomleap.contract.RegisterContract
import com.carveniche.wisdomleap.di.component.DaggerActivityComponent
import com.carveniche.wisdomleap.di.module.ActivityModule
import com.carveniche.wisdomleap.di.module.ContextModule
import com.carveniche.wisdomleap.di.module.SharedPreferenceModule
import com.carveniche.wisdomleap.interfaces.IGradeClickListener
import com.carveniche.wisdomleap.model.GradeDetail
import com.carveniche.wisdomleap.model.GradeListModel
import com.carveniche.wisdomleap.model.MySharedPreferences
import com.carveniche.wisdomleap.util.Constants
import com.carveniche.wisdomleap.util.showLoadingProgress

import com.carveniche.wisdomleap.util.showLongToast
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.layout_progressbar.*
import java.util.*
import javax.inject.Inject

class RegisterActivity : AppCompatActivity(),RegisterContract.View,IGradeClickListener {

    @Inject
    lateinit var mySharedPreferences: MySharedPreferences
    @Inject lateinit var presenter: RegisterContract.Presenter
    private lateinit var geocoder : Geocoder
    private val AUTOCOMPLETE_REQUEST_CODE = 33
    private var fieldList : List<Place.Field>  = Arrays.asList(
        Place.Field.ID,
        Place.Field.ADDRESS)
    lateinit var selectedPlace  : Place
    private var isAutoLocatedFlag = false
    private lateinit var gradeList : List<GradeDetail>
    private var mSelectedGradeId = 0
    private var mSelectedCity = ""
    private var mMobileNumber = ""
    private var mEmail = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        if(!Places.isInitialized())
        {
            Places.initialize(this,getString(R.string.place_api_key))
        }
        injectDependency()
        initUI()
        mMobileNumber = mySharedPreferences.getString(Constants.MOBILE_NUMBER)
        presenter.attach(this)
        presenter.subscribe()


        presenter.getGradeList()

    }

    private fun initUI() {

        tvCity.setOnClickListener {
            openAutoCompletePlaceActivity()
        }
        btnNext.setOnClickListener {
           if(tvCity.text.isNotEmpty())
                viewflipper.showNext()
            else
           {
               showLongToast("Please select your city",this)
           }
        }
        btnSubmit.setOnClickListener {
            mEmail = edEmail.text.toString()
            presenter.submitRegisterDetails(mMobileNumber,mEmail,mSelectedGradeId,mSelectedCity)
        }
    }


    override fun onGradeClick(id: Int) {
        mSelectedGradeId  = gradeList[id].id
        Log.d(Constants.LOG_TAG,id.toString())
        viewflipper.showNext()
    }
    private fun injectDependency() {
        val activityComponent = DaggerActivityComponent.builder()
            .activityModule(ActivityModule(this))
            .sharedPreferenceModule(SharedPreferenceModule())
            .contextModule(ContextModule(this))
            .build()
        activityComponent.inject(this)
    }
    private fun checkPermission()  = ActivityCompat.checkSelfPermission(this,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
    override fun showProgress(status: Boolean) {
        showLoadingProgress(progressBar,status)
    }

    override fun emailObservable(): Observable<CharSequence> {
        return RxTextView.textChanges(edEmail).skipInitialValue()
    }

    override fun updateSubmitButton(state: Boolean) {
        btnSubmit.isEnabled = state
    }




    private fun openAutoCompletePlaceActivity() {
        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN,fieldList)
            .setCountry("IN")
            .build(this)
        startActivityForResult(intent,AUTOCOMPLETE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode==AUTOCOMPLETE_REQUEST_CODE)
        {
            when(resultCode)
            {
                AutocompleteActivity.RESULT_OK -> {
                    selectedPlace  = Autocomplete.getPlaceFromIntent(data!!)
                    isAutoLocatedFlag = false
                    updateLocation()
                }
                AutocompleteActivity.RESULT_ERROR -> {
                    val status = Autocomplete.getStatusFromIntent(data!!)
                    showLongToast(status.statusMessage!!,this)
                    Log.i(Constants.LOG_TAG,status.statusMessage)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun updateLocation() {
        mSelectedCity = selectedPlace.address!!
        tvCity.text = selectedPlace.address
    }
    override fun gradeListLoadSucess(gradeListModel: GradeListModel) {
        this.gradeList = gradeListModel.grade_details
        gvBoards.adapter = GradeListAdapter(this,gradeList,this)
    }

    override fun gradeListLoadFailed(msg: String) {
        showLongToast(msg,this)
    }
    override fun onSubmitDetailsSucess() {
        mySharedPreferences.putString(Constants.EMAIL,mEmail)
        var intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }

    override fun onSubmitDetailFailed(msg: String) {
        showLongToast(msg,this)
    }
}

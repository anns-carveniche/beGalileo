package com.carveniche.begalileo.ui.nearbyCenters

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.carveniche.begalileo.Constants
import com.carveniche.begalileo.R
import com.carveniche.begalileo.adapters.CustomInfoMapAdapter
import com.carveniche.begalileo.api.ApiServiceInterface
import com.carveniche.begalileo.di.component.DaggerActivityComponent
import com.carveniche.begalileo.di.module.ActivityModule
import com.carveniche.begalileo.di.module.ContextModule
import com.carveniche.begalileo.di.module.SharedPreferenceModule
import com.carveniche.begalileo.ui.base.BaseContract
import com.carveniche.begalileo.ui.nearbyCenters.fragments.FragmentMapMarker
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import android.content.Context.LAYOUT_INFLATER_SERVICE
import com.carveniche.begalileo.ui.main.MainActivity

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupWindow
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.carveniche.begalileo.models.CenterData
import com.carveniche.begalileo.models.NearCenterModel
import com.carveniche.begalileo.util.screenHeight
import com.carveniche.begalileo.util.screenWidth
import com.carveniche.begalileo.util.showLongToast
import kotlinx.android.synthetic.main.activity_nearby_center.*


class NearbyCenterActivity : AppCompatActivity(),NearbyCenterContract.View,OnMapReadyCallback,GoogleMap.OnMarkerClickListener {



    lateinit var popupWindow: PopupWindow
    lateinit var  customView : View
    lateinit var layoutInflaters : LayoutInflater
    private lateinit var map : GoogleMap
   lateinit var nearbyCenterDatas : NearCenterModel
    @Inject lateinit var presenter: NearbyCenterContract.Presenter
    lateinit var selectedcenter : CenterData
    var REQUEST_CALL_CODE = 333

     var hashMap = HashMap<Marker,CenterData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nearby_center)
        injectDependency()
        presenter.attach(this)
        initUI()
    }

    private fun initUI() {
         layoutInflaters = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
         customView = layoutInflaters.inflate(R.layout.map_marker_layout,null)
        popupWindow  = PopupWindow(customView,ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        nearbyCenterDatas = intent.getParcelableExtra(Constants.NEARBY_CENTERS)

        var mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun showMapMarkerFragment() {
        val ldf = FragmentMapMarker()
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.frame,ldf,FragmentMapMarker.TAG)
            .commit()
    }

    override fun showProgress(boolean: Boolean) {

    }
    override fun onMapReady(p0: GoogleMap?) {
        map = p0!!
        presenter.showSetupMap(nearbyCenterDatas.center_data,map)
        map.setOnMarkerClickListener(this)
        map.setOnCameraMoveStartedListener {
            popupWindow.dismiss()
        }


    }
    override fun onMarkerClick(p0: Marker?): Boolean {
        selectedcenter = hashMap[p0]!!

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(p0!!.position,18f))
        var latLng = p0.position
        var screenPosition = map.projection.toScreenLocation(latLng)
       var tvMarkerText = customView.findViewById<TextView>(R.id.tvMarkerText)
        tvMarkerText.text = selectedcenter!!.name
        customView.measure(View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED),View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED))
        var ibCall = customView.findViewById<ImageButton>(R.id.ibCall)
        ibCall.setOnClickListener {
            callCenterNumber()

        }

         popupWindow.showAtLocation(llContainer,Gravity.NO_GRAVITY, screenWidth(this)/2-(customView.measuredWidth), screenHeight(this)/2-(customView.measuredHeight))
        return true
    }

    private fun callCenterNumber() {
        var permissionCheck = ContextCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE)
        if(permissionCheck!=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE),REQUEST_CALL_CODE)
        }
        else
        {
            presenter.callMobile(selectedcenter.contact_no,this)
        }

    }

    override fun addMarker(marker: Marker, centerData: CenterData) {
        hashMap[marker] = centerData
    }

    private fun injectDependency() {
        val activityComponent = DaggerActivityComponent.builder()
            .activityModule(ActivityModule(this))
            .sharedPreferenceModule(SharedPreferenceModule())
            .contextModule(ContextModule(this))
            .build()
        activityComponent.inject(this)
    }

    override fun onPause() {
        super.onPause()
        popupWindow.dismiss()
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode)
        {
            REQUEST_CALL_CODE->{
                if(grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED)
                {
                    showLongToast("Permission required to call",this)
                }
                else if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    callCenterNumber()
                }
            }
            else->{

            }
        }
    }
}
class NearbyCenterContract
{
    interface View : BaseContract.View
    {
        fun showMapMarkerFragment()
        fun addMarker(marker : Marker,centerData: CenterData)

    }
    interface Presenter : BaseContract.Presenter<NearbyCenterContract.View>
    {
        fun showMapMarkerFragment()
        fun showSetupMap(nearbyCenters : List<CenterData>,map:GoogleMap)
        fun callMobile(number : String,context: Context)

    }
}
class NearbyCenterPresenter : NearbyCenterContract.Presenter
{

    @Inject lateinit var view : NearbyCenterContract.View
    private val apiServiceInterface: ApiServiceInterface = ApiServiceInterface.create()
    private val subscriptions  = CompositeDisposable()
    override fun subscribe() {

    }

    override fun unsubscrbe() {
        subscriptions.clear()
    }

    override fun callMobile(number: String,context:Context) {
        var callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse("tel:$number")
        context.startActivity(callIntent)
    }

    override fun showSetupMap(nearbyCenters: List<CenterData>, map: GoogleMap) {
        map.uiSettings.isZoomControlsEnabled = true

        nearbyCenters.forEach {
            val latLng = LatLng(it.latitude,it.longitude)
            var markerOptions = MarkerOptions().position(latLng).title(it.name)
            var marker = map.addMarker(markerOptions)
            view.addMarker(marker,it)
        }
        val firstLatLng : LatLng = LatLng(nearbyCenters[0].latitude,nearbyCenters[0].longitude)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(firstLatLng,14f))
    }
    override fun attach(view: NearbyCenterContract.View) {
     this.view = view
    }
    override fun showMapMarkerFragment() {
        view.showMapMarkerFragment()
    }

}




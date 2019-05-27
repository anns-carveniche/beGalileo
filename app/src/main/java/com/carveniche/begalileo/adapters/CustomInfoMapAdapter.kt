package com.carveniche.begalileo.adapters

import android.app.Activity
import android.content.Context
import android.view.View
import com.carveniche.begalileo.R
import com.carveniche.begalileo.util.showLongToast
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.map_marker_layout.view.*

class CustomInfoMapAdapter(val context: Context): GoogleMap.InfoWindowAdapter {
    override fun getInfoContents(p0: Marker?): View {
        var mInfoView = (context as Activity).layoutInflater.inflate(R.layout.map_marker_layout,null)
        var latLng = p0!!.position
        mInfoView.ibCall.setOnClickListener {
            showLongToast("Call",context)
        }

        return mInfoView
    }

    override fun getInfoWindow(p0: Marker?): View? {
       return null
    }
}
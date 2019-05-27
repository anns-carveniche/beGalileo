package com.carveniche.begalileo.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue


@Parcelize
data class NearCenterModel(

    val center_data: List<CenterData>,
    val status: Boolean
) : Parcelable

@Parcelize
data class CenterData(
    val contact_no: String,
    val latitude: Double,
    val longitude: Double,
    val name: String,
    val tutor_id: Int
) : Parcelable
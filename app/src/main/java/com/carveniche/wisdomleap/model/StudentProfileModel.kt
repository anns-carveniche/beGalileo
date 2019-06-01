package com.carveniche.wisdomleap.model

data class StudentProfileModel(
    val contact_no: String,
    val email: String,
    val first_name: String,
    val grade_id: Int,
    val grade_name: String,
    val last_name: String,
    val place: String,
    val school_name: String,
    val status: Boolean
)
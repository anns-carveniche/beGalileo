package com.carveniche.wisdomleap.model
data class RegisterModel(
    val email: String,
    val mobile: String,
    val otp: Int,
    val status: Boolean,
    val student_id: Int,
    val message  : String
)

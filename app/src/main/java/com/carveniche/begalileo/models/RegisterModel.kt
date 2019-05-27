package com.carveniche.begalileo.models
data class RegisterModel(
    val email: String,
    val mobile: String,
    val otp: Int,
    val status: Boolean,
    val parent_id: Int,
    val message  : String
)

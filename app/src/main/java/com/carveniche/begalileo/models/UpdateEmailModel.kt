package com.carveniche.begalileo.models
data class UpdateEmailModel(
    val email: String,
    val mobile: String,
    val is_otp_verified: Boolean,
    val status: Boolean
)

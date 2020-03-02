package com.carveniche.begalileo.models

data class RewardsDataModel(
    val available_coins: Int,
    val id: Int,
    val redeedmed_rewards: Int,
    val status: Boolean,
    val student_name: String,
    val today_coins: Int
)
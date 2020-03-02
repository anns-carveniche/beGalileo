package com.carveniche.begalileo.models

data class LeaderboardDataModel(
    val current_month: List<CurrentMonth>,
    val current_user_score: Int,
    val status: Boolean,
    val total: Int
)

data class CurrentMonth(
    val grade: String,
    val points: Int,
    val rank: Int,
    val student_name: String
)
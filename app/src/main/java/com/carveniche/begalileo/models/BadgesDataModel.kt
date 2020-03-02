package com.carveniche.begalileo.models

data class BadgesDataModel(
    val `data`: List<Data>,
    val status: Boolean
)

data class Data(
    val badge_detail: String,
    val badge_image: String,
    val count: Int
)
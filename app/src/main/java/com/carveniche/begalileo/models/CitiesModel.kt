package com.carveniche.begalileo.models

data class CitiesModel(
    val cities: List<City>,
    val message: String,
    val status: Boolean
)

data class City(
    val code: String,
    val id: Int,
    val name: String
)
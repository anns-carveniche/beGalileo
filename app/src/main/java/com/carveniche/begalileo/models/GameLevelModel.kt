package com.carveniche.begalileo.models

data class GameLevelModel(
    val levels: List<Level>,
    val message: String,
    val status: Boolean
)

data class Level(
    val id: Int,
    val name: String,
    val type: String,
    val level_status: Boolean,
    val trophy: Boolean
)

package com.carveniche.begalileo.models
data class GradeBoardModel(
    val goals: List<String>,
    val grade_boards: List<GradeBoard>,
    val message: String,
    val status: Boolean
)

data class GradeBoard(
    val grade_details: List<GradeDetail>,
    val id: Int,
    val name: String
)

data class GradeDetail(
    val id: Int,
    val name: String
)
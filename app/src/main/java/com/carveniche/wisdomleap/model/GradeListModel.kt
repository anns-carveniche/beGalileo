package com.carveniche.wisdomleap.model

data class GradeListModel(
    val grade_details: List<GradeDetail>,
    val message: String,
    val status: Boolean
)

data class GradeDetail(
    val id: Int,
    val name: String
)
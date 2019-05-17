package com.carveniche.wisdomleap.model

data class SubjectListModel(
    val course_details: List<CourseDetail>,
    val message: String,
    val status: Boolean
)

data class CourseDetail(
    val course_id: Int,
    val image_url: String
)
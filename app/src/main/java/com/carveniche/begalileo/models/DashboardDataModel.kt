package com.carveniche.begalileo.models

data class DashboardDataModel(
    val status: Boolean,
    val students: List<Student>
)

data class Student(
    val grade_id: Int,
    val grade_name: String,
    val student_id: Int
)
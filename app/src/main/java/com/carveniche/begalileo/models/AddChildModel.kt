package com.carveniche.begalileo.models

data class AddChildModel(
    val message: String,
    val parent_id: Int,
    val status: Boolean,
    val student_details: StudentDetails
)

data class StudentDetails(
    val board: List<Int>,
    val category: Any,
    val contact_no: Any,
    val country: Any,
    val created_at: String,
    val created_by: Any,
    val current_login: Any,
    val day: Any,
    val demo_user: Boolean,
    val dob: Any,
    val email: Any,
    val father_email: Any,
    val first_name: String,
    val from: Any,
    val gender: String,
    val goal: String,
    val grades: Any,
    val hashed_password: String,
    val id: Int,
    val image: Image,
    val is_locked: Boolean,
    val is_logged_in: Boolean,
    val is_verified: Boolean,
    val landline: Any,
    val last_logged_in: Any,
    val last_name: String,
    val location: Any,
    val marital_status: Any,
    val math_stage: Any,
    val midas: Boolean,
    val mother_email: Any,
    val occupation: Any,
    val otp: Any,
    val parent_email: Any,
    val provider: Any,
    val questions_attempted: Int,
    val questions_correct: Int,
    val questions_incorrect: Int,
    val reasoning: Boolean,
    val reference_code: Any,
    val registered_for: Any,
    val registered_from: Any,
    val registered_on: Any,
    val s_unmask: Boolean,
    val sales_person_id: Any,
    val school_city: String,
    val school_id: Any,
    val school_name: String,
    val status: String,
    val sub_role_id: Any,
    val time: Any,
    val time_spent: Int,
    val uid: Any,
    val unique_session_id: Any,
    val updated_at: String,
    val user_image: String,
    val user_name: String
)

data class Image(
    val normal: Normal,
    val thumb: Thumb,
    val url: Any
)

data class Normal(
    val url: Any
)

data class Thumb(
    val url: Any
)
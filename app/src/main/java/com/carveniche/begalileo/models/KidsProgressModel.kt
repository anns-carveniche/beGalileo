package com.carveniche.begalileo.models

data class KidsProgressModel(
    val accuracy: Int,
    val concept_name: String,
    val correct: Int,
    val status: Boolean,
    val total_question: Int
)
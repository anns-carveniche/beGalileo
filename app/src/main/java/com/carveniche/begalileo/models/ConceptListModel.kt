package com.carveniche.begalileo.models

data class ConceptListModel(
    val grade_concepts: List<GradeConcept>,
    val message: String,
    val status: Boolean
)

data class GradeConcept(
    val board: Int,
    val board_name: String,
    val grade_id: Int,
    val grade_name: String,
    val sub_concepts: List<SubConcept>
)

data class SubConcept(
    val code: String,
    val sub_concept_id: Int,
    val sub_concept_name: String
)
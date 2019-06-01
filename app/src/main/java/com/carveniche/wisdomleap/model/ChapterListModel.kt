package com.carveniche.wisdomleap.model

data class ChapterListModel(
    val chapter_concepts: List<ChapterConcept>,
    val course_id: Int,
    val message: String,
    val status: Boolean
)

data class ChapterConcept(
    val chapter_id: Int,
    val chapter_name: String,
    val questions: Boolean,
    val sub_concept_details: List<SubConceptDetail>
)

data class SubConceptDetail(
    val sub_concept_id: Int,
    val sub_concept_name: String,
    val video: Boolean,
    val video_url : String,
    val image: String
)
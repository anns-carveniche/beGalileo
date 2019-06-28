package com.carveniche.wisdomleap.model

data class ChapterVideosModel(
    val chapter_id: Int,
    val chapter_name: String,
    val status: Boolean,
    val sub_concept_details: List<SubConceptDetails>
)

data class SubConceptDetails(
    val image: String,
    val sub_concept_id: Int,
    val sub_concept_name: String,
    val video: Boolean,
    val video_url: String
)
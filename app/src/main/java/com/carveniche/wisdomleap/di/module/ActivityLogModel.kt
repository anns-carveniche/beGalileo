package com.carveniche.wisdomleap.di.module

data class ActivityLogModel(
    val message: String,
    val quiz_data: List<QuizData>,
    val search: String,
    val status: Boolean,
    val student_id: Int,
    val video_data: List<VideoData>
)

data class VideoData(
    val date: String,
    val sub_concept_id: Int,
    val sub_concept_name: String
)

data class QuizData(
    val chapter_id: Int,
    val chapter_name: String,
    val correct: Any,
    val date: String,
    val score: String,
    val total: Int
)
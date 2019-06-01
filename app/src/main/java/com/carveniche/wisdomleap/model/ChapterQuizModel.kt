package com.carveniche.wisdomleap.model

data class ChapterQuizModel(
    val current: Int,
    val quiz_data: List<QuizData>,
    val quiz_id: Int,
    val result_data: List<ResultData>,
    val status: Boolean
)

data class ResultData(
    val correct: Boolean,
    val question_id: Int,
    val question_no: Int
)

data class QuizData(
    val choices_data: List<ChoicesData>,
    val level: String,
    val question_id: Int,
    val question_image: String,
    val question_text: String,
    val type: String
)

data class ChoicesData(
    val choice_id: Int,
    val correct_answer: Boolean,
    val image: String,
    val options: String
)
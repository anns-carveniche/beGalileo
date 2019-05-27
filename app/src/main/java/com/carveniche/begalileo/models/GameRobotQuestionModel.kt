package com.carveniche.begalileo.models

data class GameRobotQuestionModel(
    val game_questions: List<GameQuestion>,
    val game_id : Int,
    val message: String,
    val status: Boolean
)

data class GameQuestion(
    val answer: String,
    val created_at: String,
    val game_level_id: Int,
    val id: Int,
    val question: String,
    val updated_at: String
)
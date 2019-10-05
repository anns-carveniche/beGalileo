package com.carveniche.begalileo.models

data class PracticeQuizQuestionModel(
    val question_data: QuestionData,
    val status: Boolean
)

data class QuestionData(
    val choiceCount: Int,
    val choiceType: String,
    val choices: List<String>,
    val cols: String,
    val operation: String,
    val questionContent: List<QuestionContent>,
    val questionName: String,
    val rows: String,
    val solution: Solution,
    val type: String
)

data class QuestionContent(
    val col: Int,
    val isMissed: String,
    val row: Int,
    val value: String
)

data class Solution(
    val model: List<Model>,
    val scols: Any,
    val sidebyside: List<Any>,
    val srows: Any
)

data class Model(
    val `val`: String
)
package com.carveniche.begalileo.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AnswerSheetModel(
    val userData : List<UserSpeedMathData>,
    val gameId : Int

) : Parcelable

@Parcelize
data class UserSpeedMathData(
    var questionId : Int,
    var question : String,
    var userAnswer : String,
    var correctAnswer : String,
    var isAnswerWright : Boolean
) : Parcelable
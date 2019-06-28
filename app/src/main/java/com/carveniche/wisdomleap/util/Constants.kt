package com.carveniche.wisdomleap.util

import android.R.drawable
import android.util.Log
import com.carveniche.wisdomleap.R
import com.carveniche.wisdomleap.api.ApiInterface

import io.reactivex.schedulers.Schedulers
import java.util.*

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId




class Constants {
    companion object {
        const val LOG_TAG = "WisdomLeap"
        const val MY_PREFS = "Prefs_Wisdom_leap"
        const val OTP = "OTP"
        const val FIRST_NAME = "FIRST_NAME"
        const val LAST_NAME = "LAST_NAME"
        const val MOBILE_NUMBER = "MOBILE_NUMBER"
        const val EMAIL = "EMAIL"
        const val STUDENT_ID = "STUDENT_ID"
        const val LOGGED_IN = "LOGGED_IN"
        const val COURSE_ID = "COURSE_ID"
        const val SUBJECT_ID = "SUBJECT_ID"
        const val QUIZ_LEVEL = "QUIZ_LEVEL"
        const val EASY = "easy"
        const val HARD = "hard"
        const val MEDIUM = "medium"
        const val QUIZ_TYPE = "multiple"
        const val QUIZ_CATEGORY = "quiz_category"
        const val CONCEPT_ID = "CONCEPT_ID"
        const val SUB_CONCEPT_ID = "SUB_CONCEPT_ID"
        const val VIDEO_URL = "VIDEO_URL"
        const val VIDEO_TITLE = "VIDEO_TITLE"
        const val QUIZ_SCORE = "QUIZ_SCORE"
        const val TIME_TAKEN = "TIME_TAKEN"
        const val TOTAL_QUIZ_QUESTION = "TOTAL_QUIZ_QUESTION"
        const val IS_FIRST_TIME_LAUNCH = "IS_FIRST_TIME_LAUNCH"
        const val LAST_DAY = "LAST_DAY"
        const val AVATAR_IMAGE_ID = "AVATAR_IMAGE_ID"
        const val OPPONENT_NAME = "OPPONENT_NAME"
        const val OPPONENT_COINS = "OPPONENT_COINS"
        const val OPPONENT_AVATAR = "OPPONENT_AVATAR"
        const val IS_PLAYER_WIN = "IS_PLAYER_WIN"
        const val WINNER = "winner"
        const val LOOSER = "loser"
        const val DRAW = "draw"
        const val USER_COINS = "USER_COINS"

        //Custom Font Path
        const val FONT_NICONNE = "fonts/Niconne-Regular.ttf"
        const val FONT_ROBOTO_LIGHT = "fonts/Roboto/Roboto-Light.ttf"


        const val ERROR_MESSAGE = "Unable to fetch data please try again"

        //Avatar Array
         val myAvatarList = intArrayOf(R.drawable.avatar_1,
                                                R.drawable.avatar_2,
                                                R.drawable.avatar_3,R.drawable.avatar_4,R.drawable.avatar_5,
                                                R.drawable.avatar_6,R.drawable.avatar_7,R.drawable.avatar_8,
                                                R.drawable.avatar_9,R.drawable.avatar_10,R.drawable.avatar_11,R.drawable.avatar_12)

        fun getAvatarList() : IntArray
        {
            return myAvatarList
        }

        fun updateDeviceInfo(studentId : Int)
        {


            FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w(Constants.LOG_TAG, "getInstanceId failed", task.exception)

                        return@OnCompleteListener
                    }

                    // Get new Instance ID token
                    val token = task.result!!.token

                    var year = Calendar.getInstance().get(Calendar.YEAR)
                    var month = Calendar.getInstance().get(Calendar.MONTH)
                    var day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                    var lastActiveDate = "$year-$month-$day"
                    ApiInterface.create().updateDeviceInfo(studentId,token,lastActiveDate)
                        .subscribeOn(Schedulers.io())
                        .subscribe {
                            Log.d(Constants.LOG_TAG,"Device Updated : $token - $it")
                        }

                })

        }
    }
}
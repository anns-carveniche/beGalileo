package com.carveniche.begalileo.util

import com.carveniche.begalileo.R
import java.lang.Exception

class Constants {
    companion object {
        const val MY_PREFS = "beGal"
        const val LOGGED_IN = "LOGGED_IN"
        const val FIRST_RUN = "FIRST_RUN"
        const val BASE_URL = "http://192.168.1.37:3000/"
        const val LOG_TAG = "Begalileo"
        const val MOBILE_NUMBER = "MOBILE_NUMBER"
        const val EMAIL = "EMAIL"
        const val OTP = "OTP"
        const val UNKNOWN_ERROR = "Something went wrong please try again"
        const val STUDENT_ID = "STUDENT_ID"
        const val PARENT_ID = "PARENT_ID"
        const val GRADE_ID = "GRADE_ID"
        const val BOARD_ID = "BOARD_ID"
        const val ADD_CHILD = "Add Child"
        const val FIND_CENTER = "Find Center"
        const val CURRICULAM = "Curriculum"
        const val MY_PROFILE = "My Profile"
        const val LIVE_VIDEO = "Live Class"
        const val KID_PROGRESS = "Kid Progress"
        const val NEARBY_CENTERS = "NEARBY_CENTERS"
        const val PLAYERS = "PLAYERS"
        const val LEVEL_ID = "LEVEL_ID"
        const val COMPUTER = "computer"
        const val QuestionAnswerLog = "QuestionAnswerLog"
        const val COMPUTER_SCORE = "COMPUTER_SCORE"
        const val PRACTICE_MATH = "Speed Math"
        const val BADGES = "Badges"
        const val REWARDS = "Rewards"
        const val LEADER_BOARD = "LeaderBoard"
        const val ADD = "ADD"
        const val ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android"
        const val SUBTRACT = "SUBTRACT"
        const val MULTIPLY = "MULTIPLY"
        const val DIVIDE = "DIVIDE"
        const val FONT_ROBOTO = "fonts/Roboto/Roboto-Black.ttf"
        const val FONT_MONTSERRAT = "fonts/Montserrat/Montserrat-Medium.ttf"
        const val REWARD_TYPE = "REWARD_TYPE"

        const val  QUESTION_TYPE_SELECT_CHOICE = "selectchoice"
        const val  QUESTION_TYPE_DRAG_DROP = "dragdrop"
        const val  QUESTION_TYPE_KEYING = "keying"
        const val  QUESTION_SUB_TYPE_RANDOM_DRAG_DROP = "randomarrangementdragdrop"

        const val ExceptionMessage = "Something Went wrong please try again later"
        const val NO_DATA_FOUND = "No Data Found"

//        var HOME_ITEM_NAME_LIST =  listOf<String>(
//            ADD_CHILD,
//            FIND_CENTER,
//            CURRICULAM,
//            PRACTICE_MATH,
//            LIVE_VIDEO
//        )
//        var HOME_ITEM_IMAGE_LIST = listOf<Int>(
//            R.drawable.ic_add_child,
//            R.drawable.ic_location_pin,
//            R.drawable.ic_curriculum,
//            R.drawable.ic_practice_math,
//            R.drawable.ic_curriculum
//        )

        var HOME_ITEM_NAME_LIST =  listOf<String>(

            KID_PROGRESS,
            PRACTICE_MATH,
            REWARDS,
            BADGES,
            LEADER_BOARD,
            LIVE_VIDEO

        )
        var HOME_ITEM_IMAGE_LIST = listOf<Int>(
            R.drawable.ic_report,
            R.drawable.ic_practice_math,
            R.drawable.ic_rewards,
            R.drawable.ic_badges,
            R.drawable.ic_leaderboard,
            R.drawable.ic_curriculum

        )


    }
}
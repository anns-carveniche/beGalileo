package com.carveniche.begalileo.util

import com.carveniche.begalileo.R

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
        const val PARENT_ID = "PARENT_ID"
        const val GRADE_ID = "GRADE_ID"
        const val BOARD_ID = "BOARD_ID"
        const val ADD_CHILD = "Add Child"
        const val FIND_CENTER = "Find Center"
        const val CURRICULAM = "Curriculum"
        const val MY_PROFILE = "My Profile"
        const val NEARBY_CENTERS = "NEARBY_CENTERS"
        const val PLAYERS = "PLAYERS"
        const val LEVEL_ID = "LEVEL_ID"
        const val COMPUTER = "computer"
        const val QuestionAnswerLog = "QuestionAnswerLog"
        const val COMPUTER_SCORE = "COMPUTER_SCORE"
        const val PRACTICE_MATH = "Practice Math"

        var HOME_ITEM_NAME_LIST =  listOf<String>(
            ADD_CHILD,
            FIND_CENTER,
            CURRICULAM,
            PRACTICE_MATH,
            MY_PROFILE
        )
        var HOME_ITEM_IMAGE_LIST = listOf<Int>(
            R.drawable.ic_add_child,
            R.drawable.ic_location_pin,
            R.drawable.ic_curriculum,
            R.drawable.ic_practice_math,
            R.drawable.ic_custom_person
        )


    }
}
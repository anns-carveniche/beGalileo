package com.carveniche.wisdomleap.contract

import android.view.View
import android.widget.TextView
import com.carveniche.wisdomleap.base.BaseContract
import com.carveniche.wisdomleap.model.QuizQuestionModel

class MultiplayerPlayQuizContract {
    interface View : BaseContract.View
    {
        fun questionLoadSuccess(quizQuestionModel: QuizQuestionModel)
        fun questionLoadFailed(msg : String)
        fun showAnswerStatus(tvOption: android.view.View,isCorrect : Boolean)

    }
    interface Presenter : BaseContract.Presenter<MultiplayerPlayQuizContract.View>
    {
        fun loadQuizQuestions(amount : Int,category : Int,difficulty : String,type : String)
        fun verifyAnswer(tvOption : android.view.View,userAnswer : String,correctAnswer : String)
        fun saveMultiPlayerQuiz(studentId : Int,
                                category: Int,
                                level : String,
                                total : Int,
                                correct : Int,
                                playedWith : String,
                                winningStatus : String)

    }
}
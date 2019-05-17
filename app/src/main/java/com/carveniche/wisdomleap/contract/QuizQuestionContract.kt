package com.carveniche.wisdomleap.contract

import com.carveniche.wisdomleap.base.BaseContract
import com.carveniche.wisdomleap.model.QuizQuestionModel

class QuizQuestionContract {
    interface View : BaseContract.View{
        fun showAnswerStatus(isCorrect : Boolean)
        fun updateQuestion()
        fun loadQuestionSucess(quizQuestionModel: QuizQuestionModel)
        fun loadFailed(msg : String)
    }
    interface Presenter : BaseContract.Presenter<QuizQuestionContract.View>{
        fun validateAnswer()
        fun showNextQuestion()
        fun loadQuizQuestions(amount : Int,category : Int,difficulty : String,type : String)
    }
}
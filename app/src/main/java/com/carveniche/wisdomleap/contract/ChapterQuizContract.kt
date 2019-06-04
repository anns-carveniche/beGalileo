package com.carveniche.wisdomleap.contract

import com.carveniche.wisdomleap.base.BaseContract
import com.carveniche.wisdomleap.model.ChapterQuizModel
import com.carveniche.wisdomleap.model.QuizData


class ChapterQuizContract {
    interface View : BaseContract.View{
        fun onQuestionLoadSucess(chapterQuizModel: ChapterQuizModel)
        fun onQuestionLoadFailed(msg : String)
        fun updateQuestion(quizData: QuizData)
        fun submitAnswer()
    }
    interface Presenter : BaseContract.Presenter<ChapterQuizContract.View>{
        fun loadQuestionDatas(studentId : Int,courseId : Int,chapterId : Int)
        fun saveQuiz(studentId: Int,quizId : Int,questionId : Int,questionIndex : Int,choiceId : Int,timeSpent : Int,isCorrect : Boolean,isCompleted : Boolean)
    }
}
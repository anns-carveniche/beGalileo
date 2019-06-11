package com.carveniche.wisdomleap.contract

import com.carveniche.wisdomleap.base.BaseContract

class QuizHomeContract {
    interface View : BaseContract.View{
        fun onCategorySelected(categoryId : Int)
        fun openQuizQuestionActivity(level : String)
        fun openMultiPlayerQuiz()
    }
    interface Presenter : BaseContract.Presenter<QuizHomeContract.View>
    {

    }
}
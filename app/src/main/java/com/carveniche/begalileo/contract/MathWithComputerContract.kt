package com.carveniche.begalileo.contract

import android.content.Context
import com.carveniche.begalileo.models.GameQuestion
import com.carveniche.begalileo.models.GameRobotQuestionModel

class MathWithComputerContract  {

    interface View : BaseContract.View
    {
        fun updateTime()
        fun updateQuestion(question : String,answer : String)
        fun loadDataSuccess(gameRobotQuestionModel: GameRobotQuestionModel)
        fun loadDataFailed(msg : String)
    }
    interface Presenter : BaseContract.Presenter<View>
    {
        fun startGameTimer(state: Boolean)
        fun startGameQuestions(gameQuestion: GameQuestion)
        fun nextGameQuestion(gameQuestion: GameQuestion)
        fun loadGameQuestions(context: Context,levelId: Int,players : ArrayList<Int>)
    }
}
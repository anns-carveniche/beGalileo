package com.carveniche.begalileo.ui.speedMath.fragments.mathWithComputer

import android.content.Context
import com.carveniche.begalileo.models.GameQuestion
import com.carveniche.begalileo.models.GameRobotQuestionModel
import com.carveniche.begalileo.ui.base.BaseContract

class MathWithComputerContract  {

    interface View : BaseContract.View
    {
        fun updateTime()
        fun updateQuestion(question : String,answer : String)
        fun loadDataSuccess(gameRobotQuestionModel: GameRobotQuestionModel)
        fun loadDataFailed(msg : String)
    }
    interface Presenter : BaseContract.Presenter<MathWithComputerContract.View>
    {
        fun startGameTimer(state: Boolean)
        fun startGameQuestions(gameQuestion: GameQuestion)
        fun nextGameQuestion(gameQuestion: GameQuestion)
        fun loadGameQuestions(context: Context,levelId: Int,players : ArrayList<Int>)
    }
}
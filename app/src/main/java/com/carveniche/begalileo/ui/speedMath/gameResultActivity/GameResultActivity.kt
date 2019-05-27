package com.carveniche.begalileo.ui.speedMath.gameResultActivity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.carveniche.begalileo.Constants
import com.carveniche.begalileo.R
import com.carveniche.begalileo.adapters.AnswerSummaryAdapter
import com.carveniche.begalileo.di.component.DaggerActivityComponent
import com.carveniche.begalileo.di.module.ActivityModule
import com.carveniche.begalileo.di.module.ContextModule
import com.carveniche.begalileo.models.AnswerSheetModel
import com.carveniche.begalileo.models.UserSpeedMathData
import com.carveniche.begalileo.ui.speedMath.SpeedMathActivity
import kotlinx.android.synthetic.main.activity_game_result.*
import kotlinx.android.synthetic.main.dialog_layout_answer_summary.view.*
import javax.inject.Inject

class GameResultActivity : AppCompatActivity(), GameResultContractor.View {

    @Inject
    lateinit var presenter: GameResultContractor.Presenter
    lateinit var listData: List<UserSpeedMathData>
    var userCorrectAnswer = 0
    var userWrongAnswer = 0
    var robotCorrectAnswer = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_result)
        injectDependency()
        presenter.attach(this)
        initUI()
    }


    private fun initUI() {

        var data = intent.getParcelableExtra<AnswerSheetModel>(Constants.QuestionAnswerLog)
        robotCorrectAnswer = intent.getIntExtra(Constants.COMPUTER_SCORE, 0)
        listData = data.userData
        Log.d(Constants.LOG_TAG, "Game Id  : ${data.gameId}")
        btnViewAnswer.setOnClickListener {
            showAnswerSummary()
        }
        listData.forEach {
            if (it.isAnswerWright)
                userCorrectAnswer++
            else
                userWrongAnswer++
        }
        tvUserCorrect.text = "Correct : $userCorrectAnswer"
        tvUserWrong.text = "Wrong : $userWrongAnswer"
        tvComputerMarks.text = "Correct : $robotCorrectAnswer"
        when {
            userCorrectAnswer > robotCorrectAnswer -> tvGameResult.text = "You won"
            userCorrectAnswer < robotCorrectAnswer -> tvGameResult.text = "You Loose"
            else -> tvGameResult.text = "Draw"
        }
    }

    override fun showProgress(boolean: Boolean) {

    }

    private fun injectDependency() {
        val activityComponent = DaggerActivityComponent.builder()
            .activityModule(ActivityModule(this))
            .contextModule(ContextModule(this))
            .build()
        activityComponent.inject(this)
    }

    override fun showAnswerSummary() {
        try {
            var answerScreenDialog = AnswerSummaryDialog()
            answerScreenDialog.listUserData = listData
            var fragmentTransaction = supportFragmentManager.beginTransaction()
            answerScreenDialog.show(fragmentTransaction, AnswerSummaryDialog.TAG)
        } catch (ae: Exception) {
            Log.d(Constants.LOG_TAG, ae.message)
        }

    }

    override fun onBackPressed() {
        var intent = Intent(this, SpeedMathActivity::class.java)
        startActivity(intent)
    }

}

class AnswerSummaryDialog : DialogFragment() {

    var listUserData = listOf<UserSpeedMathData>()
    lateinit var rootView: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        rootView = inflater.inflate(R.layout.dialog_layout_answer_summary, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(Constants.LOG_TAG, "Data Start")
        Log.d(Constants.LOG_TAG, listUserData.toString())
        rootView.listView.adapter = AnswerSummaryAdapter(context!!, listUserData)
    }

    override fun onStart() {
        super.onStart()
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.MATCH_PARENT
        dialog.window.setLayout(width, height)
    }

    companion object {
        const val TAG = "AnswerSummaryDialog"
    }

}
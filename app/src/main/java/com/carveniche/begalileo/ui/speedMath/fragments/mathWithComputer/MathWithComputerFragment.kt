package com.carveniche.begalileo.ui.speedMath.fragments.mathWithComputer


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.carveniche.begalileo.Constants
import com.carveniche.begalileo.R
import com.carveniche.begalileo.di.component.DaggerFragmentComponent
import com.carveniche.begalileo.di.module.ContextModule
import com.carveniche.begalileo.di.module.Fragmentmodule
import com.carveniche.begalileo.models.*
import com.carveniche.begalileo.ui.speedMath.SpeedMathContract
import com.carveniche.begalileo.ui.speedMath.fragments.ComputerPlayer
import com.carveniche.begalileo.util.showLongToast
import kotlinx.android.synthetic.main.fragment_math_with_computer.*
import kotlinx.android.synthetic.main.layout_progressbar.*
import kotlinx.android.synthetic.main.speedmath_number_layout.*
import org.jsoup.Jsoup
import javax.inject.Inject

class MathWithComputerFragment : Fragment(), MathWithComputerContract.View, View.OnClickListener {


    @Inject
    lateinit var presenter: MathWithComputerContract.Presenter
    lateinit var speedMathView: SpeedMathContract.View
    private lateinit var rootView: View
    private var countTimer = 60
    private var userMarks = 0
    private var computerMarks = 0
    private var correctAnswer = ""
    @Inject
    lateinit var viewModelProvider: PlayerGameStatusModel
    lateinit var computerPlayer: ComputerPlayer
    lateinit var listPlayer: ArrayList<Int>
    lateinit var listQuestion: List<GameQuestion>
    var levelId: Int = 0
    var userQuestionNumber = 0
    var gameId = 0
    var listQuestionAnswer = mutableListOf<UserSpeedMathData>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_math_with_computer, container, false)
        return rootView
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
        speedMathView = activity as SpeedMathContract.View
    }

    private fun injectDependency() {
        val fragmentComponent = DaggerFragmentComponent.builder()
            .fragmentmodule(Fragmentmodule())
            .contextModule(ContextModule(context!!))
            .build()
        fragmentComponent.inject(this)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attach(this)
        initUI()
        viewModelProvider = ViewModelProviders.of(this).get(PlayerGameStatusModel::class.java)
        viewModelProvider.getGameScore().observe(this, Observer {
            updateComputerScore(it)
        })
        computerPlayer = ComputerPlayer(viewModelProvider)
        listPlayer = arguments!!.getIntegerArrayList(Constants.PLAYERS)
        levelId = arguments!!.getInt(Constants.LEVEL_ID)

        presenter.loadGameQuestions(context!!, levelId, listPlayer)
        Log.d(Constants.LOG_TAG, "${listPlayer.toArray()} - $levelId")
    }

    private fun updateComputerScore(score: Int) {
        this.computerMarks = score
        tvComputerStatus.text = getString(R.string.ComputerScore, score)
    }


    private fun initUI() {
        tvNumOne.setOnClickListener(this)
        tvNumTwo.setOnClickListener(this)
        tvNumThree.setOnClickListener(this)
        tvNumFour.setOnClickListener(this)
        tvNumFive.setOnClickListener(this)
        tvNumSix.setOnClickListener(this)
        tvNumSeven.setOnClickListener(this)
        tvNumEight.setOnClickListener(this)
        tvNumNine.setOnClickListener(this)
        tvNumZero.setOnClickListener(this)
        ivDel.setOnClickListener(this)

        ibGo.setOnClickListener {
            val answer = edAnswer.text.toString()
            if(!answer.isEmpty())
                checkAnswer(answer)
        }

    }

    private fun checkAnswer(answer : String)
    {
        val isAnswerRight: Boolean = if (answer == correctAnswer) {
            userMarks++
            true
        } else {
            false
        }

        val answerSheetModel = UserSpeedMathData(
            listQuestion[userQuestionNumber - 1].id,
            listQuestion[userQuestionNumber - 1].question,
            answer,
            listQuestion[userQuestionNumber - 1].answer,
            isAnswerRight
        )
        listQuestionAnswer.add(answerSheetModel)
        Log.d(Constants.LOG_TAG, answerSheetModel.toString())
        tvUserStatus.text = getString(R.string.YourScore, userMarks)
        presenter.startGameQuestions(listQuestion[userQuestionNumber])
    }

    override fun loadDataSuccess(gameRobotQuestionModel: GameRobotQuestionModel) {
        this.listQuestion = gameRobotQuestionModel.game_questions
        this.gameId = gameRobotQuestionModel.game_id
        presenter.subscribe()
        presenter.startGameQuestions(listQuestion[userQuestionNumber])
    }

    override fun loadDataFailed(msg: String) {
        speedMathView.showGameLevelFragment()
        showLongToast(msg, context!!)
    }

    private fun displayNumberValue(value: String) {
        val answerValue = edAnswer.text.toString()
        edAnswer.setText(answerValue.plus(value))
    }

    private fun deleteLastNumber() {
        val length = edAnswer.length()
        if (length > 0) {
            edAnswer.text.delete(length - 1, length)
        }
    }

    override fun onClick(v: View?) {

        when (v!!.id) {
            tvNumOne.id -> displayNumberValue(getString(R.string.num1))
            tvNumTwo.id -> displayNumberValue(getString(R.string.num2))
            tvNumThree.id -> displayNumberValue(getString(R.string.num3))
            tvNumFour.id -> displayNumberValue(getString(R.string.num4))
            tvNumFive.id -> displayNumberValue(getString(R.string.num5))
            tvNumSix.id -> displayNumberValue(getString(R.string.num6))
            tvNumSeven.id -> displayNumberValue(getString(R.string.num7))
            tvNumEight.id -> displayNumberValue(getString(R.string.num8))
            tvNumNine.id -> displayNumberValue(getString(R.string.num9))
            tvNumZero.id -> displayNumberValue(getString(R.string.num0))
            ivDel.id -> deleteLastNumber()
        }

    }

    override fun showProgress(boolean: Boolean) {
        progressBar.bringToFront()
        if (boolean) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    companion object {
        var TAG = "MathWithComputerFragment"
    }

    override fun updateTime() {
        tvTimer.text = countTimer.toString()
        countTimer--
        if (countTimer < 0) {
            presenter.unsubscrbe()
            ibGo.isEnabled = false
            computerPlayer.stopGame()
            speedMathView.showGameResultActivity(AnswerSheetModel(listQuestionAnswer, gameId), computerMarks)
            showLongToast("Time Over", context!!)
        }
    }

    override fun updateQuestion(question: String, answer: String) {
        userQuestionNumber++
        tvQuestion.text = Jsoup.parse(question).text()
        edAnswer.text.clear()
        correctAnswer = answer
    }

    override fun onPause() {
        computerPlayer.stopGame()
        presenter.unsubscrbe()
        super.onPause()
    }

    override fun onDetach() {
        presenter.unsubscrbe()
        super.onDetach()
    }


}

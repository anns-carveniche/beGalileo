package com.carveniche.wisdomleap.view.fragment


import android.os.Bundle
import android.provider.SyncStateContract

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager

import com.carveniche.wisdomleap.R
import com.carveniche.wisdomleap.adapter.QuizResultAdapter
import com.carveniche.wisdomleap.contract.QuizResultContract
import com.carveniche.wisdomleap.di.component.DaggerFragmentComponent
import com.carveniche.wisdomleap.di.module.ContextModule
import com.carveniche.wisdomleap.di.module.FragmentModule
import com.carveniche.wisdomleap.di.module.SharedPreferenceModule
import com.carveniche.wisdomleap.model.QuizResultModel
import com.carveniche.wisdomleap.util.Constants
import com.github.mikephil.charting.charts.HorizontalBarChart
import kotlinx.android.synthetic.main.fragment_quiz_result.*
import javax.inject.Inject
import com.github.mikephil.charting.utils.ColorTemplate
import android.R.id
import android.content.Intent
import android.graphics.Color
import android.util.Log
import com.carveniche.wisdomleap.model.MySharedPreferences
import com.carveniche.wisdomleap.util.ConfigChart
import com.carveniche.wisdomleap.util.XAxisValueFormatter
import com.carveniche.wisdomleap.view.activity.MainActivity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.PercentFormatter


class QuizResultFragment : Fragment(),QuizResultContract.View {


    @Inject lateinit var presenter : QuizResultContract.Presenter
    private lateinit var rootView: View
    private  var quizResultList = mutableListOf<QuizResultModel>()
    private var score = 0
    private var averageScore = 72
    private var totalQuestions  =15
    private var categoryId = 0
    private lateinit var level  : String
    private var userPercent = 0
    var configChart = ConfigChart()
    private lateinit var accuracyChart : PieChart
    @Inject lateinit var mySharedPreferences: MySharedPreferences
    private var studentId = 0
    private var timeTaken = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_quiz_result, container, false)
        return rootView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
        score = arguments!!.getInt(Constants.QUIZ_SCORE)
        categoryId = arguments!!.getInt(Constants.QUIZ_CATEGORY)
        level = arguments!!.getString(Constants.QUIZ_LEVEL)!!
        timeTaken = arguments!!.getInt(Constants.TIME_TAKEN)
        studentId = mySharedPreferences.getIntData(Constants.STUDENT_ID)
    }

    private fun initUI() {
        quizResultList.add(QuizResultModel(1,"Amit Saurah",15))
        quizResultList.add(QuizResultModel(2,"Riya Singhal",15))
        quizResultList.add(QuizResultModel(3,"Mahmood Sheth",13))
        quizResultList.add(QuizResultModel(4,"Gauransh Gaba",12))
        quizResultList.add(QuizResultModel(5,"Shanti Badal",12))

        rvQuizResult.layoutManager = LinearLayoutManager(context)
        rvQuizResult.adapter = QuizResultAdapter(context!!,quizResultList)

        userPercent = Math.abs(score*100/totalQuestions)


        tvPoints.text = "$score  of $totalQuestions ($userPercent%)"
        btnHome.setOnClickListener {
            var intent = Intent(context,MainActivity::class.java)
            startActivity(intent)
        }

        ll_players_container.visibility = View.GONE
    }

    private fun injectDependency() {
        val fragmentComponent = DaggerFragmentComponent.builder()
            .fragmentModule(FragmentModule())
            .sharedPreferenceModule(SharedPreferenceModule())
            .contextModule(ContextModule(context!!))
            .build()
        fragmentComponent.inject(this)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attach(this)
        presenter.subscribe()
        presenter.submitQuizResult(studentId,categoryId,level,totalQuestions,score,timeTaken)
        initUI()
        setPieChart()
    }

    private fun setPieChart() {
        accuracyChart  = accuracy_chart
        configChart.setPieChart(accuracyChart)
        accuracyChart.setHoleColor(resources.getColor(R.color.primaryDarkColor))
        setAccuracyData()
    }

    private fun setAccuracyData()
    {
        var entries  = ArrayList<PieEntry>()
        entries.add(PieEntry(userPercent.toFloat(), "Accuracy"))
        entries.add(PieEntry((100-userPercent).toFloat(), ""))

        var dataSet  = PieDataSet(entries,"Quiz Result")
        dataSet.setDrawIcons(false)
        dataSet.sliceSpace = 3f
        dataSet.selectionShift = 5f
        var colors = mutableListOf<Int>()
        colors.add(Color.GREEN)
        colors.add(Color.RED)
        dataSet.colors = colors

        var data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.WHITE)
        accuracyChart.data = data
        accuracyChart.highlightValues(null)
        accuracyChart.invalidate()
    }

    override fun showProgress(show: Boolean) {

    }
    companion object {
        const val TAG = "QuizResultFragment"
    }

    override fun onPause() {
        super.onPause()
        presenter.unSubscribe()
    }
}

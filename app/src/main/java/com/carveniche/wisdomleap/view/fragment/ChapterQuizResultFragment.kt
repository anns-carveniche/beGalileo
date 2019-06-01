package com.carveniche.wisdomleap.view.fragment


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat

import com.carveniche.wisdomleap.R
import com.carveniche.wisdomleap.contract.ChapterQuizResultContract
import com.carveniche.wisdomleap.di.component.DaggerFragmentComponent
import com.carveniche.wisdomleap.di.module.ContextModule
import com.carveniche.wisdomleap.di.module.FragmentModule
import com.carveniche.wisdomleap.di.module.SharedPreferenceModule
import com.carveniche.wisdomleap.util.Constants
import com.carveniche.wisdomleap.util.XAxisValueFormatter
import com.carveniche.wisdomleap.view.activity.MainActivity
import com.carveniche.wisdomleap.view.activity.SubjectActivity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import kotlinx.android.synthetic.main.fragment_chapter_quiz_result.*
import javax.inject.Inject


class ChapterQuizResultFragment : Fragment(),ChapterQuizResultContract.View {

    @Inject lateinit var presenter : ChapterQuizResultContract.Presenter
    private lateinit var rootView : View
    private lateinit var scoreChart : BarChart
    private var score = 0
    private var totalQuestions  = 0
    private var userPercent = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_chapter_quiz_result, container, false)
        return rootView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
        score = arguments!!.getInt(Constants.QUIZ_SCORE)
        totalQuestions = arguments!!.getInt(Constants.TOTAL_QUIZ_QUESTION)
        userPercent = Math.abs(score*100/totalQuestions)
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
        initUI()
        scoreChart()
    }

    private fun initUI() {
        tvTotalQuestion.text = totalQuestions.toString()
        tvCorrectAnswer.text = score.toString()
        tvIncorrectAnswer.text = (totalQuestions-score).toString()
        btnHome.setOnClickListener {
            var intent  = Intent(context,MainActivity::class.java)
            startActivity(intent)
        }
    }


    companion object {
        const val TAG = "ChapterQuizResultFragment"
    }
    override fun showProgress(show: Boolean) {

    }
    private fun scoreChart()
    {
       scoreChart = bcAvarageChart
        scoreChart.setDrawBarShadow(false)
        val description =  Description()
        description.text = ""
        scoreChart.description = description
        scoreChart.legend.isEnabled = false
        scoreChart.setPinchZoom(false)
        scoreChart.isDoubleTapToZoomEnabled = false
        scoreChart.setDrawValueAboveBar(false)

        val xAxis = scoreChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.position = XAxis.XAxisPosition.BOTTOM

        xAxis.isEnabled  = true
        xAxis.setDrawAxisLine(false)

        val yLeft = scoreChart.axisLeft

        yLeft.axisMaximum  = 100f
        yLeft.axisMinimum = 0f
        yLeft.isEnabled = false


        xAxis.labelCount = 2
        xAxis.textSize = 10f
        xAxis.textColor = Color.WHITE


        val values = arrayOf("Your Score","Average Score")
        xAxis.valueFormatter = XAxisValueFormatter(values)

        val yRight = scoreChart.axisRight
        yRight.setDrawAxisLine(true)
        yRight.setDrawGridLines(false)
        yRight.isEnabled  = false

        setGraphData()

        scoreChart.animateY(2000)
    }

    private fun setGraphData() {
        var entries = ArrayList<BarEntry>()
        entries.add(BarEntry(0f,userPercent.toFloat()))
        scoreChart.setDrawBarShadow(true)
        val barDataSet = BarDataSet(entries, "Bar Data Set")

        barDataSet.setColors(
            Color.CYAN
        )
        barDataSet.valueTextColor = Color.BLACK
        barDataSet.valueTextSize = 15f

        barDataSet.barShadowColor = ContextCompat.getColor(context!!,R.color.primaryDarkColor)

        val data = BarData(barDataSet)

        data.barWidth = 0.5f

        scoreChart.data = data
        scoreChart.invalidate()

    }
}

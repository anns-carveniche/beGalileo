package com.carveniche.wisdomleap.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.carveniche.wisdomleap.R
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieEntry
import kotlinx.android.synthetic.main.activity_chat_support.*
import android.graphics.Color
import com.carveniche.wisdomleap.util.ConfigChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.formatter.PercentFormatter


class ChatSupportActivity : AppCompatActivity() {

    private lateinit var accuracyPieChart : PieChart
    private lateinit var timeTakenPieChart : PieChart
    private lateinit var questionAttemptedPieChart : PieChart
    private lateinit var speedPieChart : PieChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_support)
        accuracyPieChart = accuracy_chart
        timeTakenPieChart = time_taken_chart
        questionAttemptedPieChart = attempted_question_chart
        speedPieChart = speed_chart
        ConfigChart().setPieChart(accuracyPieChart)
        ConfigChart().setPieChart(timeTakenPieChart)
        ConfigChart().setPieChart(questionAttemptedPieChart)
        ConfigChart().setPieChart(speedPieChart)
        setAccuracyData()
        setSpeedData()
        setAttemptedQuestionData()
        setTimeTakenData()
    }

    private fun setAccuracyData()
    {
         var entries  = ArrayList<PieEntry>()
         entries.add(PieEntry(60f, "Accuracy"))
         entries.add(PieEntry(40f, ""))

        var dataSet  = PieDataSet(entries,"Quiz Result")
        dataSet.setDrawIcons(false)
        dataSet.sliceSpace = 3f
        dataSet.selectionShift = 5f
        var colors = mutableListOf<Int>()
        colors.add(Color.RED)
        colors.add(Color.WHITE)
        dataSet.colors = colors

        var data =PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.WHITE)
        accuracyPieChart.data = data
        accuracyPieChart.highlightValues(null)
        accuracyPieChart.invalidate()
    }
    private fun setSpeedData()
    {
        var entries  = ArrayList<PieEntry>()
        entries.add(PieEntry(30f, "Speed"))
        entries.add(PieEntry(70f, ""))

        var dataSet  = PieDataSet(entries,"Quiz Result")
        dataSet.setDrawIcons(false)
        dataSet.sliceSpace = 3f
        dataSet.selectionShift = 5f
        var colors = mutableListOf<Int>()
        colors.add(Color.GREEN)
        colors.add(Color.WHITE)
        dataSet.colors = colors

        var data =PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.WHITE)
        speedPieChart.data = data
        speedPieChart.highlightValues(null)
        speedPieChart.invalidate()
    }
    private fun setAttemptedQuestionData()
    {
        var entries  = ArrayList<PieEntry>()
        entries.add(PieEntry(15f, "Questions"))
        entries.add(PieEntry(85f, ""))

        var dataSet  = PieDataSet(entries,"Quiz Result")
        dataSet.setDrawIcons(false)
        dataSet.sliceSpace = 3f
        dataSet.selectionShift = 5f
        var colors = mutableListOf<Int>()
        colors.add(Color.BLUE)
        colors.add(Color.WHITE)
        dataSet.colors = colors

        var data =PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.WHITE)
        questionAttemptedPieChart.data = data
        questionAttemptedPieChart.highlightValues(null)
        questionAttemptedPieChart.invalidate()
    }
    private fun setTimeTakenData()
    {
        var entries  = ArrayList<PieEntry>()
        entries.add(PieEntry(50f, "Time Taken"))
        entries.add(PieEntry(50f, ""))

        var dataSet  = PieDataSet(entries,"Quiz Result")
        dataSet.setDrawIcons(false)
        dataSet.sliceSpace = 3f
        dataSet.selectionShift = 5f
        var colors = mutableListOf<Int>()
        colors.add(Color.CYAN)
        colors.add(Color.WHITE)
        dataSet.colors = colors

        var data =PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.WHITE)
        timeTakenPieChart.data = data
        timeTakenPieChart.highlightValues(null)
        timeTakenPieChart.invalidate()
    }
}

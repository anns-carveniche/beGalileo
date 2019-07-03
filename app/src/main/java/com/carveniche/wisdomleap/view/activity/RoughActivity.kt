package com.carveniche.wisdomleap.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.carveniche.wisdomleap.R
import android.R.id
import android.graphics.Color
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.Entry
import kotlinx.android.synthetic.main.activity_rough.*
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.formatter.DefaultValueFormatter
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet
import com.github.mikephil.charting.utils.ColorTemplate


class RoughActivity : AppCompatActivity() {

    private lateinit var mPieChart: PieChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rough)
      initUI()
    }

    private fun initUI() {
        mPieChart = piechart
        mPieChart.setUsePercentValues(true)
        val yvalues = mutableListOf<PieEntry>()
        yvalues.add(PieEntry(5f, "Incorrect"))
        yvalues.add(PieEntry(5f, "Correct"))
        yvalues.add(PieEntry(5f, "Skipped"))


        val pieDataSet = PieDataSet(yvalues, "Quiz Results")
        pieDataSet.sliceSpace = 2f;
        pieDataSet.valueTextSize = 15f;
        pieDataSet.selectionShift = 10f;
        pieDataSet.valueLinePart1OffsetPercentage = 80f;
        pieDataSet.valueLinePart1Length = 1f;
        pieDataSet.valueLinePart2Length = 0.9f;

        val colors = ArrayList<Int>()
        colors.add(Color.RED)
        colors.add(Color.GREEN)
        colors.add(Color.YELLOW)
        pieDataSet.colors = colors
        var pieData = PieData(pieDataSet)

       // pieData.dataSet = dataSet
        pieData.setValueFormatter(DefaultValueFormatter(0))

        mPieChart.data = pieData
        mPieChart.holeRadius = 0.0f
        mPieChart.isDrawHoleEnabled = false



    }
}


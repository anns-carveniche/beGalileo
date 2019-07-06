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

    }


}


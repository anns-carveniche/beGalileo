package com.carveniche.wisdomleap.view.activity

import android.animation.Animator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.carveniche.wisdomleap.R
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieEntry
import kotlinx.android.synthetic.main.activity_chat_support.*
import android.graphics.Color
import android.view.View
import com.carveniche.wisdomleap.util.ConfigChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.formatter.PercentFormatter


class ChatSupportActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_support)

    }

}

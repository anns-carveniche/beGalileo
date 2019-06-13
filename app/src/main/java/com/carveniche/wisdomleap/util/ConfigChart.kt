package com.carveniche.wisdomleap.util

import android.graphics.Color
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend

class ConfigChart {

        fun setPieChart(chart: PieChart) {
            chart.setUsePercentValues(true)
            chart.setUsePercentValues(true);
            chart.getDescription().setEnabled(false);
            chart.setExtraOffsets(5f, 10f, 5f, 5f);

            chart.setDragDecelerationFrictionCoef(0.95f);

          //  chart.setCenterTextTypeface(tfLight)
          // chart.setCenterText("Result");

            chart.setDrawHoleEnabled(true);
            chart.setHoleColor(Color.WHITE);

            chart.setTransparentCircleColor(Color.WHITE);
            chart.setTransparentCircleAlpha(0);

            chart.setHoleRadius(30f);
            chart.setTransparentCircleRadius(0f);

            chart.setDrawCenterText(true);

            chart.setRotationAngle(0f);
            // enable rotation of the chart by touch
            chart.setRotationEnabled(true);
            chart.setHighlightPerTapEnabled(true);

            // chart.setUnit(" €");
            // chart.setDrawUnitsInChart(true);

            // add a selection listener
            //chart.setOnChartValueSelectedListener(this);


            chart.animateY(1400, Easing.EasingOption.EaseInCubic);
            // chart.spin(2000, 0, 360);

            val l = chart.legend
            l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
            l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
            l.orientation = Legend.LegendOrientation.VERTICAL
            l.setDrawInside(false)
            l.xEntrySpace = 7f
            l.yEntrySpace = 0f
            l.yOffset = 0f
            l.isEnabled = false
            chart.setEntryLabelColor(Color.WHITE)
         //   chart.setEntryLabelTypeface(tfRegular)
            chart.setEntryLabelTextSize(12f)
        }


}
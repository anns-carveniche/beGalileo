package com.carveniche.begalileo.activities.questions

import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import com.carveniche.begalileo.R
import kotlinx.android.synthetic.main.activity_questions.*
import kotlin.random.Random


class MathboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions)
        showSelectAdditionView()
    }

    private fun showSelectAdditionView() {

        val tvQuesPreText = TextView(this)
        tvQuesPreText.setText("This is Question pre text ")
        llQuestionContainer.addView(tvQuesPreText)
        val tvQuestions = TextView(this)
        tvQuestions.text = "55+44"
        llQuestionContainer.addView(tvQuestions)

        val llOptionContainer  = LinearLayout(this)
        llOptionContainer.orientation = LinearLayout.HORIZONTAL
        llOptionContainer.gravity  = Gravity.CENTER_HORIZONTAL
        val btnOptions = arrayOfNulls<Button>(4)
        for (i in 0..3)
        {
            btnOptions[i] = Button(this)
            btnOptions[i]!!.text = Random.nextInt(10,100).toString()
            llOptionContainer.addView(btnOptions[i])
        }

        llQuestionContainer.addView(llOptionContainer)

        val tvQuesPostText = TextView(this)
        tvQuesPostText.text = "this is question post text"
        llQuestionContainer.addView(tvQuesPostText)

    }


}

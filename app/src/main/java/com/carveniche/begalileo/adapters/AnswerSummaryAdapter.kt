package com.carveniche.begalileo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.content.ContextCompat
import com.carveniche.begalileo.R

import com.carveniche.begalileo.models.UserSpeedMathData
import kotlinx.android.synthetic.main.list_item_answer_summary.view.*
import org.jsoup.Jsoup

class AnswerSummaryAdapter(context: Context, var data: List<UserSpeedMathData>) : BaseAdapter() {

    lateinit var rootView: View
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var context = context
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        rootView = inflater.inflate(R.layout.list_item_answer_summary, parent, false)
        rootView.tvQuestion.text = Jsoup.parse(data[position].question).text()
        rootView.tvYourAnswer.text = data[position].userAnswer
        rootView.tvCorrectAnswer.text = data[position].correctAnswer
        if (data[position].isAnswerWright)
            rootView.ivResultIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_tick))
        else
            rootView.ivResultIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_cross))
        return rootView
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return data.size
    }
}
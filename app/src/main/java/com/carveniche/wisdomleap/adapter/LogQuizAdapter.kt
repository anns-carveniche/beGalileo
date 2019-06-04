package com.carveniche.wisdomleap.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.carveniche.wisdomleap.R
import com.carveniche.wisdomleap.di.module.QuizData

class LogQuizAdapter(private var quizDataList : List<QuizData>) : RecyclerView.Adapter<LogQuizAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_log_quiz,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return quizDataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(quizDataList[position])
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(data : QuizData)
        {
            val tvQuizName  = itemView.findViewById<TextView>(R.id.tv_quiz_name)
            val tvQuizDate = itemView.findViewById<TextView>(R.id.tv_quiz_date)
            val tvQuizScore = itemView.findViewById<TextView>(R.id.tv_quiz_score)
            val tvQuizTotal = itemView.findViewById<TextView>(R.id.tv_quiz_total_questions)
            tvQuizName.text = data.chapter_name.replace("_"," ")
            tvQuizDate.text = data.date
            tvQuizScore.text = "Score : ${data.score}"
            tvQuizTotal.text = "Total : ${data.total}"


        }
    }
}
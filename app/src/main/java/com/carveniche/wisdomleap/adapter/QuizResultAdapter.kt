package com.carveniche.wisdomleap.adapter


import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.carveniche.wisdomleap.R
import com.carveniche.wisdomleap.contract.ListChapterContract

import com.carveniche.wisdomleap.model.QuizResultModel


class QuizResultAdapter(public var context : Context, private var quizResult : List<QuizResultModel>) : RecyclerView.Adapter<QuizResultAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_quiz_result,parent,false)
        return ViewHolder(view,context)
    }

    override fun getItemCount(): Int {
        return quizResult.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(quizResult[position])
    }

    class ViewHolder(itemView : View,private var mContext: Context) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(quizResult: QuizResultModel)
        {
            val tvName  = itemView.findViewById<TextView>(R.id.tvName)
            val tvScore  = itemView.findViewById<TextView>(R.id.tvScore)
            val tvSlNo = itemView.findViewById<TextView>(R.id.tvSerialNumber)
            tvSlNo.text = quizResult.id.toString()
            tvName.text = quizResult.name
            tvScore.text = quizResult.score.toString()

        }
    }
}
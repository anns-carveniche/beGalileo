package com.carveniche.wisdomleap.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.carveniche.wisdomleap.R
import com.carveniche.wisdomleap.contract.QuizHomeContract
import com.carveniche.wisdomleap.model.QuizCategoryModel
import kotlinx.android.synthetic.main.item_quiz_category.view.*

class QuizCategroyAdapter(private val context : Context,private val quizCategoryList : List<QuizCategoryModel>,private val quizView : QuizHomeContract.View) : BaseAdapter() {
    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.item_quiz_category,parent,false)
        rowView.imageView.setBackgroundResource(quizCategoryList[position].categoryImage)
        rowView.textView.text = quizCategoryList[position].categoryName.toString()
        rowView.setOnClickListener {
            quizView.onCategorySelected(quizCategoryList[position].categoryId)
        }
        return rowView
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
       return quizCategoryList.size
    }
}
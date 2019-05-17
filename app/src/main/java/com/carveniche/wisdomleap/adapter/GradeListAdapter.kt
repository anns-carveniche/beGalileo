package com.carveniche.wisdomleap.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.carveniche.wisdomleap.R
import com.carveniche.wisdomleap.interfaces.IGradeClickListener
import com.carveniche.wisdomleap.model.GradeDetail
import kotlinx.android.synthetic.main.grade_list_item.view.*


class GradeListAdapter(
    private val context: Context,
    private val gradeList: List<GradeDetail>,
    listener: IGradeClickListener):BaseAdapter() {

    private var iGradeClickListener = listener
    private val inflater :LayoutInflater =context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView =inflater.inflate(R.layout.grade_list_item,parent,false)
        val mGradeValue = gradeList[position].name
        rowView.tvGradeNumber.text = context.getString(R.string.GradeValue,mGradeValue)
        rowView.setOnClickListener {
            iGradeClickListener.onGradeClick(position)
        }
        return rowView
    }

    override fun getItem(position: Int): Any {
        return gradeList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return gradeList.size
    }
}
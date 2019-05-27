package com.carveniche.begalileo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.carveniche.begalileo.R
import com.carveniche.begalileo.iInterfaces.IGradeBoardListener
import com.carveniche.begalileo.models.GradeDetail

import kotlinx.android.synthetic.main.grade_list_item.view.*

class GradesListAdapter(private val context:Context, private val gradeList : List<GradeDetail>, private val iGradeBoardListener: IGradeBoardListener): BaseAdapter() {
    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rowView  = inflater.inflate(R.layout.grade_list_item,parent,false)
            rowView.tvGradeNumber.text = gradeList[position].name
            rowView.setOnClickListener {
                iGradeBoardListener.onBoardClick(position)
            }
            return rowView
        }

        override fun getItem(position: Int): Any {
            return gradeList[position]
        }

        override fun getItemId(position: Int): Long {
            return gradeList[position].id.toLong()
        }

        override fun getCount(): Int {
            return gradeList.size
        }
}
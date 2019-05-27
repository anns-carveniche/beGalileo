package com.carveniche.begalileo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.carveniche.begalileo.R
import com.carveniche.begalileo.iInterfaces.IGradeBoardListener

import com.carveniche.begalileo.models.GradeBoard
import kotlinx.android.synthetic.main.grade_list_item.view.*

class BoardListAdapter(private val context:Context, private val boardList : List<GradeBoard>, private val iGradeBoardListener: IGradeBoardListener): BaseAdapter() {
    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rowView  = inflater.inflate(R.layout.grade_list_item,parent,false)
            rowView.tvGradeNumber.text = boardList[position].name
           rowView.setOnClickListener {
               iGradeBoardListener.onGradeClick(position)
           }
            return rowView
        }

        override fun getItem(position: Int): Any {
            return boardList[position]
        }

        override fun getItemId(position: Int): Long {
            return boardList[position].id.toLong()
        }

        override fun getCount(): Int {
            return boardList.size
        }
}
package com.carveniche.begalileo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.recyclerview.widget.RecyclerView


import com.carveniche.begalileo.R
import com.carveniche.begalileo.iInterfaces.IGradeBoardListener
import com.carveniche.begalileo.models.GradeDetail
import com.carveniche.begalileo.ui.concepts.fragments.ConceptHomeContract
import kotlinx.android.synthetic.main.concept_list_item.view.*


class ConceptGradesListAdapter(private val context:Context, private val gradeList : List<GradeDetail>, private val conceptView: ConceptHomeContract.View) : RecyclerView.Adapter<ConceptGradesListAdapter.ViewHolder>()
{
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvGradeNumber.text = gradeList[position].name
        holder.llContainer.isSelected = true
        holder.llContainer.setOnClickListener {
            conceptView.onGradeClick(position,it)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.concept_list_item, parent, false))
    }

    override fun getItemCount(): Int {
       return gradeList.size
    }


    class ViewHolder(view : View) : RecyclerView.ViewHolder(view)
    {
        val tvGradeNumber = view.tvGradeNumber!!
        val llContainer = view.llGradeBoardContainer!!
    }

}
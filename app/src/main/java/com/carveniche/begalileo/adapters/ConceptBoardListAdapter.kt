package com.carveniche.begalileo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


import com.carveniche.begalileo.R
import com.carveniche.begalileo.models.GradeBoard
import com.carveniche.begalileo.ui.fragments.ConceptHomeContract
import kotlinx.android.synthetic.main.concept_list_item.view.*



class ConceptBoardListAdapter(private val context:Context, private val boardList : List<GradeBoard>,var mSelectedItem : Int, private val conceptView: ConceptHomeContract.View) : RecyclerView.Adapter<ConceptBoardListAdapter.ViewHolder>()
{
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvGradeNumber.text = boardList[position].name
        holder.llContainer.isSelected = position==mSelectedItem

        holder.llContainer.setOnClickListener {
            conceptView.onBoardClick(position,it)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.concept_list_item, parent, false))
    }

    override fun getItemCount(): Int {
       return boardList.size
    }


    class ViewHolder(view : View) : RecyclerView.ViewHolder(view)
    {
        val tvGradeNumber = view.tvGradeNumber!!
        val llContainer = view.llGradeBoardContainer!!
    }

}
package com.carveniche.begalileo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


import com.carveniche.begalileo.R
import com.carveniche.begalileo.models.SubConcept
import com.carveniche.begalileo.ui.fragments.ConceptListContract
import kotlinx.android.synthetic.main.concept_horizontal_list_item.view.*


class ConceptListAdapter(private val context:Context, private val conceptList : List<SubConcept>,  private val conceptView: ConceptListContract.View) : RecyclerView.Adapter<ConceptListAdapter.ViewHolder>()
{
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvItemName.text = conceptList[position].sub_concept_name
        holder.tvItemCode.text = conceptList[position].code
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.concept_horizontal_list_item, parent, false))
    }

    override fun getItemCount(): Int {
       return conceptList.size
    }


    class ViewHolder(view : View) : RecyclerView.ViewHolder(view)
    {
        val tvItemName = view.tvItemName!!
        val tvItemCode = view.tvItemCode!!
    }

}
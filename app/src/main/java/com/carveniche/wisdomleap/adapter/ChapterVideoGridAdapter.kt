package com.carveniche.wisdomleap.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.carveniche.wisdomleap.R
import com.carveniche.wisdomleap.interfaces.IChapterVideoClickListener
import com.carveniche.wisdomleap.model.SubConceptDetails
import com.carveniche.wisdomleap.util.Constants
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class ChapterVideoGridAdapter(var context: Context,var data : List<SubConceptDetails>,var listener : IChapterVideoClickListener) : BaseAdapter(){
    private val inflater : LayoutInflater =context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView =inflater.inflate(R.layout.list_item_chapter,parent,false)
        val tvDescription  = rowView.findViewById<TextView>(R.id.tvDescription)
        val ivImageItem = rowView.findViewById<ImageView>(R.id.ivImageItem)
        Picasso.with(context).load(data[position].image)
            .fit()
            .into(ivImageItem)
        tvDescription.text = data[position].sub_concept_name
        return rowView
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return data.size
    }
}
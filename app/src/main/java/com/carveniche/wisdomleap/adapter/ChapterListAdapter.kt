package com.carveniche.wisdomleap.adapter

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.carveniche.wisdomleap.R
import com.carveniche.wisdomleap.model.ChapterListModel
import com.carveniche.wisdomleap.model.SubConceptDetail
import com.carveniche.wisdomleap.util.Constants
import com.carveniche.wisdomleap.util.URL
import com.carveniche.wisdomleap.util.screenWidth
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class ChapterListAdapter(public var context : Context,private var subConceptList : List<SubConceptDetail>) : RecyclerView.Adapter<ChapterListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_chapter,parent,false)
        return ViewHolder(view,context)
    }

    override fun getItemCount(): Int {
        return subConceptList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(subConceptList[position])
    }

    class ViewHolder(itemView : View,private var mContext: Context) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(subConceptDetail :  SubConceptDetail)
        {
            val tvDescription  = itemView.findViewById<TextView>(R.id.tvDescription)
            val ivImageItem = itemView.findViewById<ImageView>(R.id.ivImageItem)
            var screenWidth = screenWidth(mContext as Activity)

            ivImageItem.layoutParams.height = (screenWidth/2)-30
            ivImageItem.layoutParams.width = (screenWidth/2)-30

//            Picasso.with(mContext).load(subConceptDetail.image).into(ivImageItem)
            Picasso.with(mContext)
                .load(URL.SAMPLE_THUMBNAIL_IMAGE)
                .fit()
                .into(ivImageItem,object: Callback{
                    override fun onSuccess() {
                        Log.d(Constants.LOG_TAG,"Image Load Suc")
                    }

                    override fun onError() {
                        Log.d(Constants.LOG_TAG,"Image Load Failed")
                    }

                })
            tvDescription.text = subConceptDetail.sub_concept_name
        }
    }
}
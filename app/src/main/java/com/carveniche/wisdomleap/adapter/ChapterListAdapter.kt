package com.carveniche.wisdomleap.adapter

import android.app.Activity
import android.content.Context
import android.telecom.Call
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.carveniche.wisdomleap.R
import com.carveniche.wisdomleap.contract.ListChapterContract
import com.carveniche.wisdomleap.interfaces.IChapterClickListener
import com.carveniche.wisdomleap.model.ChapterListModel
import com.carveniche.wisdomleap.model.SubConceptDetail
import com.carveniche.wisdomleap.util.Constants
import com.carveniche.wisdomleap.util.URL
import com.carveniche.wisdomleap.util.screenWidth
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class ChapterListAdapter(public var context : Context,var conceptId: Int,private var subConceptList : List<SubConceptDetail>,private var viewListener : IChapterClickListener) : RecyclerView.Adapter<ChapterListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_chapter,parent,false)
        return ViewHolder(view,viewListener,context)
    }

    override fun getItemCount(): Int {
        return subConceptList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bindItems(conceptId,subConceptList[position])

    }

    class ViewHolder(itemView : View,private val listener : IChapterClickListener,private var mContext: Context) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(conceptId: Int,subConceptDetail :  SubConceptDetail)
        {
            val tvDescription  = itemView.findViewById<TextView>(R.id.tvDescription)
            val ivImageItem = itemView.findViewById<ImageView>(R.id.ivImageItem)
            var screenWidth = screenWidth(mContext as Activity)

           /* ivImageItem.layoutParams.height = (screenWidth/2)
            ivImageItem.layoutParams.width = (screenWidth/2)-30*/

            Picasso.with(mContext).load(subConceptDetail.image)
                .fit()
                .into(ivImageItem,object : Callback{
                    override fun onSuccess() {
                        Log.d(Constants.LOG_TAG,"Image Load Suc")
                    }

                    override fun onError() {
                        Log.d(Constants.LOG_TAG,"Image Load Failed")
                    }

                })


            ivImageItem.setOnClickListener {
                listener.onChapterClick(conceptId,subConceptDetail.sub_concept_id,subConceptDetail.video_url,subConceptDetail.sub_concept_name)
            }
            tvDescription.text = subConceptDetail.sub_concept_name
        }
    }
}
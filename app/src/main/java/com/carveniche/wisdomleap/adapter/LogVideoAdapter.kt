package com.carveniche.wisdomleap.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.carveniche.wisdomleap.R
import com.carveniche.wisdomleap.di.module.VideoData

class LogVideoAdapter(private var videoDataList : List<VideoData>) : RecyclerView.Adapter<LogVideoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_log_video,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return videoDataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(videoDataList[position])
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(data : VideoData)
        {
            val tvVideoName  = itemView.findViewById<TextView>(R.id.tv_video_name)
            val tvVideoDate = itemView.findViewById<TextView>(R.id.tv_video_date)
            tvVideoName.text = data.sub_concept_name.replace("_"," ")
            tvVideoDate.text = data.date

        }
    }
}
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
import com.carveniche.wisdomleap.interfaces.IQuizClickListener
import com.carveniche.wisdomleap.model.ChapterListModel
import com.carveniche.wisdomleap.model.RecentPractice
import com.carveniche.wisdomleap.model.RecentVideo
import com.carveniche.wisdomleap.model.SubConceptDetail
import com.carveniche.wisdomleap.util.Constants
import com.carveniche.wisdomleap.util.URL
import com.carveniche.wisdomleap.util.screenWidth
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class DashboardRecentQuizListAdapter(public var context : Context,  private var recentPractice: List<RecentPractice>, private var viewListener : IQuizClickListener) : RecyclerView.Adapter<DashboardRecentQuizListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_chapter,parent,false)
        return ViewHolder(view,viewListener,context)
    }

    override fun getItemCount(): Int {
        return recentPractice.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bindItems(recentPractice[position])

    }

    class ViewHolder(itemView : View,private val listener : IQuizClickListener,private var mContext: Context) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(recentPractice: RecentPractice )
        {
            val tvDescription  = itemView.findViewById<TextView>(R.id.tvDescription)
            val ivImageItem = itemView.findViewById<ImageView>(R.id.ivImageItem)
            var screenWidth = screenWidth(mContext as Activity)

           /* ivImageItem.layoutParams.height = (screenWidth/2)
            ivImageItem.layoutParams.width = (screenWidth/2)-30*/

            Picasso.with(mContext).load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT39r3PtQTWg2d-JhFeGWQgrjlx0etaq3ffc1vfCNy_z1FqSbAwJA")
                .fit()
                .into(ivImageItem)


            ivImageItem.setOnClickListener {
               listener.onQuizItemClick(recentPractice)
            }
            tvDescription.text = recentPractice.chapter_name
        }
    }
}
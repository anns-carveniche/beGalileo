package com.carveniche.wisdomleap.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.carveniche.wisdomleap.R
import com.carveniche.wisdomleap.model.CourseDetail
import com.carveniche.wisdomleap.util.Constants
import com.carveniche.wisdomleap.util.URL
import com.carveniche.wisdomleap.view.activity.SubjectActivity
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.list_item_subject.view.*

class SubjectListAdapter(private val context: Context,private val courseDetail: List<CourseDetail>) : BaseAdapter() {

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.list_item_subject,parent,false)
        /*Picasso.with(context).load(URL.WISDOM_LEAP_URL+courseDetail[position].image_url)
            .into(rowView.ivImageItem)*/
        rowView.tv_chapter_count.text = context.getString(R.string.chapter_count,courseDetail[position].chapters_count)
        rowView.tv_lesson_count.text = context.getString(R.string.lesson_count,courseDetail[position].lessons_count)
        rowView.tv_subject_name.text = courseDetail[position].name

        return rowView
    }

    override fun getItem(position: Int): Any {
     return position
    }

    override fun getItemId(position: Int): Long {
       return position.toLong()
    }

    override fun getCount(): Int {
       return courseDetail.size
    }
}


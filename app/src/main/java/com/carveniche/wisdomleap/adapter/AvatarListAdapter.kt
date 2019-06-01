package com.carveniche.wisdomleap.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.carveniche.wisdomleap.R
import com.carveniche.wisdomleap.contract.ProfileHomeContract
import com.carveniche.wisdomleap.interfaces.IGradeClickListener
import com.carveniche.wisdomleap.model.GradeDetail
import com.carveniche.wisdomleap.util.Constants
import kotlinx.android.synthetic.main.grade_list_item.view.*
import kotlinx.android.synthetic.main.item_list_avatar.view.*


class AvatarListAdapter(
    private val context: Context,
    private val imageList: List<Int>,
    private val listener: ProfileHomeContract.View):BaseAdapter() {
    private val inflater :LayoutInflater =context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView =inflater.inflate(R.layout.item_list_avatar,parent,false)
        rowView.iv_avatar.setImageDrawable(context.getDrawable(imageList[position]))
        rowView.iv_avatar.setOnClickListener {
            listener.updateProfileAvatar(imageList[position])
        }
        return rowView
    }

    override fun getItem(position: Int): Any {
        return imageList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return imageList.size
    }
}
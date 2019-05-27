package com.carveniche.begalileo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.carveniche.begalileo.util.Constants


import com.carveniche.begalileo.R
import com.carveniche.begalileo.contract.HomeContract
import kotlinx.android.synthetic.main.home_list_item.view.*


class HomeItemListAdapter(private val context:Context ,private val homeContract: HomeContract.View) : RecyclerView.Adapter<HomeItemListAdapter.ViewHolder>()
{
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.tvItemName.text = Constants.HOME_ITEM_NAME_LIST[position]
            holder.ivItemImage.setImageDrawable(context.getDrawable(Constants.HOME_ITEM_IMAGE_LIST[position]))
            holder.itemView.setOnClickListener {
                    homeContract.onItemClick(position)
            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.home_list_item, parent, false))
    }

    override fun getItemCount(): Int {
       return Constants.HOME_ITEM_IMAGE_LIST.size
    }


    class ViewHolder(view : View) : RecyclerView.ViewHolder(view)
    {
        val tvItemName = view.tvHomeItemName!!
        val ivItemImage = view.ivHomeItemImage!!

    }

}
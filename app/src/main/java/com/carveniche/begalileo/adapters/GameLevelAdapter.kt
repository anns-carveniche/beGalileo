package com.carveniche.begalileo.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.carveniche.begalileo.R
import com.carveniche.begalileo.models.Level


class GameLevelAdapter(private val context: Context, private val levelDataList: List<Level>) : BaseAdapter() {


    private class ViewHolder(row: View?) {
        var clContainer: ConstraintLayout? = null
        var tvLevelType: TextView? = null
        var tvLevelName: TextView? = null
        var ivTrophy : ImageView? = null

        init {
            this.clContainer = row!!.findViewById<ConstraintLayout>(R.id.clContainer)
            this.tvLevelName = row!!.findViewById<TextView>(R.id.tvLevelName)
            this.tvLevelType = row!!.findViewById<TextView>(R.id.tvLevelType)
            this.ivTrophy = row.findViewById<ImageView>(R.id.ivTrophy)

        }
    }

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view: View
        val viewHolder: ViewHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.game_level_list_item, null)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        viewHolder.tvLevelType!!.text = levelDataList[position].type.capitalize()
        viewHolder.tvLevelName!!.text = "Level " + levelDataList[position].name.capitalize()
        when {
            levelDataList[position].type == "easy" ->
                showEasyLevelView(viewHolder,levelDataList[position])

            levelDataList[position].type == "medium" -> showMediumLevelView(viewHolder,levelDataList[position])
            else -> showHardView(viewHolder,levelDataList[position])
        }

        return view
    }

    private fun showEasyLevelView(viewHolder: ViewHolder, level: Level) {

        if(level.level_status)
        {
            viewHolder.clContainer!!.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    android.R.color.holo_red_dark
                )
            )
        }
        else
        {
            viewHolder.clContainer!!.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    android.R.color.holo_red_light
                )
            )
        }
        if(level.trophy)
            viewHolder.ivTrophy!!.visibility = View.VISIBLE
        else
            viewHolder.ivTrophy!!.visibility = View.GONE

    }
    private fun showMediumLevelView(viewHolder: ViewHolder, level: Level) {

        if(level.level_status)
        {
            viewHolder.clContainer!!.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    android.R.color.holo_green_dark
                )
            )
        }
        else
        {
            viewHolder.clContainer!!.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    android.R.color.holo_green_light
                )
            )
        }
        if(level.trophy)
            viewHolder.ivTrophy!!.visibility = View.VISIBLE
        else
            viewHolder.ivTrophy!!.visibility = View.GONE

    }
    private fun showHardView(viewHolder: ViewHolder, level: Level) {

        if(level.level_status)
        {
            viewHolder.clContainer!!.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    android.R.color.holo_orange_dark
                )
            )
        }
        else
        {
            viewHolder.clContainer!!.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    android.R.color.holo_orange_light
                )
            )
        }
        if(level.trophy)
            viewHolder.ivTrophy!!.visibility = View.VISIBLE
        else
            viewHolder.ivTrophy!!.visibility = View.GONE

    }



    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return levelDataList.size

    }


}
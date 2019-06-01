package com.carveniche.wisdomleap.util

import android.app.Activity
import android.content.Context
import android.graphics.Typeface
import android.util.DisplayMetrics
import android.util.Patterns
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.carveniche.wisdomleap.model.MySharedPreferences
import com.google.android.material.snackbar.Snackbar
import java.util.*


    fun showLongToast(msg:String,context: Context)
    {
        Toast.makeText(context,msg, Toast.LENGTH_LONG).show()
    }
    fun showShortToast(msg:String,context: Context)
    {
        Toast.makeText(context,msg, Toast.LENGTH_SHORT).show()
    }

    fun screenWidth(activity: Activity) : Int
    {
        val display  = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(display)
        return display.widthPixels
    }
    fun screenHeight(activity: Activity) : Int
    {
        val display  = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(display)
        return display.heightPixels
    }

    fun Context.toast(message: CharSequence) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    fun Context.snackBar(message: CharSequence, view : View) =
        Snackbar.make(view,message, Snackbar.LENGTH_LONG).show()

    fun String.isValidEmail(): Boolean
            = this.isNotEmpty() &&
            Patterns.EMAIL_ADDRESS.matcher(this).matches()

    fun Random.getInt(range: IntRange):Int{
        return range.start + nextInt(range.last - range.start)
    }

    fun getPercentage(totValue:Float,percentValue:Float) : Float
    {
        return (totValue/100)*percentValue
    }

    fun showLoadingProgress(progressBar : ProgressBar,status : Boolean)
    {
        progressBar.bringToFront()
        if(status)
            progressBar.visibility = View.VISIBLE
        else
            progressBar.visibility = View.GONE
    }
    fun TextView.setCustomFontType(context: Context,fontPath : String)
    {
        var customFont  = Typeface.createFromAsset(context.assets,fontPath)
        typeface = customFont
    }

     fun isFirstLaunchToday(mySharedPreferences: MySharedPreferences) : Boolean
    {
        val calendar = Calendar.getInstance()
        val currentDay  = calendar.get(Calendar.DAY_OF_MONTH)
        val lastDay = mySharedPreferences.getIntData(Constants.LAST_DAY)
        return if(lastDay!=currentDay) {
            mySharedPreferences.putIntData(Constants.LAST_DAY,currentDay)
            true
        } else {
            false
        }
    }




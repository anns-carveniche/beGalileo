package com.carveniche.begalileo.util


import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.util.Patterns
import android.view.View
import android.widget.Toast

import com.google.android.material.snackbar.Snackbar
import java.util.*
import android.net.NetworkInfo
import android.net.ConnectivityManager



fun showLongToast(msg:String,context:Context)
{
    Toast.makeText(context,msg,Toast.LENGTH_LONG).show()
}
fun showShortToast(msg:String,context:Context)
{
    Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
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
fun Context.snackBar(message: CharSequence,view : View) =
        Snackbar.make(view,message,Snackbar.LENGTH_LONG).show()

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

fun isNetworkAvailable(context: Context): Boolean {
    // Get Connectivity Manager class object from Systems Service
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    // Get Network Info from connectivity Manager
    val networkInfo = cm.activeNetworkInfo

    // if no network is available networkInfo will be null
    // otherwise check if we are connected
    return networkInfo != null && networkInfo.isConnected
}





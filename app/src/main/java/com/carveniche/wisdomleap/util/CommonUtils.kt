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
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.google.android.material.snackbar.Snackbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import android.webkit.WebSettings
import android.R.id
import android.graphics.Color
import android.webkit.WebView
import org.jsoup.Jsoup


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

    fun showtMathView(webView: WebView,str : String)
    {
       // var test = "<style>\r\n.fraction {\r\ndisplay: inline-block;\r\nvertical-align: middle; \r\nmargin: 0 0.2em 0.4ex;\r\ntext-align: center;\r\nfont-size: 20px;\r\n}\r\n.fraction > span {\r\ndisplay: block;\r\npadding-top: 0.12em;\r\n}\r\n.fraction span.fdn {border-top: thin solid black;}\r\n.fraction span.bar {display: none;}\r\n\r\n</style>\r\n\r\nif \r\n\r\n<div class=\"fraction\">\r\n<span class=\"fup\">x +   1 </span>\r\n<span class=\"bar\">/</span>\r\n<span class=\"fdn\">2x+  3</span>\r\n</div> =  \r\n\r\n<div class=\"fraction\">\r\n<span class=\"fup\">3</span>\r\n<span class=\"bar\">/</span>\r\n<span class=\"fdn\">8</span></div>, then x = ?"
        val data = StringBuilder()
        data.append("<HTML><HEAD><LINK href=\"maths_style.css\" type=\"text/css\" rel=\"stylesheet\"/></HEAD><body>")
        data.append(str.replace("\n","").replace("\r",""))
        data.append("</body></HTML>")
        webView.setBackgroundColor(Color.TRANSPARENT)
        webView.loadDataWithBaseURL("file:///android_asset/", data.toString(), "text/html", "utf-8", null)
    }







package com.carveniche.begalileo.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.carveniche.begalileo.R
import com.carveniche.begalileo.util.Constants
import com.carveniche.begalileo.util.isNetworkAvailable
import com.carveniche.begalileo.util.showShortToast
import kotlinx.android.synthetic.main.activity_live_class.*
import java.lang.reflect.InvocationTargetException
import android.content.Intent

import android.net.Uri


class LiveClassActivity : AppCompatActivity() {

   // var  checkUrl = "https://liveclasses.concepttutors.com/bigbluebutton/api/join?fullName=Live&meetingID=aa4870e1-1eec-40fd-9663-d2b4674f30ac-1473853555&password=cd9d3215&checksum=05b242dcb86e1a23e7e5eefbb6fa9d3e8634d0fe"
    var  checkUrl = "http://192.168.1.37:3000/bigbluebutton/rooms/antikaa/join"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_class)
        if (isNetworkAvailable(this))
            initWeb()
        else
            showShortToast("No Internet", this)
    }


    private fun initWeb() {
        webView.loadUrl(checkUrl)
        webView.webViewClient = MyCustomWebClient()
        configureWebview(webView)

        /*val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(checkUrl)
        startActivity(i)*/
    }



    private class MyCustomWebClient : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            Log.d(Constants.LOG_TAG, request.toString())
            return super.shouldOverrideUrlLoading(view, request)
        }

    }

    @SuppressLint("SetJavaScriptEnabled")
    fun configureWebview(webView: WebView) {
        var ws = webView.settings
        ws.javaScriptEnabled = true
        ws.allowFileAccess = true;
        try {
            Log.d(Constants.LOG_TAG, "Enabling HTML5-Features")
            val m1 = WebSettings::class.java.getMethod(
                "setDomStorageEnabled",
                *arrayOf<Class<*>>(java.lang.Boolean.TYPE)
            )
            m1.invoke(ws, java.lang.Boolean.TRUE)

            val m2 =
                WebSettings::class.java.getMethod("setDatabaseEnabled", *arrayOf<Class<*>>(java.lang.Boolean.TYPE))
            m2.invoke(ws, java.lang.Boolean.TRUE)

            val m3 = WebSettings::class.java.getMethod("setDatabasePath", *arrayOf<Class<*>>(String::class.java))
            m3.invoke(ws, "/data/data/$packageName/databases/")

            val m4 =
                WebSettings::class.java.getMethod("setAppCacheMaxSize", *arrayOf<Class<*>>(java.lang.Long.TYPE))
            m4.invoke(ws, 1024 * 1024 * 8)

            val m5 = WebSettings::class.java.getMethod("setAppCachePath", *arrayOf<Class<*>>(String::class.java))
            m5.invoke(ws, "/data/data/$packageName/cache/")

            val m6 =
                WebSettings::class.java.getMethod("setAppCacheEnabled", *arrayOf<Class<*>>(java.lang.Boolean.TYPE))
            m6.invoke(ws, java.lang.Boolean.TRUE)

            Log.d(Constants.LOG_TAG, "Enabled HTML5-Features")
        } catch (e: NoSuchMethodException) {
            Log.e(Constants.LOG_TAG, "Reflection fail", e)
        } catch (e: InvocationTargetException) {
            Log.e(Constants.LOG_TAG, "Reflection fail", e)
        } catch (e: IllegalAccessException) {
            Log.e(Constants.LOG_TAG, "Reflection fail", e)
        }


    }
}



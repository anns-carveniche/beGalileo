package com.carveniche.begalileo.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.DialogFragment
import com.carveniche.begalileo.R
import com.carveniche.begalileo.ui.activities.ISolutionListener
import com.carveniche.begalileo.util.Constants
import com.carveniche.begalileo.util.getHtmlFormattedSolution
import kotlinx.android.synthetic.main.dialog_solution.view.*

class ShowSolutionDialogFragment(var value : String,var listener : ISolutionListener) : DialogFragment() {

    private lateinit var rootView: View
    private lateinit var webViewsolution : WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         rootView = inflater.inflate(R.layout.dialog_solution, container, false)
        this.webViewsolution = rootView.wv_dialog_solution
         initUI()
         return rootView

    }

    private fun initUI() {
        var strHtml = getHtmlFormattedSolution(value)
        Log.d(Constants.LOG_TAG,strHtml)
        webViewsolution.loadData(strHtml,"text/html","UTF-8")
       // webViewsolution.setInitialScale(1);
        webViewsolution.settings.loadWithOverviewMode = true
        webViewsolution.settings.useWideViewPort = true
        webViewsolution.settings.builtInZoomControls = true
        webViewsolution.webViewClient = object  : WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                Log.d(Constants.LOG_TAG,"On Page Finished")
            }
        }

        rootView.btn_close.setOnClickListener {
            dialog.dismiss()
            listener.onSolutionModelClosed()
        }


    }
}
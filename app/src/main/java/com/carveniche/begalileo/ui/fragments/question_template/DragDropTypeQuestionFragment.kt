package com.carveniche.begalileo.ui.fragments.question_template


import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.webkit.WebView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment

import com.carveniche.begalileo.R
import com.carveniche.begalileo.models.PracticeQuizQuestionModel
import com.carveniche.begalileo.ui.activities.PracticeMathActivity
import com.carveniche.begalileo.util.Constants
import com.carveniche.begalileo.util.getHtmlFormaatedQuestionData
import com.carveniche.begalileo.util.setWebSettingsQuiz
import kotlinx.android.synthetic.main.fragment_drag_drop_type_question.*
import kotlinx.android.synthetic.main.fragment_drag_drop_type_question.view.*
import kotlinx.android.synthetic.main.layout_random_drag_drop.view.*


/**
 * A simple [Fragment] subclass.
 */
class DragDropTypeQuestionFragment : Fragment(),View.OnTouchListener,View.OnDragListener {

    private lateinit var rootView : View
    private lateinit var practiceMathActivity: PracticeMathActivity
    private lateinit var practiceData : PracticeQuizQuestionModel
    private lateinit var wvQuestion : WebView

    override fun onCreate(savedInstanceState: Bundle?) {

        practiceMathActivity = activity as PracticeMathActivity
        practiceData = practiceMathActivity.practiceQuizQuestionModel
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_drag_drop_type_question, container, false)
        initUI()
        return rootView

    }




    fun initUI()
    {
        wvQuestion = rootView.wv_question
        showQuestionText()
        if(practiceData.question_data.type==Constants.QUESTION_SUB_TYPE_RANDOM_DRAG_DROP)
            showRandomArrangmentDragDrop()
    }

    private fun showRandomArrangmentDragDrop() {
        var webView = WebView(context)
        // setWebSettingsQuiz(webView.settings,context!!)
        webView.setBackgroundColor(0)
        webView.isVerticalScrollBarEnabled = false
        webView.isHorizontalScrollBarEnabled = false

        var strHtml = getHtmlFormaatedQuestionData(practiceData.question_data.questionContent[0].value)
        webView.loadData(strHtml,"text/html","UTF-8")


        val webViewParams =  LinearLayout.LayoutParams(100,100,1.0f)



        var li = LayoutInflater.from(context)
        var subView = li.inflate(R.layout.layout_random_drag_drop,null)
       // subView.grid.adapter = ArrayAdapter<WebView>(context,android.R.layout.simple_gallery_item,)


        ll_question_choice_container.addView(webView,webViewParams)
    }

    private fun showQuestionText() {
        setWebSettingsQuiz(wvQuestion.settings,context!!)
        wvQuestion.setBackgroundColor(0)
        wvQuestion.loadData(getHtmlFormaatedQuestionData(practiceData.question_data.questionName),"text/html", "UTF-8")
    }

    override fun onTouch(view: View?, event: MotionEvent?): Boolean {
       return if(event!!.action === MotionEvent.ACTION_DOWN){
           val dragShadowBuilder = View.DragShadowBuilder(view)
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
               view!!.startDragAndDrop(null,dragShadowBuilder,view,0)
           } else {
               view!!.startDrag(null,dragShadowBuilder,view,0);
           }

           true
       }
        else
       {
           false
       }
    }

    override fun onDrag(view:View, dragEvent: DragEvent):Boolean {
       // Log.d(Constants.LOG_TAG, "onDrag: view->$view\n DragEvent$dragEvent")
        when (dragEvent.action) {
            DragEvent.ACTION_DRAG_ENDED -> {
                Log.d(Constants.LOG_TAG, "onDrag: ACTION_DRAG_ENDED ")
                return true
            }
            DragEvent.ACTION_DRAG_EXITED -> {
                Log.d(Constants.LOG_TAG, "onDrag: ACTION_DRAG_EXITED")
                return true
            }
            DragEvent.ACTION_DRAG_ENTERED -> {
                Log.d(Constants.LOG_TAG, "onDrag: ACTION_DRAG_ENTERED")
                return true
            }
            DragEvent.ACTION_DRAG_STARTED -> {
                Log.d(Constants.LOG_TAG, "onDrag: ACTION_DRAG_STARTED")
                return true
            }
            DragEvent.ACTION_DROP -> {
                Log.d(Constants.LOG_TAG, "onDrag: ACTION_DROP")
                val container = view as LinearLayout
                container.removeAllViews()
                val textView = TextView(context)
                textView.text = "TARGET"

                container.addView(textView)

               /* Log.d(Constants.LOG_TAG, "onDrag:viewX" + dragEvent.x + "viewY" + dragEvent.y)
                Log.d(Constants.LOG_TAG, "onDrag: Owner->" + tvState.parent)
                val tvParent = tvState.parent as ViewGroup
                tvParent.removeView(tvState)
                val container = view as LinearLayout
                container.addView(tvState)
                tvParent.removeView(tvState)
                tvState.x = dragEvent.x
                tvState.y = dragEvent.y
                view.addView(tvState)
                view.setVisibility(View.VISIBLE)*/
                return true
            }
            DragEvent.ACTION_DRAG_LOCATION -> {
               // Log.d(Constants.LOG_TAG, "onDrag: ACTION_DRAG_LOCATION")
                return true
            }
            else -> return false
        }
    }

    companion object
    {
        var TAG = Constants.QUESTION_TYPE_DRAG_DROP
    }

}

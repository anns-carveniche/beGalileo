package com.carveniche.begalileo.ui.fragments.question_template


import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment

import com.carveniche.begalileo.R
import com.carveniche.begalileo.models.PracticeQuizQuestionModel
import com.carveniche.begalileo.ui.activities.PracticeMathActivity
import com.carveniche.begalileo.util.Constants
import kotlinx.android.synthetic.main.fragment_drag_drop_type_question.*
import kotlinx.android.synthetic.main.fragment_drag_drop_type_question.view.*

/**
 * A simple [Fragment] subclass.
 */
class DragDropTypeQuestionFragment : Fragment(),View.OnTouchListener,View.OnDragListener {

    private lateinit var rootView : View
    private lateinit var practiceMathActivity: PracticeMathActivity
    private lateinit var practiceData : PracticeQuizQuestionModel


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

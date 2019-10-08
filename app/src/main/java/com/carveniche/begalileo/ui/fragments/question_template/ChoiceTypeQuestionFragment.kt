package com.carveniche.begalileo.ui.fragments.question_template


import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.*


import com.carveniche.begalileo.models.PracticeQuizQuestionModel
import com.carveniche.begalileo.ui.activities.PracticeMathActivity
import com.carveniche.begalileo.util.Constants
import com.carveniche.begalileo.util.getHtmlFormaatedQuestionData
import com.carveniche.begalileo.util.setWebSettingsQuiz
import kotlinx.android.synthetic.main.fragment_choice_type_question.*
import com.carveniche.begalileo.R
import com.carveniche.begalileo.ui.activities.ISolutionListener
import com.carveniche.begalileo.ui.fragments.ShowSolutionDialogFragment
import com.carveniche.begalileo.util.showShortToast
import com.squareup.picasso.Picasso
import org.jsoup.Jsoup
import ru.nikartm.support.BadgePosition
import ru.nikartm.support.ImageBadgeView


/**
 * A simple [Fragment] subclass.
 */
class ChoiceTypeQuestionFragment : Fragment(),View.OnTouchListener {



    private lateinit var rootView : View
    private lateinit var practiceMathActivity: PracticeMathActivity
    private lateinit var practiceData : PracticeQuizQuestionModel
    private lateinit var frameColor : String
    private var badgeCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        practiceMathActivity = activity as PracticeMathActivity
        practiceData = practiceMathActivity.practiceQuizQuestionModel
        frameColor = "#64000000"

        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_choice_type_question, container, false)
        return rootView;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {

       /* Log.d(Constants.LOG_TAG,"Desnity Value ${resources.displayMetrics.density } -- ${resources.displayMetrics.densityDpi
        }")*/
        setWebSettingsQuiz(wv_question.settings,context!!)
        wv_question.setBackgroundColor(0)
        wv_question.loadData(getHtmlFormaatedQuestionData(practiceData.question_data.questionName), "text/html", "UTF-8");
        btn_next.setOnClickListener {
            practiceMathActivity.getPracticeQuizQuestion()
        }
        displayQuestionContent()
        displayChoiceOptions()
    }

    private fun displayChoiceOptions() {
        practiceData.question_data.choices.forEachIndexed { index, s ->
            var webView = WebView(context)
            // setWebSettingsQuiz(webView.settings,context!!)
            webView.tag = "wv_choice_$index"
            webView.setBackgroundColor(0)
            webView.isVerticalScrollBarEnabled = false
            webView.isHorizontalScrollBarEnabled = false

            var strHtml = getHtmlFormaatedQuestionData(s)
            webView.loadData(strHtml,"text/html","UTF-8")

            webView.setOnTouchListener(this)

            val webViewParams =  LinearLayout.LayoutParams(100,100,1.0f)

            ll_question_choice_container.addView(webView,webViewParams)

        }

    }

    private fun onOptionClicked(value : Int)
    {
       Log.d(Constants.LOG_TAG,"Option Clicked $value")
        practiceMathActivity.openShowDialogFragment(practiceData.question_data.solution.model[0].`val`)
    }



    private fun displayQuestionContent() {
        var rows = practiceData.question_data.rows.toInt()
        var columns =  practiceData.question_data.cols.toInt()


        for(i in 0..rows)
        {

            var layoutQuestionDataContainer = LinearLayout(context)
            layoutQuestionDataContainer.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT)
            layoutQuestionDataContainer.orientation = LinearLayout.HORIZONTAL

            for(j in 0..columns)
            {
                practiceData.question_data.questionContent.forEach {
                  //  Log.d(Constants.LOG_TAG,"${it.row} == $i ** ${it.col} == $j")
                    if(it.row == i && it.col==j)
                    {

                       var srcs = Jsoup.parse(it.value).select("[src]")

                        if(srcs.size>0)
                        {
                            var imagUrl = srcs[0].attr("abs:src")
                            Log.d(Constants.LOG_TAG,"Html Tag : "+ srcs[0].attr("abs:src"))
                            var ivQuestionView = ImageBadgeView(context)
                            var params = LinearLayout.LayoutParams(80, 60)
                            params.setMargins(10,0,10,0)
                            ivQuestionView.layoutParams = params
                            ivQuestionView.maxHeight = 20
                            ivQuestionView.maxWidth =20
                            Picasso.get().load(imagUrl).into(ivQuestionView)
                            ivQuestionView.setOnClickListener {
                                addBadge(ivQuestionView)
                            }
                            layoutQuestionDataContainer.addView(ivQuestionView)
                        }
                        else
                        {
                            var tvQuestionView = TextView(context)
                            var params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
                            params.setMargins(10,0,10,0)
                            tvQuestionView.layoutParams = params
                            tvQuestionView.text = it.value
                            layoutQuestionDataContainer.addView(tvQuestionView)
                        }

                    }
                }
            }
            ll_question_content_container.addView(layoutQuestionDataContainer)

        }
    }

   /* fun openShowDialogFragment(value : String)
    {
        val fragmentTransaction = childFragmentManager.beginTransaction()
        val prev = childFragmentManager.findFragmentByTag("dialog")
        if(prev!=null)
        {
            fragmentTransaction.remove(prev)
        }
        fragmentTransaction.addToBackStack(null)
        val dialogFragment = ShowSolutionDialogFragment(value)
        dialogFragment.show(fragmentTransaction,"dialog")
    }*/

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if(event!!.action == MotionEvent.ACTION_DOWN)
        {
            Log.d(Constants.LOG_TAG,"WEB VIEW EVENT ; ${v!!.tag.toString().takeLast(1)}")
            onOptionClicked(v.tag.toString().takeLast(1).toInt())

        }


       return false
    }




    private fun addBadge(imageBadge : ImageBadgeView)
    {
        if(!imageBadge.isBadgeOvalAfterFirst)
        {
            Log.d(Constants.LOG_TAG,"Batch : ${imageBadge.isBadgeOvalAfterFirst}")
            badgeCount++
            imageBadge.setBadgeValue(badgeCount)
                .setBadgeOvalAfterFirst(true)
                .setBadgeTextSize(16f)
                .setMaxBadgeValue(999)
                .setBadgePosition(BadgePosition.BOTTOM_RIGHT)
                .setBadgeTextStyle(Typeface.NORMAL)
                .setShowCounter(true)
                .setBadgePadding(6)
        }

    }


    companion object
    {
        var TAG = Constants.QUESTION_TYPE_SELECT_CHOICE
    }

}

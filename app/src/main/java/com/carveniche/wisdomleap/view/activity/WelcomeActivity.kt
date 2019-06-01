package com.carveniche.wisdomleap.view.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.viewpager.widget.ViewPager
import com.carveniche.wisdomleap.R
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_welcome.*

import android.content.Intent

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.carveniche.wisdomleap.util.Constants
import com.carveniche.wisdomleap.util.setCustomFontType


import org.jsoup.Jsoup


class WelcomeActivity : AppCompatActivity() {

    private lateinit var mViewPager : ViewPager
    private lateinit var dotsLayout : LinearLayout
    private lateinit var welcomeViewPagerAdapter: WelcomeViewPagerAdapter
    private var dots = mutableListOf<TextView>()
    public var layouts = mutableListOf<Int>()
    private lateinit var btnSkip : Button
    private lateinit var btnNext : Button
    private var isLoggedIn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        initUI()
        isLoggedIn = intent.getBooleanExtra(Constants.LOGGED_IN,false)
    }

    private fun initUI() {
        mViewPager = view_pager
        dotsLayout = layoutDots
        btnSkip = btn_skip
        btnNext = btn_next
        layouts.add(R.layout.welcome_slide_1)
        layouts.add(R.layout.welcome_slide_2)
        layouts.add(R.layout.welcome_slide_3)


        addBottomDots(0)
        welcomeViewPagerAdapter = WelcomeViewPagerAdapter()
        mViewPager.adapter = welcomeViewPagerAdapter
        mViewPager.addOnPageChangeListener(viewPageListener)

        btnNext.setOnClickListener {
            var current = getItem(+1)
            if(current < layouts.size)
            {
                mViewPager.currentItem = current
            }
            else
            {
                goToLoginActivity()
            }
        }
        btnSkip.setOnClickListener {
            goToLoginActivity()
        }

    }

    private fun goToLoginActivity()
    {
        if(isLoggedIn)
        {
            var intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        else
        {
            var intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

    }

    private fun addBottomDots(currentPage: Int) {
        Log.d(Constants.LOG_TAG,"Page $currentPage")
        val colorsActive = resources.getIntArray(R.array.array_dot_active)
        val colorsInactive = resources.getIntArray(R.array.array_dot_inactive)
        dotsLayout.removeAllViews()

        layouts.forEachIndexed { index, i ->
            var textView = TextView(this)
            textView.text = Jsoup.parse("&#8226;").text()
            textView.textSize = 35f
            Log.d(Constants.LOG_TAG,"$index - $currentPage")
            if(index==currentPage)
                 textView.setTextColor(colorsActive[currentPage])
            else
                textView.setTextColor(colorsInactive[currentPage])
            dotsLayout.addView(textView)
        }


    }
    private fun getItem(i:Int) : Int
    {
        return mViewPager.currentItem + i
    }

    private var viewPageListener = object : ViewPager.OnPageChangeListener{
        override fun onPageScrollStateChanged(state: Int) {

        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

        }

        override fun onPageSelected(position: Int) {
          addBottomDots(position)
            if(position== layouts.size-1)
            {
                btnNext.text = getString(R.string.start)
                btnSkip.visibility = View.INVISIBLE
            }
            else
            {
                btnNext.text = getString(R.string.next)
                btnSkip.visibility = View.VISIBLE
            }
        }

    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if(hasFocus)
            hideSystemUI()

    }
    private fun hideSystemUI() {
        val decorView = window.decorView
        decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                )
    }





    //View Page Adapter
   inner class WelcomeViewPagerAdapter : PagerAdapter() {
        private lateinit var layoutInflater : LayoutInflater
        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            layoutInflater = LayoutInflater.from(applicationContext)
            var view = layoutInflater.inflate(layouts[position],container,false)
            var tvDescription = view.findViewById<TextView>(R.id.tvDescription)
            var tvTitle = view.findViewById<TextView>(R.id.tvTitle)
            tvTitle.setCustomFontType(applicationContext,Constants.FONT_ROBOTO_LIGHT)
            tvDescription.setCustomFontType(applicationContext,Constants.FONT_NICONNE)
            tvDescription.textSize = 25f
            container.addView(view)
            return view
        }


        override fun getCount(): Int {
            return layouts.size
        }

       override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
           val view = `object` as View
           container.removeView(view)
       }


    }
}


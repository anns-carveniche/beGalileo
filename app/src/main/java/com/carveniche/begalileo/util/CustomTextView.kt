package com.carveniche.begalileo.util

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.Log
import android.widget.TextView
import com.carveniche.begalileo.util.Constants.Companion.ANDROID_SCHEMA



class CustomTextView : TextView {

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        val textStyle = attrs.getAttributeIntValue(ANDROID_SCHEMA, "textStyle", Typeface.NORMAL)

        when(textStyle)
        {
            Typeface.BOLD -> initBold()
            Typeface.NORMAL -> init()
            else -> init()
        }
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val textStyle = attrs.getAttributeIntValue(ANDROID_SCHEMA, "textStyle", Typeface.NORMAL)

        when(textStyle)
        {
            Typeface.BOLD -> initBold()
            Typeface.NORMAL -> init()
            else -> init()
        }

    }

    private fun initBold() {
        val tf = Typeface.createFromAsset(
            context.assets,
            Constants.FONT_ROBOTO
        )
        typeface = tf
    }

    constructor(context: Context) : super(context) {

        init()
    }

    private fun init() {
        val tf = Typeface.createFromAsset(
            context.assets,
            Constants.FONT_ROBOTO
        )
        typeface = tf

    }
}
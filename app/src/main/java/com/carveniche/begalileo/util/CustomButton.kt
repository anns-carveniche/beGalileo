package com.carveniche.wisdomleap.util

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.Log
import android.widget.Button
import com.carveniche.begalileo.util.Constants

class CustomButton : Button {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val textStyle = attrs.getAttributeIntValue(Constants.ANDROID_SCHEMA, "textStyle", Typeface.NORMAL)

        when(textStyle)
        {
            Typeface.BOLD -> initBold()
            Typeface.NORMAL -> init()
            else -> init()
        }
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        val textStyle = attrs.getAttributeIntValue(Constants.ANDROID_SCHEMA, "textStyle", Typeface.NORMAL)

        when(textStyle)
        {
            Typeface.BOLD -> initBold()
            Typeface.NORMAL -> init()
            else -> init()
        }
    }

    private fun init() {
        val tf = Typeface.createFromAsset(
            context.assets,
            Constants.FONT_MONTSERRAT
        )
        typeface = tf

    }
    private fun initBold() {
        val tf = Typeface.createFromAsset(
            context.assets,
            Constants.FONT_MONTSERRAT
        )
        typeface = tf
    }
}
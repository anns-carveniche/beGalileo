package com.carveniche.begalileo.ui.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.carveniche.begalileo.R
import com.carveniche.begalileo.util.Constants
import kotlinx.android.synthetic.main.fragment_number_practice.*

class NumberPracticeFragment : Fragment(),View.OnClickListener {


    private lateinit var rootView : View
    private var selectedNumberList = mutableListOf<Int>()
    private var selectedOperation = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_number_practice, container, false)
        return rootView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {

        btnNumber1.setOnClickListener(this)
        btnNumber2.setOnClickListener(this)
        btnNumber3.setOnClickListener(this)
        btnNumber4.setOnClickListener(this)
        btnNumber5.setOnClickListener(this)
        btnNumber6.setOnClickListener(this)
        btnNumber7.setOnClickListener(this)
        btnNumber8.setOnClickListener(this)
        btnNumber9.setOnClickListener(this)
        btnNumber10.setOnClickListener(this)
        btnNumber11.setOnClickListener(this)
        btnNumber12.setOnClickListener(this)
        btnPlus.setOnClickListener(this)
        btnMinus.setOnClickListener(this)
        btnMultiplication.setOnClickListener(this)
        btnDivision.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v!!.id)
        {
            R.id.btnNumber1-> onNumberClick(1,v.isActivated,v)
            R.id.btnNumber2-> onNumberClick(2,v.isActivated,v)
            R.id.btnNumber3-> onNumberClick(3,v.isActivated,v)
            R.id.btnNumber4-> onNumberClick(4,v.isActivated,v)
            R.id.btnNumber5-> onNumberClick(5,v.isActivated,v)
            R.id.btnNumber6-> onNumberClick(6,v.isActivated,v)
            R.id.btnNumber7-> onNumberClick(7,v.isActivated,v)
            R.id.btnNumber8-> onNumberClick(8,v.isActivated,v)
            R.id.btnNumber9-> onNumberClick(9,v.isActivated,v)
            R.id.btnNumber10-> onNumberClick(10,v.isActivated,v)
            R.id.btnNumber11-> onNumberClick(11,v.isActivated,v)
            R.id.btnNumber12-> onNumberClick(12,v.isActivated,v)
            R.id.btnPlus->onOperatorClick(Constants.ADD,v.isActivated,v)
            R.id.btnMinus->onOperatorClick(Constants.SUBTRACT,v.isActivated,v)
            R.id.btnMultiplication->onOperatorClick(Constants.MULTIPLY,v.isActivated,v)
            R.id.btnDivision->onOperatorClick(Constants.DIVIDE,v.isActivated,v)
        }
    }
    private fun onNumberClick(number : Int,activated : Boolean,view : View) {
        view.isActivated = !activated
        if(!activated)
        {
            //add number to array
            selectedNumberList.add(number)
        }
        else
        {
            //remove number from array
            selectedNumberList.remove(number)
        }
        Log.d(Constants.LOG_TAG,"Number $selectedNumberList")
    }

    private fun onOperatorClick(operation: String, activated: Boolean, v: View) {
        v.isActivated = !activated
        if(!activated)
            selectedOperation.add(operation)
        else
            selectedOperation.remove(operation)
        Log.d(Constants.LOG_TAG,selectedOperation.toString())
    }


}

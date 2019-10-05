package com.carveniche.begalileo.ui.fragments.question_template


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.carveniche.begalileo.R
import com.carveniche.begalileo.util.Constants

/**
 * A simple [Fragment] subclass.
 */
class KeyingTypeQuestionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_keying_type_question, container, false)
    }


    companion object
    {
        var TAG = Constants.QUESTION_TYPE_KEYING
    }

}

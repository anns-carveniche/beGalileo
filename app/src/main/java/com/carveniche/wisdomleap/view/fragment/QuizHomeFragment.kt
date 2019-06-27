package com.carveniche.wisdomleap.view.fragment


import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import androidx.fragment.app.DialogFragment

import com.carveniche.wisdomleap.R
import com.carveniche.wisdomleap.adapter.QuizCategroyAdapter
import com.carveniche.wisdomleap.contract.QuizHomeContract
import com.carveniche.wisdomleap.di.component.DaggerFragmentComponent
import com.carveniche.wisdomleap.di.module.ContextModule
import com.carveniche.wisdomleap.di.module.FragmentModule
import com.carveniche.wisdomleap.di.module.SharedPreferenceModule
import com.carveniche.wisdomleap.model.MySharedPreferences
import com.carveniche.wisdomleap.model.QuizCategoryModel
import com.carveniche.wisdomleap.util.Constants
import com.carveniche.wisdomleap.util.showLongToast
import com.carveniche.wisdomleap.view.activity.MultiPlayerQuizActivity
import com.carveniche.wisdomleap.view.activity.QuizActivity
import kotlinx.android.synthetic.main.dialog_quiz_levels.*
import kotlinx.android.synthetic.main.dialog_quiz_levels.view.*
import kotlinx.android.synthetic.main.fragment_quiz_home.*

import javax.inject.Inject


class QuizHomeFragment : Fragment(),QuizHomeContract.View {

    private lateinit var rootView: View
    @Inject lateinit var presenter : QuizHomeContract.Presenter
    private var categoryId = 0
    private var mquizLevel = Constants.EASY
    private var studentId = 0
    private var userCoins = 0
    private var quizCategory  = mutableListOf<QuizCategoryModel>()
    @Inject lateinit var mySharedPreferences: MySharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_quiz_home, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attach(this)
        presenter.subscribe()
        presenter.loadUserCoins(studentId)
        displayData()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        injectDependency()
        studentId= mySharedPreferences.getIntData(Constants.STUDENT_ID)
        initLoadDatas()

    }
    override fun onCoinsLoad(coins: Int) {
        userCoins = coins
        Log.d(Constants.LOG_TAG,"Coins $userCoins")
    }


    private fun displayData() {
        gvQuizCateg.adapter = QuizCategroyAdapter(context!!,quizCategory,this)
    }

    private fun initLoadDatas() {
        quizCategory.add(QuizCategoryModel(9,"General Knowledge",R.drawable.ic_quiz_gk))
        quizCategory.add(QuizCategoryModel(17,"Nature",R.drawable.ic_quiz_nature))
        quizCategory.add(QuizCategoryModel(18,"Computer",R.drawable.ic_quiz_computer))
      //  quizCategory.add(QuizCategoryModel(19,"Mathematics",R.drawable.ic_quiz_maths))
        quizCategory.add(QuizCategoryModel(22,"Geography",R.drawable.ic_quiz_geography))
        quizCategory.add(QuizCategoryModel(23,"History",R.drawable.ic_quiz_history))
        quizCategory.add(QuizCategoryModel(27,"Animals",R.drawable.ic_quiz_animals))
      //  quizCategory.add(QuizCategoryModel(30,"Science",R.drawable.ic_quiz_science))
    }

    override fun showProgress(show: Boolean) {

    }
    companion object {
        const val TAG = "QuizHomeFragment"
    }
    private fun injectDependency() {
        val fragmentComponent = DaggerFragmentComponent.builder()
            .fragmentModule(FragmentModule())
            .sharedPreferenceModule(SharedPreferenceModule())
            .contextModule(ContextModule(context!!))
            .build()
        fragmentComponent.inject(this)
    }
    override fun onCategorySelected(categoryId: Int) {
        if(userCoins==0)
        {
            showLongToast("You do not have enough coins",context!!)
            return
        }
        this.categoryId = categoryId
        var ft = childFragmentManager.beginTransaction()
        var prev = childFragmentManager.findFragmentByTag("dialog")
        if(prev!=null)
            ft.remove(prev)
        ft.addToBackStack(null)

        var dialogFragment = QuizDifficultyDialog(this)
        dialogFragment.show(ft,"dialog")


    }

    override fun openQuizQuestionActivity(level: String) {
        Log.d(Constants.LOG_TAG,"$categoryId -- $level")
        var intent = Intent(context!!,QuizActivity::class.java)
        intent.putExtra(Constants.QUIZ_LEVEL,level)
        intent.putExtra(Constants.QUIZ_CATEGORY,categoryId)
        startActivity(intent)
    }

    override fun onPause() {
        super.onPause()
        presenter.unSubscribe()
    }
    override fun openMultiPlayerQuiz(level: String) {
        Log.d(Constants.LOG_TAG,"$categoryId -- $level")
        var intent = Intent(context!!,MultiPlayerQuizActivity::class.java)
        intent.putExtra(Constants.QUIZ_LEVEL,level)
        intent.putExtra(Constants.QUIZ_CATEGORY,categoryId)
        intent.putExtra(Constants.USER_COINS,userCoins)
        startActivity(intent)
    }

}

class QuizDifficultyDialog(private var quizHomeView : QuizHomeContract.View) : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme);
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       var view = inflater.inflate(R.layout.dialog_quiz_levels,container,false)

        view.btn_single_player.setOnClickListener {
            var selectedId = view.rg_quiz_level.checkedRadioButtonId
            quizLevelSelected(selectedId,true)
        }
        view.btn_multi_player.setOnClickListener {
            var selectedId = view.rg_quiz_level.checkedRadioButtonId
            quizLevelSelected(selectedId,false)
        }

        return view
    }

    fun quizLevelSelected(selected : Int,isSingle : Boolean)
    {
        if(selected==-1)
        {
            showLongToast("Please select the difficulty level",context!!)
        }
        else
        {
            var radioButton = view!!.findViewById<RadioButton>(selected)
            if(isSingle)
                 quizHomeView.openQuizQuestionActivity(radioButton.text.toString().toLowerCase())
            else
                quizHomeView.openMultiPlayerQuiz(radioButton.text.toString().toLowerCase())
        }
    }

}

package com.carveniche.wisdomleap.view.fragment


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment

import com.carveniche.wisdomleap.R
import com.carveniche.wisdomleap.adapter.DashboardSubjectListAdapter
import com.carveniche.wisdomleap.contract.DashboardContract
import com.carveniche.wisdomleap.di.component.DaggerFragmentComponent
import com.carveniche.wisdomleap.di.module.ContextModule
import com.carveniche.wisdomleap.di.module.FragmentModule
import com.carveniche.wisdomleap.di.module.SharedPreferenceModule
import com.carveniche.wisdomleap.model.MySharedPreferences
import com.carveniche.wisdomleap.model.SubjectListModel
import com.carveniche.wisdomleap.util.Constants
import com.carveniche.wisdomleap.util.isFirstLaunchToday
import com.carveniche.wisdomleap.util.showLoadingProgress
import com.carveniche.wisdomleap.view.activity.SubjectActivity
import com.google.android.libraries.places.internal.i
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.layout_progressbar.*
import javax.inject.Inject


class DashboardFragment : Fragment(),DashboardContract.View {

    private lateinit var rootView : View
    @Inject lateinit var presenter : DashboardContract.Presenter
    @Inject lateinit var mySharedPreferences: MySharedPreferences
    private  var studentId =0
    private lateinit var subjectListData : SubjectListModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_dashboard, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attach(this)
        presenter.subscribe()
        initUI()
        if(isFirstLaunchToday(mySharedPreferences))
        {
            showDailyMessages()
        }
        studentId = mySharedPreferences.getIntData(Constants.STUDENT_ID)
        presenter.loadDashboardData(studentId)
    }

    private fun showDailyMessages() {
        var ft = childFragmentManager.beginTransaction()
        var prev = childFragmentManager.findFragmentByTag("dialog")
        if(prev!=null)
            ft.remove(prev)
        ft.addToBackStack(null)

        var dialogFragment = DailyMessageDialogFragment()
        dialogFragment.show(ft,"dialog")
    }

    private fun initUI() {

    }

    private fun injectDependency() {
        val fragmentComponent = DaggerFragmentComponent.builder()
            .fragmentModule(FragmentModule())
            .sharedPreferenceModule(SharedPreferenceModule())
            .contextModule(ContextModule(context!!))
            .build()
        fragmentComponent.inject(this)

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
    }

    companion object {
        const val TAG = "DashboardFragment"
    }
    override fun showProgress(show: Boolean) {
        showLoadingProgress(progressBar,show)
    }
    override fun onLoadDataSucess(subjectListModel: SubjectListModel) {
        this.subjectListData = subjectListModel
        assignSubjectDatas()

    }

    private fun assignSubjectDatas() {
        var size = subjectListData.course_details.size

        var total = size;
        var column = 2
        var row = total / column;
        gl_subject_container.columnCount = column;
        gl_subject_container.rowCount = row+1
        var i = 0
        var c = 0
        var r = 0
        while (i < total)
        {
            if (c == column)
            {
                c = 0
                r++
            }
            val oImageView = ImageView(context)
            oImageView.setImageResource(R.drawable.ic_quiz_computer)
            oImageView.setBackgroundColor(Color.GREEN)
           oImageView.layoutParams = ViewGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
            var params = GridLayout.LayoutParams()
            params.height = GridLayout.LayoutParams.WRAP_CONTENT
            params.width = GridLayout.LayoutParams.WRAP_CONTENT
            params.setGravity(Gravity.TOP or Gravity.CENTER)
            params.columnSpec = GridLayout.spec(c,1)
            params.rowSpec = GridLayout.spec(r,1)

            gl_subject_container.addView(oImageView,params)

            i++
            c++
        }
    }

    override fun onLoadDataFailed(msg: String) {

    }



}


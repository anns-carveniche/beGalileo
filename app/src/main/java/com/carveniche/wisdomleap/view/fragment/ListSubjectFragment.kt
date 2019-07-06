package com.carveniche.wisdomleap.view.fragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.LinearLayoutManager
import com.carveniche.wisdomleap.R
import com.carveniche.wisdomleap.adapter.SubjectListAdapter
import com.carveniche.wisdomleap.contract.ListSubjctContract
import com.carveniche.wisdomleap.di.component.DaggerFragmentComponent
import com.carveniche.wisdomleap.di.module.ContextModule
import com.carveniche.wisdomleap.di.module.FragmentModule
import com.carveniche.wisdomleap.di.module.SharedPreferenceModule
import com.carveniche.wisdomleap.model.CourseDetail
import com.carveniche.wisdomleap.model.MySharedPreferences
import com.carveniche.wisdomleap.model.SubjectListModel
import com.carveniche.wisdomleap.util.Constants
import com.carveniche.wisdomleap.util.showLoadingProgress
import com.carveniche.wisdomleap.util.showLongToast
import com.carveniche.wisdomleap.view.activity.SubjectActivity
import kotlinx.android.synthetic.main.fragment_list_subject.*
import kotlinx.android.synthetic.main.layout_progressbar.*


import javax.inject.Inject


class ListSubjectFragment : Fragment(),ListSubjctContract.View {

    lateinit var rootView: View
    @Inject lateinit var presenter : ListSubjctContract.Presenter
    lateinit var subjectActivity : SubjectActivity
    private lateinit var courseDetail: List<CourseDetail>
    @Inject lateinit var mySharedPreferences: MySharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
        subjectActivity = activity as SubjectActivity
    }
    private fun injectDependency() {
        val fragmentComponent = DaggerFragmentComponent.builder()
            .fragmentModule(FragmentModule())
            .sharedPreferenceModule(SharedPreferenceModule())
            .contextModule(ContextModule(context!!))
            .build()
        fragmentComponent.inject(this)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_list_subject, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }
    override fun onSubjectLoadSucess(subjectListModel: SubjectListModel) {
        this.courseDetail = subjectListModel.course_details
       // gvSubject.adapter = SubjectListAdapter(context!!,courseDetail,subjectActivity)
    }

    override fun onSubjectLoadFailed(msg: String) {
        Log.d(Constants.LOG_TAG,msg)
        showLongToast(msg,context!!)
    }



    private fun initUI() {
        presenter.attach(this)
        presenter.subscribe()
        presenter.loadSubjectData(mySharedPreferences.getIntData(Constants.STUDENT_ID))
    }

    override fun showProgress(show: Boolean) {
        showLoadingProgress(progressBar,show)
    }

    companion object {
        const val TAG = "ListSubjectFragment"
    }

}

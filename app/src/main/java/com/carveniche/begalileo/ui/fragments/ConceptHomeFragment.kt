package com.carveniche.begalileo.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.carveniche.begalileo.util.Constants
import com.carveniche.begalileo.R
import com.carveniche.begalileo.adapters.ConceptBoardListAdapter
import com.carveniche.begalileo.adapters.ConceptGradesListAdapter
import com.carveniche.begalileo.api.ApiServiceInterface
import com.carveniche.begalileo.di.component.DaggerFragmentComponent
import com.carveniche.begalileo.di.module.ContextModule
import com.carveniche.begalileo.di.module.Fragmentmodule
import com.carveniche.begalileo.models.GradeBoardModel
import com.carveniche.begalileo.contract.BaseContract
import com.carveniche.begalileo.contract.ConceptContract
import com.carveniche.begalileo.util.showLongToast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_concept_home.*

import kotlinx.android.synthetic.main.layout_progressbar.*
import javax.inject.Inject








class ConceptHomeFragment :Fragment(), ConceptHomeContract.View {



    @Inject lateinit var presenter: ConceptHomeContract.Presenter
    lateinit var conceptView : ConceptContract.View
    lateinit var rootView: View
    private lateinit var gradeBoardModel: GradeBoardModel
    private lateinit var boardListAdapter: ConceptBoardListAdapter
    private lateinit var gradeListAdapter: ConceptGradesListAdapter
    private var mBoardPos = 0
    private var mGradeId = 0
    private var mBoardId = 0


    fun newInstance(): ConceptHomeFragment
    {
        return ConceptHomeFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
        conceptView = activity as ConceptContract.View
    }



    private fun initUI() {

    }

    private fun injectDependency() {
        val fragmentComponent = DaggerFragmentComponent.builder()
            .fragmentmodule(Fragmentmodule())
            .contextModule(ContextModule(context!!))
            .build()
        fragmentComponent.inject(this)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView =inflater.inflate(R.layout.fragment_concept_home,container,false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUI()
        presenter.attach(this)

    }




    override fun showProgress(show: Boolean) {
        progressBar.bringToFront()
        if(show)
            progressBar.visibility = View.VISIBLE
        else
            progressBar.visibility = View.GONE
    }
    override fun loadDataSucess(gradeBoard: GradeBoardModel) {
        this.gradeBoardModel = gradeBoard
        boardListAdapter = ConceptBoardListAdapter(context!!,gradeBoardModel.grade_boards,0,this)
        lvBoard.layoutManager = LinearLayoutManager(context)
        lvGrade.layoutManager = LinearLayoutManager(context)
        lvBoard.adapter = boardListAdapter
        gradeListAdapter = ConceptGradesListAdapter(context!!,gradeBoardModel.grade_boards[0].grade_details,this)
        lvGrade.adapter = gradeListAdapter
        mBoardId = gradeBoardModel.grade_boards[0].id

    }

    override fun onGradeClick(pos: Int, view: View) {
        lvGrade.layoutManager!!.scrollToPosition(pos)
        mGradeId = gradeBoardModel.grade_boards[mBoardPos].grade_details[pos].id
        conceptView.showConceptList(mBoardId,mGradeId)

    }


    override fun onBoardClick(pos: Int, view: View) {
        mBoardPos = pos
        mBoardId = gradeBoardModel.grade_boards[pos].id
        lvBoard.layoutManager!!.scrollToPosition(pos)
        boardListAdapter = ConceptBoardListAdapter(context!!,gradeBoardModel.grade_boards,pos,this)
        lvBoard.adapter = boardListAdapter
        gradeListAdapter = ConceptGradesListAdapter(context!!,gradeBoardModel.grade_boards[pos].grade_details,this)
        lvGrade.adapter = gradeListAdapter

    }

    override fun loadDataError(msg: String) {
        showLongToast(msg,context!!)
    }
    companion object {
        val TAG: String = "ConceptHomeFragment"
    }

    override fun onResume() {
        presenter.loadData()
        super.onResume()
    }

    override fun onPause() {
        Log.d(Constants.LOG_TAG,"Pause $mBoardId")
        super.onPause()
    }

}

class ConceptHomeContract
{
    interface View : BaseContract.View
    {
       fun loadDataSucess(gradeBoard: GradeBoardModel)
        fun loadDataError(msg : String)
        fun onGradeClick(pos:Int,view: android.view.View)
        fun onBoardClick(pos:Int,view: android.view.View)
    }
    interface Presenter : BaseContract.Presenter<View>
    {
        fun loadData()
    }
}


class ConceptHomePresenter : ConceptHomeContract.Presenter
{


    lateinit var view : ConceptHomeContract.View
    private val subscriptions = CompositeDisposable()
    private var apiInterface = ApiServiceInterface.create()
    override fun subscribe() {

    }

    override fun unsubscrbe() {
        subscriptions.clear()
    }

    override fun attach(view: ConceptHomeContract.View) {
      this.view = view
    }
    override fun loadData() {
        view.showProgress(true)
        val subscribe = apiInterface.getGradeBoardDetails().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if(it.status)
                {
                    view.loadDataSucess(it)
                    view.showProgress(false)
                }
                else
                {
                    view.showProgress(false)
                    view.loadDataError(Constants.UNKNOWN_ERROR)

                }

            },{
                    error->
                view.showProgress(false)
                view.loadDataError(error.localizedMessage)
            }
            )

    }

}

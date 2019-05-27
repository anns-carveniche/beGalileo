package com.carveniche.begalileo.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.azoft.carousellayoutmanager.CarouselLayoutManager
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener
import com.azoft.carousellayoutmanager.CenterScrollListener
import com.carveniche.begalileo.util.Constants
import com.carveniche.begalileo.R
import com.carveniche.begalileo.adapters.ConceptListAdapter
import com.carveniche.begalileo.api.ApiServiceInterface
import com.carveniche.begalileo.di.component.DaggerFragmentComponent
import com.carveniche.begalileo.di.module.ContextModule
import com.carveniche.begalileo.di.module.Fragmentmodule
import com.carveniche.begalileo.models.ConceptListModel
import com.carveniche.begalileo.contract.BaseContract
import com.carveniche.begalileo.contract.ConceptContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_concept_list.*
import kotlinx.android.synthetic.main.layout_progressbar.*
import javax.inject.Inject



class ConceptListFragment :Fragment(), ConceptListContract.View {

    @Inject lateinit var presenter: ConceptListContract.Presenter
    lateinit var conceptView : ConceptContract.View
    lateinit var rootView: View
    private var mGradeId = 0
    private var mBoardId = 0



    fun newInstance(): ConceptListFragment
    {
        return ConceptListFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
        conceptView = activity as ConceptContract.View
    }
    private fun injectDependency() {
        val fragmentComponent = DaggerFragmentComponent.builder()
            .fragmentmodule(Fragmentmodule())
            .contextModule(ContextModule(context!!))
            .build()

        fragmentComponent.inject(this)

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView =inflater.inflate(R.layout.fragment_concept_list,container,false)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       initUI()
        presenter.attach(this)
        Log.d(Constants.LOG_TAG,"Board id $mBoardId Grade id $mGradeId")
        presenter.loadData(mBoardId,mGradeId)

    }

    private fun initUI() {
        var carouselLayoutManager = CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL)
        carouselLayoutManager.setPostLayoutListener(CarouselZoomPostLayoutListener())
        rvConceptList.layoutManager = carouselLayoutManager
        rvConceptList.hasFixedSize()

        mBoardId = arguments!!.getInt(Constants.BOARD_ID)
        mGradeId = arguments!!.getInt(Constants.GRADE_ID)

    }

    override fun showProgress(show: Boolean) {
        progressBar.bringToFront()
        if(show)
            progressBar.visibility = View.VISIBLE
        else
            progressBar.visibility = View.GONE
    }

    companion object {
        const val TAG: String = "ConceptListFragment"
    }

    override fun loadDataSucess(conceptList: ConceptListModel) {
        rvConceptList.adapter = ConceptListAdapter(context!!,conceptList.grade_concepts[0].sub_concepts,this)
        rvConceptList.addOnScrollListener(CenterScrollListener())
    }

    override fun loadDataFailed(msg: String) {
        Log.d(Constants.LOG_TAG,"Data Failed $msg")
    }



}

class ConceptListContract
{
    interface View : BaseContract.View
    {
        fun loadDataSucess(conceptList : ConceptListModel)
        fun loadDataFailed(msg : String)
    }
    interface Presenter : BaseContract.Presenter<View>
    {
        fun loadData(boardId : Int,gradeId: Int)
    }
}


class ConceptListPresenter : ConceptListContract.Presenter
{

    lateinit var view : ConceptListContract.View
    private val subscriptions = CompositeDisposable()
    private var apiInterface = ApiServiceInterface.create()
    override fun subscribe() {

    }

    override fun unsubscrbe() {
        subscriptions.clear()
    }

    override fun attach(view: ConceptListContract.View) {
      this.view = view
    }
    override fun loadData(boardId: Int, gradeId: Int) {
        view.showProgress(true)
        var subscribe = apiInterface.getConceptList(gradeId,boardId).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

                if(it.status)
                {
                    view.showProgress(false)
                   view.loadDataSucess(it)
                }
                else
                {
                    view.showProgress(false)
                    view.loadDataFailed("Something went wrong please try again")
                }
            },{
                view.showProgress(false)
                    view.loadDataFailed(it.localizedMessage)
            })
    }
}

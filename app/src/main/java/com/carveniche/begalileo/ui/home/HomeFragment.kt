package com.carveniche.begalileo.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.azoft.carousellayoutmanager.CarouselLayoutManager
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener
import com.carveniche.begalileo.Constants
import com.carveniche.begalileo.R
import com.carveniche.begalileo.adapters.HomeItemListAdapter
import com.carveniche.begalileo.di.component.DaggerFragmentComponent
import com.carveniche.begalileo.di.module.ContextModule
import com.carveniche.begalileo.di.module.Fragmentmodule
import com.carveniche.begalileo.ui.addChild.AddChildActivity
import com.carveniche.begalileo.ui.concepts.ConceptActivity
import com.carveniche.begalileo.ui.main.MainContract
import com.carveniche.begalileo.ui.userLocation.UserLocationActivity
import kotlinx.android.synthetic.main.fragment_home.*

import javax.inject.Inject




class HomeFragment : Fragment(), HomeContract.View {


    @Inject lateinit var presenter : HomeContract.Presenter
    lateinit var mainPresenter: MainContract.View
    var carouselLayoutManager = CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL)


    private lateinit var rootView: View

    fun newInstance(): HomeFragment{
        return HomeFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
        mainPresenter = activity as MainContract.View

    }

    private fun injectDependency() {
        val fragmentComponent = DaggerFragmentComponent.builder()
            .fragmentmodule(Fragmentmodule())
            .contextModule(ContextModule(context!!))
            .build()
        fragmentComponent.inject(this)
    }
    override fun onItemClick(position: Int) {
        when(Constants.HOME_ITEM_NAME_LIST[position])
        {
            Constants.ADD_CHILD->openAddChild()
            Constants.FIND_CENTER->openFindCenter()
            Constants.CURRICULAM->openCurriculum()
            Constants.MY_PROFILE->openMyProfile()
        }
    }

    private fun openMyProfile() {
        Log.d(Constants.LOG_TAG,Constants.MY_PROFILE)
    }

    private fun openCurriculum() {
         var intentCurriculum = Intent(context,ConceptActivity::class.java)
        startActivity(intentCurriculum)
    }

    private fun openFindCenter() {
        var intentFindCenter = Intent(context,UserLocationActivity::class.java)
        startActivity(intentFindCenter)
    }

    private fun openAddChild() {
       var intentAddChild = Intent(context,AddChildActivity::class.java)
        startActivity(intentAddChild)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView =inflater.inflate(R.layout.fragment_home,container,false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         presenter.attach(this)
         presenter.subscribe()
        initUI()
   }

    private fun initUI() {
        carouselLayoutManager.setPostLayoutListener(CarouselZoomPostLayoutListener())
        rvHomeList.layoutManager = carouselLayoutManager
        rvHomeList.hasFixedSize()
        rvHomeList.adapter = HomeItemListAdapter(context!!,this)
    }

    override fun showProgress(boolean: Boolean) {
    }

    override fun loadMessageSuccess(message: String) {

    }
    companion object {
        const val TAG: String = "HomeFragment"
    }
}
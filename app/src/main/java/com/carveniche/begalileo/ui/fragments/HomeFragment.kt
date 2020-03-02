package com.carveniche.begalileo.ui.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.azoft.carousellayoutmanager.CarouselLayoutManager
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener
import com.carveniche.begalileo.util.Constants
import com.carveniche.begalileo.R
import com.carveniche.begalileo.adapters.HomeItemListAdapter
import com.carveniche.begalileo.di.component.DaggerFragmentComponent
import com.carveniche.begalileo.di.module.ContextModule
import com.carveniche.begalileo.di.module.Fragmentmodule
import com.carveniche.begalileo.contract.HomeContract
import com.carveniche.begalileo.contract.MainContract
import com.carveniche.begalileo.models.DashboardDataModel
import com.carveniche.begalileo.models.MySharedPreferences
import com.carveniche.begalileo.ui.activities.*
import com.carveniche.begalileo.util.showLongToast
import com.carveniche.begalileo.util.showShortToast
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.layout_progressbar.*

import javax.inject.Inject




class HomeFragment : Fragment(), HomeContract.View {


    @Inject lateinit var presenter : HomeContract.Presenter
    @Inject lateinit var mySharedPreferences: MySharedPreferences
    lateinit var mainPresenter: MainContract.View
    var carouselLayoutManager = CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL)
    var mParentID = 0
    var mStudentID = 0

    private lateinit var rootView: View

    fun newInstance(): HomeFragment {
        return HomeFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
        mainPresenter = activity as MainContract.View

        mParentID = mySharedPreferences.getIntData(Constants.PARENT_ID)





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
            //Constants.ADD_CHILD->openAddChild()
            //Constants.FIND_CENTER->openFindCenter()
           // Constants.CURRICULAM->openCurriculum()
            Constants.REWARDS->openStudentRewardDatas(Constants.REWARDS)
            Constants.LEADER_BOARD->openStudentRewardDatas(Constants.LEADER_BOARD)
            Constants.BADGES->openStudentRewardDatas(Constants.BADGES)
            Constants.KID_PROGRESS->openStudentRewardDatas(Constants.KID_PROGRESS)
            Constants.LIVE_VIDEO->openMyProfile()
            Constants.PRACTICE_MATH->openPracticeMathActivity()
        }
    }

    override fun onDashboardLoadSuccess(dashboardDataModel: DashboardDataModel) {
        if(dashboardDataModel.students.isNotEmpty())
        {
            mySharedPreferences.putIntData(Constants.STUDENT_ID,dashboardDataModel.students[0].student_id)
            this.mStudentID = dashboardDataModel.students[0].student_id
        }

        else
            showShortToast("No students registered in this account",context!!)
        Log.d(Constants.LOG_TAG,dashboardDataModel.toString())
    }

    override fun onDashboardLoadFailed(msg: String) {
        showShortToast("Unable to fetch data please try again",context!!)
    }

    private fun openMyProfile() {
        showLongToast("Live Class is Coming Soon with great updates",context!!)
//       var intentLiveVideo = Intent(context,LiveClassActivity::class.java)
//        startActivity(intentLiveVideo)
    }

    private fun openStudentRewardDatas(data : String){
        if(mStudentID==0)
        {
            showShortToast("Please add child in your account to view datas",context!!)
            return
        }

               var intent = Intent(context,StudentRewardActivity::class.java)
                intent.putExtra(Constants.REWARD_TYPE,data)
                intent.putExtra(Constants.STUDENT_ID,mStudentID)
                startActivity(intent)
    }


    private fun openCurriculum() {
         var intentCurriculum = Intent(context, ConceptActivity::class.java)
        startActivity(intentCurriculum)
    }

    private fun openFindCenter() {
        var intentFindCenter = Intent(context, UserLocationActivity::class.java)
        startActivity(intentFindCenter)
    }

    private fun openAddChild() {
       var intentAddChild = Intent(context, AddChildActivity::class.java)
        startActivity(intentAddChild)
    }

    private fun openPracticeMathActivity()
    {
        var intentPracticeMath = Intent(context,SpeedMathActivity::class.java)
        startActivity(intentPracticeMath)
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

    private fun confirmLogOut(){
        val builder = AlertDialog.Builder(context!!)

        // Set the alert dialog title
        builder.setTitle("Log Out")

        // Display a message on alert dialog
        builder.setMessage("Are you want to log out?")

        // Set a positive button and its click listener on alert dialog
        builder.setPositiveButton("YES"){dialog, which ->
            onLogOutSelected()
        }


        // Display a negative button on alert dialog
        builder.setNegativeButton("No"){dialog,which ->
          dialog.dismiss()
        }



        // Finally, make the alert dialog using builder
        val dialog: AlertDialog = builder.create()

        // Display the alert dialog on app interface
        dialog.show()
    }

    private fun onLogOutSelected(){
        mySharedPreferences.clearAllDatas()
        var intent  = Intent(context!!,LoginActivity::class.java)
        startActivity(intent)
    }

    private fun initUI() {
        presenter.loadDashboardDatas(mParentID)
        tv_scroll.isSelected = true
        carouselLayoutManager.setPostLayoutListener(CarouselZoomPostLayoutListener())
        rvHomeList.layoutManager = carouselLayoutManager
        rvHomeList.hasFixedSize()
        rvHomeList.adapter = HomeItemListAdapter(context!!,this)
        iv_log_out.setOnClickListener {
            confirmLogOut()
        }
    }

    override fun showProgress(show: Boolean) {
//        progressBar.bringToFront()
//        if(show)
//            progressBar.visibility = View.VISIBLE
//        else
//            progressBar.visibility = View.GONE
    }

    override fun loadMessageSuccess(message: String) {

    }
    companion object {
        const val TAG: String = "HomeFragment"
    }
}
package com.carveniche.begalileo.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.carveniche.begalileo.util.Constants
import com.carveniche.begalileo.R
import com.carveniche.begalileo.adapters.GradesListAdapter
import com.carveniche.begalileo.adapters.BoardListAdapter
import com.carveniche.begalileo.di.component.DaggerActivityComponent
import com.carveniche.begalileo.di.module.ActivityModule
import com.carveniche.begalileo.di.module.ContextModule
import com.carveniche.begalileo.di.module.SharedPreferenceModule
import com.carveniche.begalileo.models.GradeBoardModel
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.layout_progressbar.*
import javax.inject.Inject
import android.widget.ArrayAdapter
import com.carveniche.begalileo.contract.AddChildContract
import com.carveniche.begalileo.iInterfaces.IGradeBoardListener
import com.carveniche.begalileo.models.AddChildModel
import com.carveniche.begalileo.models.MySharedPreferences
import com.carveniche.begalileo.util.showLongToast
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable


class AddChildActivity : AppCompatActivity(), AddChildContract.View,IGradeBoardListener {



    @Inject  lateinit var presenter: AddChildContract.Presenter
    private lateinit var gradeBoardModel: GradeBoardModel
    private lateinit var gradesListAdapter : GradesListAdapter
    private lateinit var boardListAdapter : BoardListAdapter
    @Inject lateinit var mySharedPreferences: MySharedPreferences
    private var mParentId = 0
    private var mFirstName = ""
    private var mLastName = ""
    private var mGender = ""
    private var mGrade = 0
    private var mBoard = 0
    private var mGoal = ""
    private var mSchoolName = ""
    private var mSchoolCity = ""

    private var boardPosClick = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        clNameContainer.visibility = View.VISIBLE
        injectDependency()
        initView()
        initUI()
        mParentId = mySharedPreferences.getIntData(Constants.PARENT_ID)
        Log.d(Constants.LOG_TAG,mySharedPreferences.getIntData(Constants.PARENT_ID).toString())
    }

    private fun initView() {
     btnNameSubmit.setOnClickListener {
         mFirstName = edFirstName.text.toString()
         mLastName = edLastName.text.toString()
         Log.d(Constants.LOG_TAG, "$mFirstName $mLastName")
         viewflipper.showNext()
     }
        ivBoyAvatar.setOnClickListener {
            mGender = "m"
            Log.d(Constants.LOG_TAG,"Gender $mGender")
            viewflipper.showNext()
        }
        ivGirlAvatar.setOnClickListener {
            mGender = "f"
            Log.d(Constants.LOG_TAG,"Gender $mGender")
            viewflipper.showNext()
        }

        gvGoal.setOnItemClickListener { parent, view, position, id ->
            mGoal = gradeBoardModel.goals[position]
            Log.d(Constants.LOG_TAG,mGoal)
            viewflipper.showNext()
        }

     btnSchoolSubmit.setOnClickListener {
         mSchoolName = edSchoolName.text.toString()
         mSchoolCity = edSchoolCity.text.toString()
         presenter.addChild(mParentId,mFirstName,mLastName,mGender,mGrade,mGoal,mBoard,mSchoolName,mSchoolCity)
     }

    }
    override fun schoolName(): Observable<CharSequence> {
        return RxTextView.textChanges(edSchoolName).skipInitialValue()
    }

    override fun schoolCity(): Observable<CharSequence> {
        return RxTextView.textChanges(edSchoolCity).skipInitialValue()
    }

    override fun updateSchoolSubmitButtonState(state: Boolean) {
        btnSchoolSubmit.isEnabled = state
    }
    private fun injectDependency() {
        val activityComponent = DaggerActivityComponent.builder()
            .activityModule(ActivityModule(this))
            .sharedPreferenceModule(SharedPreferenceModule())
            .contextModule(ContextModule(this))
            .build()
        activityComponent.inject(this)
    }
    private fun initUI() {

        presenter.attach(this)
        presenter.loadData()
    }
    override fun addChildSuccess(childModel: AddChildModel) {
            val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun addChildFailed(msg: String) {
        showLongToast(msg,this)
        Log.d(Constants.LOG_TAG,msg)
    }
    override fun loadDataSucess(gradeBoardModel: GradeBoardModel) {
       this.gradeBoardModel = gradeBoardModel
         boardListAdapter = BoardListAdapter(this, gradeBoardModel.grade_boards,this)
        gvBoards.adapter = boardListAdapter
         gradesListAdapter = GradesListAdapter(this,gradeBoardModel.grade_boards[0].grade_details,this)
        gvGrades.adapter = gradesListAdapter

        gvGoal.adapter = ArrayAdapter<String>(
            this, R.layout.list_item_goal, gradeBoardModel.goals
        )
    }
    override fun name(): Observable<CharSequence> {
       return RxTextView.textChanges(edFirstName).skipInitialValue()
    }

    override fun updateNameButtonState(state: Boolean) {
        btnNameSubmit.isEnabled = state
    }
    override fun loadDataError(msg: String) {
        Log.d(Constants.LOG_TAG, "Data Error $msg")
    }

    override fun showProgress(show: Boolean) {
        progressBar.bringToFront()
        if(show)
            progressBar.visibility = View.VISIBLE
        else
            progressBar.visibility = View.GONE
    }

    override fun onGradeClick(id: Int) {
        viewflipper.showNext()
        boardPosClick = id
        mBoard = gradeBoardModel.grade_boards[id].id
        gradesListAdapter = GradesListAdapter(this, gradeBoardModel.grade_boards[id].grade_details,this)
        gvGrades.adapter = gradesListAdapter
    }

    override fun onBoardClick(id: Int) {
        viewflipper.showNext()
        mGrade = gradeBoardModel.grade_boards[boardPosClick].grade_details[id].id
    }
}

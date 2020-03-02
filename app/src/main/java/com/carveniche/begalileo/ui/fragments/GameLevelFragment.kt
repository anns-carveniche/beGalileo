package com.carveniche.begalileo.ui.fragments


import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.carveniche.begalileo.util.Constants
import com.carveniche.begalileo.R
import com.carveniche.begalileo.adapters.GameLevelAdapter
import com.carveniche.begalileo.api.ApiServiceInterface
import com.carveniche.begalileo.di.component.DaggerFragmentComponent
import com.carveniche.begalileo.di.module.ContextModule
import com.carveniche.begalileo.di.module.Fragmentmodule
import com.carveniche.begalileo.models.GameLevelModel
import com.carveniche.begalileo.models.Level
import com.carveniche.begalileo.contract.BaseContract
import com.carveniche.begalileo.contract.SpeedMathContract
import com.carveniche.begalileo.util.showLongToast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_game_level.*
import kotlinx.android.synthetic.main.layout_progressbar.*
import javax.inject.Inject

import android.view.*

import android.widget.TextView



class GameLevelFragment : Fragment(), GameLevelContractor.View {

    @Inject
    lateinit var presenter: GameLevelContractor.Presenter
    private lateinit var rootView: View
    private lateinit var listLevel : List<Level>
    lateinit var speedMathView : SpeedMathContract.View




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
        speedMathView = activity as SpeedMathContract.View
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_game_level, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attach(this)
        presenter.loadLevelDatas(269)
        gvLevelContainer.setOnItemClickListener { parent, view, position, id ->
            showGameSelectionPopUp(listLevel[position].id)
            Log.d(Constants.LOG_TAG,listLevel[position].name+" "+listLevel[position].type)
        }

    }

    private fun showGameSelectionPopUp(levelId : Int) {

        var dialog = Dialog(context!!,android.R.style.ThemeOverlay_Material_Dialog_Alert)
        dialog.setContentView(R.layout.game_selection_popup)
        var window = dialog.window
        var windowLayoutParams  = window.attributes
        windowLayoutParams.gravity  = Gravity.CENTER
        windowLayoutParams.flags  = WindowManager.LayoutParams.FLAG_BLUR_BEHIND
        window.attributes = windowLayoutParams
        dialog.window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT)
        dialog.show()

        var tvPlayUser = dialog.findViewById<TextView>(R.id.tvPlayUser)
        tvPlayUser.setOnClickListener {
            dialog.dismiss()
            showLongToast("We are updating this feature will be right back soon",context!!)
        }
        var tvPlayRobot = dialog.findViewById<TextView>(R.id.tvPlayRobot)
        tvPlayRobot.setOnClickListener {
            dialog.dismiss()
            speedMathView.showMathWithComputerFragement(levelId)
        }
    }


    override fun showProgress(show: Boolean) {
        progressBar.bringToFront()
        if (show)
            progressBar.visibility = View.VISIBLE
        else
            progressBar.visibility = View.GONE
    }

    private fun injectDependency() {
        val fragmentComponent = DaggerFragmentComponent.builder()
            .fragmentmodule(Fragmentmodule())
            .contextModule(ContextModule(context!!))
            .build()
        fragmentComponent.inject(this)
    }

    override fun loadDataSuccess(levelData: GameLevelModel) {
        listLevel = levelData.levels
        gvLevelContainer.adapter = GameLevelAdapter(context!!, listLevel)

        Log.d(Constants.LOG_TAG, levelData.toString())
    }

    override fun loadDataError(msg: String) {
        Log.d(Constants.LOG_TAG, msg)
        showLongToast(msg, context!!)
    }

    companion object {
        var TAG = "GameLevelFragment"
    }


}

class GameLevelContractor {
    interface View : BaseContract.View {
        fun loadDataSuccess(levelData: GameLevelModel)
        fun loadDataError(msg: String)
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun loadLevelDatas(studentId: Int)
    }
}

class GameLevelPresenter : GameLevelContractor.Presenter {
    private var subscriptions = CompositeDisposable()
    private var apiInterface = ApiServiceInterface.create()
    private lateinit var view: GameLevelContractor.View
    override fun subscribe() {

    }

    override fun unsubscrbe() {
        subscriptions.clear()
    }

    override fun attach(view: GameLevelContractor.View) {
        this.view = view
    }

    override fun loadLevelDatas(studentId : Int) {
        view.showProgress(true)
        var subscribe = apiInterface.getGameLevels(studentId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.showProgress(false)
                if (it.status) {
                    view.loadDataSuccess(it)
                } else {
                    view.loadDataError("Unable to get data please try again")
                }
            }, {
                view.showProgress(false)
                view.loadDataError(it.localizedMessage)
            })
        subscriptions.add(subscribe)
    }

}

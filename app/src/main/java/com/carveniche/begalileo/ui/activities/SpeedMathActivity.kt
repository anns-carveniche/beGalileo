package com.carveniche.begalileo.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.carveniche.begalileo.util.Constants
import com.carveniche.begalileo.R
import com.carveniche.begalileo.di.component.DaggerActivityComponent
import com.carveniche.begalileo.di.module.ActivityModule
import com.carveniche.begalileo.di.module.ContextModule
import com.carveniche.begalileo.models.AnswerSheetModel
import com.carveniche.begalileo.ui.fragments.GameLevelFragment
import com.carveniche.begalileo.ui.fragments.MathWithComputerFragment
import com.carveniche.begalileo.contract.SpeedMathContract
import com.carveniche.begalileo.util.showLongToast
import javax.inject.Inject

class SpeedMathActivity : AppCompatActivity(), SpeedMathContract.View {


    @Inject lateinit var presenter : SpeedMathContract.Presenter
    var studentId = 269

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_speed_math)
        injectDependency()
        presenter.attach(this)
        showGameLevelFragment()
    }

    override fun showProgress(boolean: Boolean) {

    }
    override fun showGameLevelFragment() {
        supportFragmentManager.beginTransaction()
            .addToBackStack(GameLevelFragment.TAG)
            .replace(R.id.frame,
                GameLevelFragment(),
                GameLevelFragment.TAG)
            .commit()
    }
    private fun injectDependency() {
        val activityComponent = DaggerActivityComponent.builder()
            .activityModule(ActivityModule(this))
            .contextModule(ContextModule(this))
            .build()
        activityComponent.inject(this)
    }

    override fun showMathWithComputerFragement(levelId: Int) {
        var ldf = MathWithComputerFragment()
        var listPlayers = arrayListOf<Int>(studentId)
        var args = Bundle()
        args.putIntegerArrayList(Constants.PLAYERS,listPlayers)
        args.putInt(Constants.LEVEL_ID,levelId)

        ldf.arguments = args

        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.frame,ldf, MathWithComputerFragment.TAG)
            .commit()
    }

    override fun showMathWithPlayerFragement() {

    }
    override fun showGameResultActivity(data: AnswerSheetModel, robotCorrectAnswer: Int) {

        var intent = Intent(this, GameResultActivity::class.java)
        intent.putExtra(Constants.QuestionAnswerLog,data)
        intent.putExtra(Constants.COMPUTER_SCORE,robotCorrectAnswer)
        startActivity(intent)
    }

    override fun onBackPressed() {
        Log.d(Constants.LOG_TAG,supportFragmentManager.backStackEntryCount.toString()+" BK")
        if (supportFragmentManager.backStackEntryCount > 1){
            supportFragmentManager.popBackStack()
        }
        else
        {
          showLongToast("Do u want to exit ",this)
        }

    }

}

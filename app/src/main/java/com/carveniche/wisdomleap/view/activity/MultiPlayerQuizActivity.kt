package com.carveniche.wisdomleap.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.carveniche.wisdomleap.R
import com.carveniche.wisdomleap.util.Constants
import com.carveniche.wisdomleap.view.fragment.MultiplayerQuizPlayFragment
import com.carveniche.wisdomleap.view.fragment.MultiplayerResultFragment
import com.carveniche.wisdomleap.view.fragment.MultiplayerSearchFragment

class MultiPlayerQuizActivity : AppCompatActivity() {

    var quizLevel  = Constants.EASY
    var quizCategory = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multi_player_quiz)
        quizLevel = intent.getStringExtra(Constants.QUIZ_LEVEL)
        quizCategory = intent.getIntExtra(Constants.QUIZ_CATEGORY,0)
        Log.d(Constants.LOG_TAG,quizLevel+" -- "+quizCategory)
        showMultiplayerSearch()
    }

    private fun showMultiplayerSearch() {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.container,MultiplayerSearchFragment(),MultiplayerSearchFragment.TAG)
            .commit()
    }
     fun showMultiplayerQuizPlay(opponentName : String,opponentAvatar : Int) {
         var ldf = MultiplayerQuizPlayFragment()
         var args = Bundle()
         args.putString(Constants.OPPONENT_NAME,opponentName)
         args.putInt(Constants.OPPONENT_AVATAR,opponentAvatar)
         args.putInt(Constants.QUIZ_CATEGORY,quizCategory)
         args.putString(Constants.QUIZ_LEVEL,quizLevel)
         ldf.arguments = args
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.container,ldf,MultiplayerQuizPlayFragment.TAG)
            .commit()
    }
     fun showMultiplayerQuizResult(opponentName: String,isPlayerWin : Boolean,avatar : Int) {
         var ldf = MultiplayerResultFragment()
         var args = Bundle()
         args.putString(Constants.OPPONENT_NAME,opponentName)
         args.putBoolean(Constants.IS_PLAYER_WIN,isPlayerWin)
         args.putInt(Constants.OPPONENT_AVATAR,avatar)
         ldf.arguments = args
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.container,ldf,MultiplayerResultFragment.TAG)
            .commit()
    }

    override fun onBackPressed() {
        var count = supportFragmentManager.backStackEntryCount
        Log.d(Constants.LOG_TAG,"Count $count")
        when(count)
        {
            1->{
                exitQuiz()
            }
            2->{
                exitQuiz()
            }
            3->{
                goToMainActivity()
            }
        }
    }

    private fun exitQuiz()
    {
        var builder = AlertDialog.Builder(this)
        builder.setTitle("Exit Quiz")
        builder.setMessage("are you sure want to exit the quiz right now?")
        builder.setPositiveButton("Yes"){dialog, which ->
            goToMainActivity()
        }
        builder.setNegativeButton("No"){dialog, which ->
            dialog.dismiss()
        }
        val dialog : AlertDialog = builder.create()
        dialog.show()
    }
    private fun goToMainActivity()
    {
        var intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }
}

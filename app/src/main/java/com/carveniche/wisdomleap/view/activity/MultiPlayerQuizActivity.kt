package com.carveniche.wisdomleap.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.carveniche.wisdomleap.R
import com.carveniche.wisdomleap.util.Constants
import com.carveniche.wisdomleap.view.fragment.MultiplayerQuizPlayFragment
import com.carveniche.wisdomleap.view.fragment.MultiplayerResultFragment
import com.carveniche.wisdomleap.view.fragment.MultiplayerSearchFragment

class MultiPlayerQuizActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multi_player_quiz)
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

    }
}

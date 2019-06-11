package com.carveniche.wisdomleap.view.fragment


import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.carveniche.wisdomleap.R
import com.carveniche.wisdomleap.contract.MultiplayerResultContract
import com.carveniche.wisdomleap.di.component.DaggerFragmentComponent
import com.carveniche.wisdomleap.di.module.ContextModule
import com.carveniche.wisdomleap.di.module.FragmentModule
import com.carveniche.wisdomleap.di.module.SharedPreferenceModule
import com.carveniche.wisdomleap.model.MySharedPreferences
import com.carveniche.wisdomleap.util.Constants
import com.carveniche.wisdomleap.view.activity.MainActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_multiplayer_result.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MultiplayerResultFragment : Fragment(),MultiplayerResultContract.View {

    @Inject lateinit var presenter: MultiplayerResultContract.Presenter
    private lateinit var rootView: View
    @Inject lateinit var mySharedPreferences: MySharedPreferences
    private var playerScore= 0
    private var opponentScore = 0
    private var opponentAvatarId = 0
    private var totalCoins = 100
    private var playerName = ""
    private var opponentName = ""
    private var isPlayerWin = false
    private var coinAnimationSpeed : Long = 4000
    private var avatarList = Constants.getAvatarList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_multiplayer_result, container, false)
        return rootView
    }
    override fun showProgress(show: Boolean) {

    }
    private fun injectDependency() {
        val fragmentComponent = DaggerFragmentComponent.builder()
            .fragmentModule(FragmentModule())
            .sharedPreferenceModule(SharedPreferenceModule())
            .contextModule(ContextModule(context!!))
            .build()
        fragmentComponent.inject(this)

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attach(this)
        presenter.subscribe()
        initUI()
    }

    private fun initUI() {
        playerName = mySharedPreferences.getString(Constants.FIRST_NAME)+" "+mySharedPreferences.getString(Constants.LAST_NAME)
        opponentName = arguments!!.getString(Constants.OPPONENT_NAME)
        isPlayerWin = arguments!!.getBoolean(Constants.IS_PLAYER_WIN)
        opponentAvatarId = arguments!!.getInt(Constants.OPPONENT_AVATAR)
        if(playerName.isBlank())
            tv_player_name.text = "Guest Player"
        tv_opponent_name.text = opponentName
        iv_opponent_avatar.setBackgroundResource(avatarList[opponentAvatarId])
        if(isPlayerWin)
        {
            tv_player_winner.visibility = View.VISIBLE
            tv_opponent_winner.visibility = View.INVISIBLE
        }
        else
        {
            tv_opponent_winner.visibility = View.VISIBLE
            tv_player_winner.visibility = View.INVISIBLE
        }

        tv_total_coins.text = "$totalCoins Coins"
        tv_player_coins.text = "0 Coins"
        tv_opponent_coins.text = "0 Coins"

         Observable.timer(2000,TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe {
                addWinnerCoins()
            }

        btn_home.setOnClickListener {
            var intent = Intent(context,MainActivity::class.java)
            startActivity(intent)
        }


    }

    private fun addWinnerCoins() {
        var playerAnimator = ValueAnimator.ofInt(0,totalCoins)
        playerAnimator.duration= coinAnimationSpeed
        playerAnimator.addUpdateListener {
            if(isPlayerWin)
                tv_player_coins.text = it.animatedValue.toString() + " Coins"
            else
                tv_opponent_coins.text = it.animatedValue.toString()+" Coins"
        }

        var totalCoinAnimator = ValueAnimator.ofInt(totalCoins,0)
        totalCoinAnimator.duration= coinAnimationSpeed
        totalCoinAnimator.addUpdateListener {
            tv_total_coins.text = it.animatedValue.toString() + " Coins"
        }

        var animatorSet = AnimatorSet()
        animatorSet.playTogether(playerAnimator,totalCoinAnimator)
        animatorSet.duration = coinAnimationSpeed
        animatorSet.addListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                btn_home.isEnabled = true

            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationStart(animation: Animator?) {

            }

        })
        animatorSet.start()
    }

    companion object {
        const val TAG = "MultiplayerResultFragment"
    }

}

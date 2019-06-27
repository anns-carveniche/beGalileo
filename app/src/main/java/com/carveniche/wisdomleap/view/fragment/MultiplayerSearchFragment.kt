package com.carveniche.wisdomleap.view.fragment


import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationSet
import androidx.core.content.ContextCompat

import com.carveniche.wisdomleap.R
import com.carveniche.wisdomleap.contract.MultiplayerSearchContract
import com.carveniche.wisdomleap.di.component.DaggerFragmentComponent
import com.carveniche.wisdomleap.di.module.ContextModule
import com.carveniche.wisdomleap.di.module.FragmentModule
import com.carveniche.wisdomleap.di.module.SharedPreferenceModule
import com.carveniche.wisdomleap.model.MySharedPreferences
import com.carveniche.wisdomleap.util.Config
import com.carveniche.wisdomleap.util.Constants
import com.carveniche.wisdomleap.util.showLoadingProgress
import com.carveniche.wisdomleap.view.activity.MultiPlayerQuizActivity
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_multiplayer_search.*
import kotlinx.android.synthetic.main.fragment_profile_home.*
import javax.inject.Inject
import kotlin.random.Random

class MultiplayerSearchFragment : Fragment(),MultiplayerSearchContract.View {


    private lateinit var rootView : View
    @Inject lateinit var presenter : MultiplayerSearchContract.Presenter
    @Inject lateinit var mySharedPreferences: MySharedPreferences
    private lateinit var randomPlayerFrameAnimation : AnimationDrawable
    private var coinAnimationSpeed : Long = 4000
    private var opponentCoins = 100
    private var userCoins = 0
    private var betValue = Config.MULTIPLAYER_BET_VALUE
    private var mUserName = ""
    private var mOpponentName = ""
    private var mOpponentAvatar = 0
    private var randomAvatarIndex = 0
    private lateinit var multiPlayerQuizActivity : MultiPlayerQuizActivity
    private var avatarArray = Constants.getAvatarList()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
        userCoins = arguments!!.getInt(Constants.USER_COINS)
        multiPlayerQuizActivity = activity as MultiPlayerQuizActivity
        mUserName = mySharedPreferences.getString(Constants.FIRST_NAME)+" "+mySharedPreferences.getString(Constants.LAST_NAME)
        if(mUserName.isBlank())
            mUserName = "Guest Player"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        presenter.attach(this)
        presenter.subscribe()
        btn_play.isEnabled = false
        iv_random_Player.setBackgroundResource(R.drawable.player_avatar_list)
        randomPlayerFrameAnimation = iv_random_Player.background as AnimationDrawable
        tv_player_coins.text = "$userCoins Coins"
        presenter.searchRandomPlayer()
        btn_play.setOnClickListener {
            multiPlayerQuizActivity.showMultiplayerQuizPlay(mOpponentName,randomAvatarIndex)
        }

        tv_player_search_header.text = "Matching Player"
        tv_player_name.text = mUserName
    }

    override fun randomPlayerAnimation(state: Boolean) {
        if(state)
            randomPlayerFrameAnimation.start()
        else
        {
            randomPlayerFrameAnimation.stop()
            var randomImage = Random.nextInt(0,11)
            iv_random_Player.setBackgroundResource(avatarArray[randomImage])
            randomAvatarIndex = randomImage

        }

    }

    private fun injectDependency() {
        val fragmentComponent = DaggerFragmentComponent.builder()
            .fragmentModule(FragmentModule())
            .sharedPreferenceModule(SharedPreferenceModule())
            .contextModule(ContextModule(context!!))
            .build()
        fragmentComponent.inject(this)

    }

    override fun showRandomPlayer(name: String, coins: Int) {
        tv_random_player_name.text = name
        tv_random_player_coins.text = "$coins Coins"
        opponentCoins = coins
        mOpponentName = name
        tv_player_search_header.text = "Matching Found"

        tv_random_player_coins.text = "$userCoins Coins"
        coinStackFromUser()
    }

    private fun coinStackFromUser(){
        var playerAnimator = ValueAnimator.ofInt(opponentCoins,opponentCoins-betValue)
        playerAnimator.duration= coinAnimationSpeed
        playerAnimator.addUpdateListener {
            tv_random_player_coins.text = it.animatedValue.toString() + " Coins"
        }
        var userAnimator = ValueAnimator.ofInt(userCoins,userCoins-betValue)
        userAnimator.duration= coinAnimationSpeed
        userAnimator.addUpdateListener {
            tv_player_coins.text = it.animatedValue.toString() + " Coins"
        }
        var totalCoinAnimator = ValueAnimator.ofInt(0,betValue*2)
        totalCoinAnimator.duration= coinAnimationSpeed
        totalCoinAnimator.addUpdateListener {
            tv_total_coins.text = it.animatedValue.toString() + " Coins"
        }

        var animatorSet = AnimatorSet()
        animatorSet.playTogether(playerAnimator,userAnimator,totalCoinAnimator)
        animatorSet.duration = coinAnimationSpeed
        animatorSet.addListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
               btn_play.isEnabled = true

            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationStart(animation: Animator?) {

            }

        })
        animatorSet.start()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_multiplayer_search, container, false)
        return rootView
    }
    override fun showProgress(show: Boolean) {

    }

    companion object {
        const val TAG = "MultiplayerSearch"
    }

}

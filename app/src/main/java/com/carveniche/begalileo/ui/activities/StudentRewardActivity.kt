package com.carveniche.begalileo.ui.activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.carveniche.begalileo.R
import com.carveniche.begalileo.api.ApiServiceInterface
import com.carveniche.begalileo.di.component.DaggerActivityComponent
import com.carveniche.begalileo.di.module.ActivityModule
import com.carveniche.begalileo.di.module.ContextModule
import com.carveniche.begalileo.di.module.SharedPreferenceModule
import com.carveniche.begalileo.models.*
import com.carveniche.begalileo.util.Constants
import com.carveniche.begalileo.util.showShortToast
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_student_reward.*
import kotlinx.android.synthetic.main.layout_progressbar.*


class StudentRewardActivity : AppCompatActivity() {

    private var api = ApiServiceInterface.create()
    private var disposable = CompositeDisposable()
    private var mRewardType = ""
    private var mStudentId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_reward)
        injectDependency()
        mRewardType = intent.getStringExtra(Constants.REWARD_TYPE)
        mStudentId = intent.getIntExtra(Constants.STUDENT_ID,0)
        Log.d(Constants.LOG_TAG,mRewardType);
        initUI()
    }

    private fun initUI() {
        when (mRewardType) {
            Constants.REWARDS -> {
                    getRewardDatas()
            }
            Constants.BADGES -> {
                    getBadgeDatas()
            }
            Constants.LEADER_BOARD -> {
                    getLeaderBoardDatas()
            }
            Constants.KID_PROGRESS -> {
                getKidsProgress()
            }

        }
    }

    private fun getKidsProgress() {
        showProgress(true)
        var badgeDisposable = api.getKidsProgress(mStudentId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                showProgress(false)
                if(it.status)
                {
                    showKidsProgress(it)
                }
                else
                {
                    showShortToast(Constants.NO_DATA_FOUND,this)
                    finish()
                }
            },{
                showProgress(false)
                showShortToast(Constants.ExceptionMessage,this)
                finish()
            })
        disposable.add(badgeDisposable)
    }

    private fun showKidsProgress(it: KidsProgressModel) {
        cl_reward_container.visibility = View.GONE
        cl_badges_container.visibility = View.GONE
        cl_leaderboard_container.visibility = View.GONE
        cl_kid_progress_container.visibility = View.VISIBLE

        tv_kp_name.text = it.concept_name
        tv_kp_accuracy.text = "Accuracy : "+it.accuracy
        tv_kp_correct_answers.text = "Correct Answers : "+it.correct
        tv_kp_questions_attempted.text = "Questions Attempted : "+it.total_question

    }

    fun showProgress(show: Boolean) {
        progressBar.bringToFront()
        if(show)
            progressBar.visibility = View.VISIBLE
        else
            progressBar.visibility = View.GONE
    }

    private fun injectDependency() {
        var activityComponent = DaggerActivityComponent.builder()
            .activityModule(ActivityModule(this))
            .sharedPreferenceModule(SharedPreferenceModule())
            .contextModule(ContextModule(this))
            .build()
        activityComponent.inject(this)
    }


    fun getBadgeDatas(){
        showProgress(true)
        var badgeDisposable = api.getBadgesDataModel(mStudentId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                showProgress(false)
                if(it.status)
                {
                    showBadgesScreen(it)
                }
                else
                {
                    showShortToast(Constants.NO_DATA_FOUND,this)
                    finish()
                }
            },{
                showProgress(false)
                showShortToast(Constants.ExceptionMessage,this)
                finish()
            })
        disposable.add(badgeDisposable)
    }

    fun getRewardDatas(){
        showProgress(true)
        var rewardDisposable = api.getRewardsDataModel(mStudentId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                showProgress(false)
                if(it.status)
                {
                    showRewardsScreen(it)
                }
                else
                {
                    showShortToast(Constants.NO_DATA_FOUND,this)
                    finish()
                }
            },{
                showProgress(false)
                showShortToast(Constants.ExceptionMessage,this)
                finish()
            })
        disposable.add(rewardDisposable)
    }

    fun getLeaderBoardDatas(){
        showProgress(true)
        var badgeDisposable = api.getLeaderboardDataModel(mStudentId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                showProgress(false)
                if(it.status)
                {
                    showLeaderboardScreen(it)
                }
                else
                {
                    showShortToast(Constants.NO_DATA_FOUND,this)
                    finish()
                }
            },{
                showProgress(false)
                showShortToast(Constants.ExceptionMessage,this)
                finish()
            })
        disposable.add(badgeDisposable)
    }



    fun showRewardsScreen(rewardsDataModel: RewardsDataModel){

        cl_reward_container.visibility = View.VISIBLE
        cl_badges_container.visibility = View.GONE
        cl_leaderboard_container.visibility = View.GONE
        cl_kid_progress_container.visibility = View.GONE

        tv_available_coins.text = resources.getString(R.string.available_coins,rewardsDataModel.available_coins)
        tv_coins_redeemed.text = resources.getString(R.string.coins_redeemed,rewardsDataModel.redeedmed_rewards)
        tv_coins_today.text = resources.getString(R.string.coins_earned_today,rewardsDataModel.today_coins)


    }
    fun showLeaderboardScreen(leaderboardDataModel: LeaderboardDataModel){
        cl_reward_container.visibility = View.GONE
        cl_badges_container.visibility = View.GONE
        cl_leaderboard_container.visibility = View.VISIBLE
        cl_kid_progress_container.visibility = View.GONE

       addLeaderboardTabledatas(leaderboardDataModel.current_month)
        tv_your_score.text = resources.getString(R.string.yours_score,leaderboardDataModel.current_user_score)
        tv_points_away.text = resources.getString(R.string.points_away,leaderboardDataModel.total)
    }

    fun showBadgesScreen(badgesDataModel: BadgesDataModel){
        cl_reward_container.visibility = View.GONE
        cl_badges_container.visibility = View.VISIBLE
        cl_leaderboard_container.visibility = View.GONE
        cl_kid_progress_container.visibility = View.GONE

        addBadgeDatas(badgesDataModel)
    }
    fun addBadgeDatas(badgesDataModel: BadgesDataModel){
        val vi = applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        badgesDataModel.data.forEach {
            var view = vi.inflate(R.layout.item_list_badges,null)
            var tvCount = view.findViewById<TextView>(R.id.tvBadgeCount)
            var ivBadge = view.findViewById<ImageView>(R.id.iv_badge)
            Picasso.get().load(it.badge_image).into(ivBadge)

            tvCount.text = it.count.toString()

            ll_badges_container.addView(view)
        }

    }

    fun addLeaderboardTabledatas(currentMonth: List<CurrentMonth>) {

        val vi = applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        currentMonth.forEachIndexed { index, currentMonth ->
            var view = vi.inflate(R.layout.list_item_leaderboard,null)
            var tvRank = view.findViewById<TextView>(R.id.tvRank)
            var tvStudentName = view.findViewById<TextView>(R.id.tvStudentName)
            var tvStudentGrade = view.findViewById<TextView>(R.id.tvStudentGrade)
            var tvStudentScore = view.findViewById<TextView>(R.id.tvStudentScore)

            tvRank.text = currentMonth.rank.toString()
            tvStudentName.text = currentMonth.student_name
            tvStudentGrade.text = currentMonth.grade
            tvStudentScore.text = currentMonth.points.toString()

            tl_container.addView(view)
        }


    }
}

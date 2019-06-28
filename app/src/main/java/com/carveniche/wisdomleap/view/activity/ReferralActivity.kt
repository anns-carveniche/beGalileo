package com.carveniche.wisdomleap.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.carveniche.wisdomleap.R
import com.carveniche.wisdomleap.api.ApiInterface
import com.carveniche.wisdomleap.model.ChapterQuizModel
import com.carveniche.wisdomleap.model.QuizData
import com.carveniche.wisdomleap.util.Constants
import com.carveniche.wisdomleap.util.showtMathView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_referral.*
import android.content.Intent
import com.carveniche.wisdomleap.util.showLoadingProgress
import com.carveniche.wisdomleap.util.showShortToast
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.layout_progressbar.*


class ReferralActivity : AppCompatActivity() {

    private var api = ApiInterface.create()
    private var mStudentId = 0
    private var mRefferalCode = ""
    var disposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_referral)
        mStudentId = intent.getIntExtra(Constants.STUDENT_ID,0)
        getRefferalCode()
        btn_share_code.setOnClickListener {
            shareReferralCode()
        }

    }

    fun showProgress(state : Boolean)
    {
        showLoadingProgress(progressBar,state)
    }

    private fun getRefferalCode() {
        showProgress(true)
        var referralObs = api.showRefferalCode(mStudentId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                showProgress(false)
                if(it.status)
                {
                    mRefferalCode = it.reference_code
                    tv_referral_code.text = it.reference_code
                }
                else
                {
                    showShortToast(Constants.ERROR_MESSAGE,this)
                }
            },{
                showProgress(false)
                showShortToast(it.localizedMessage,this)
            })
        disposable.add(referralObs)
    }

    private fun shareReferralCode() {
        val sharingIntent = Intent(android.content.Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Wisdom Leap Invitation")
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Please use my invite code $mRefferalCode and get various benefits")
        startActivity(Intent.createChooser(sharingIntent, "share using"))
    }


}

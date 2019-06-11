package com.carveniche.wisdomleap.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.carveniche.wisdomleap.R
import com.carveniche.wisdomleap.util.showLoadingProgress
import com.carveniche.wisdomleap.util.showLongToast
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.layout_progressbar.*
import java.util.concurrent.TimeUnit
import io.reactivex.schedulers.Schedulers



class ChatSupportActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_support)
        loadChatForm()
    }

    private fun loadChatForm() {
        showProgress(true)
        Observable
            .timer(3000, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map<Any> { o -> showChatForm() }
            .subscribe()
    }

    private fun showChatForm() {
        showProgress(false)
        showLongToast("Your trial period expired.please subscribe to our premium package",this)
        onBackPressed()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }


    fun showProgress(state : Boolean)
    {
        showLoadingProgress(progressBar,state)
    }

}

package com.carveniche.begalileo.ui.activities

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.carveniche.begalileo.R

import com.carveniche.begalileo.util.Constants
import com.carveniche.begalileo.util.isNetworkAvailable
import com.carveniche.begalileo.util.showShortToast
import kotlinx.android.synthetic.main.activity_live_class.*
import pub.devrel.easypermissions.EasyPermissions

import pub.devrel.easypermissions.AfterPermissionGranted

import com.opentok.android.Session;
import com.opentok.android.Stream;
import com.opentok.android.Publisher;
import com.opentok.android.PublisherKit;
import com.opentok.android.Subscriber;
import com.opentok.android.OpentokError;
import android.opengl.GLSurfaceView





















class LiveClassActivity : AppCompatActivity(),Session.SessionListener,PublisherKit.PublisherListener {



    private val API_KEY = "46415992"
    private val SESSION_ID = "1_MX40NjQxNTk5Mn5-MTU2Nzc2ODIzNDIyOX5QS1N2RVNJd2dMakR0Vkp2aG1scFdScmZ-QX4"
    private val TOKEN = "T1==cGFydG5lcl9pZD00NjQxNTk5MiZzaWc9NTkyZmVhZWM3Nzc2MzZlYmMwZjNhYjQ4MTI4NmI0MDI1MGY1MjJiNTpyb2xlPW1vZGVyYXRvciZzZXNzaW9uX2lkPTFfTVg0ME5qUXhOVGs1TW41LU1UVTJOemMyT0RJek5ESXlPWDVRUzFOMlJWTkpkMmRNYWtSMFZrcDJhRzFzY0ZkU2NtWi1RWDQmY3JlYXRlX3RpbWU9MTU2Nzc2ODIzNCZub25jZT0wLjE1MDQ1NDYzMTc2MTM1NzImY29ubmVjdGlvbl9kYXRhPW5hbWUlM0RKb2hubnk="

    private val LOG_TAG = LoginActivity::class.java.simpleName
    private val RC_SETTINGS_SCREEN_PERM = 123
     val RC_VIDEO_APP_PERM = 124
    private lateinit var mSession: Session
    private  var mPublisher: Publisher? = null
    private var mSubscriber: Subscriber? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_class)
        initUI()
        requestPermissions()

    }

    private fun initUI() {
        iv_call_end.setOnClickListener {
            disConnectCall()
        }
    }


    @AfterPermissionGranted(124)
    fun requestPermissions() {

        val perms = arrayOf(Manifest.permission.INTERNET, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
        if (EasyPermissions.hasPermissions(this, *perms)) {
            // initialize view objects from your layout

            connectToSession()
            // initialize and connect to the session


        } else {

            EasyPermissions.requestPermissions(
                this,
                getString(R.string.video_audio_permission),
                RC_VIDEO_APP_PERM,
              *perms
            )
        }
    }

    private fun connectToSession() {
        mSession = Session.Builder(this, API_KEY, SESSION_ID).build()
        mSession.setSessionListener(this)
        mSession.connect(TOKEN)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }


    override fun onStreamDropped(p0: Session?, p1: Stream?) {
        Log.i(Constants.LOG_TAG, "Stream Dropped")
        if (mSubscriber != null) {
            mSubscriber = null
            subscriber_container.removeAllViews()
        }
    }

    override fun onStreamReceived(session: Session?, stream: Stream?) {
        Log.i(Constants.LOG_TAG, "Stream Received")
        Log.d(Constants.LOG_TAG, "onStreamReceived: New stream " + stream!!.streamId + " in session " + session!!.sessionId);

        if(mSubscriber == null)
        {
            mSubscriber = Subscriber.Builder(this,stream).build()
            mSession.subscribe(mSubscriber)
            subscriber_container.addView(mSubscriber!!.view)

        }

    }

    override fun onConnected(p0: Session?) {
        Log.i(Constants.LOG_TAG, "Session Connected")
        mPublisher = Publisher.Builder(this).build()
        mPublisher!!.publishAudio = true
        mPublisher!!.setPublisherListener(this)
        publisher_container.addView(mPublisher!!.view)
        if (mPublisher!!.view is GLSurfaceView) {
            (mPublisher!!.view as GLSurfaceView).setZOrderOnTop(true)
        }

        mSession.publish(mPublisher)
    }

    override fun onDisconnected(p0: Session?) {
        Log.i(Constants.LOG_TAG, "Session Disconnected");
    }

    override fun onError(p0: Session?, opentokError: OpentokError?) {
        Log.e(Constants.LOG_TAG, "Session error: " + opentokError!!.message);
    }

    override fun onError(p0: PublisherKit?, opentokError: OpentokError?) {
        Log.e(Constants.LOG_TAG, "Publisher error: " + opentokError!!.message);
    }

    override fun onStreamCreated(p0: PublisherKit?, p1: Stream?) {
        Log.i(Constants.LOG_TAG, "Publisher onStreamCreated");
    }

    override fun onStreamDestroyed(p0: PublisherKit?, p1: Stream?) {
        Log.i(Constants.LOG_TAG, "Publisher onStreamDestroyed");
    }

    private fun disConnectCall()
    {
        mSession.disconnect()
    }


}



package com.carveniche.begalileo.ui.activities

import android.Manifest
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

import com.carveniche.begalileo.util.Constants
import kotlinx.android.synthetic.main.activity_live_class.*
import pub.devrel.easypermissions.EasyPermissions

import pub.devrel.easypermissions.AfterPermissionGranted

import android.opengl.GLSurfaceView
import android.view.MotionEvent
import android.view.View
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat

import com.carveniche.begalileo.R
import com.carveniche.begalileo.util.showShortToast

import pub.devrel.easypermissions.AppSettingsDialog


import java.lang.Exception

import android.view.animation.Animation
import android.view.animation.AnimationUtils

import com.carveniche.begalileo.adapters.SignalMessageAdapter
import com.carveniche.begalileo.models.SignalMessage

import com.carveniche.begalileo.util.OpenTokConfig




class LiveClassActivity : AppCompatActivity(){



    val  SIGNAL_TYPE = "text-signal";
    private val LOG_TAG = LoginActivity::class.java.simpleName
    private val RC_SETTINGS_SCREEN_PERM = 123
     val RC_VIDEO_APP_PERM = 124
//    private  var mSession: Session? = null
//    private  var mPublisher: Publisher? = null
//    private lateinit var mSubscriber: Subscriber
//    private var subscriberList = mutableListOf<Subscriber>()
//    private val mSubscriberStreams = HashMap<Stream, Subscriber>()
//    private lateinit var mWrapper : OTWrapper
//    private lateinit var mVideoRenderer : AnnotationsVideoRenderer
    private lateinit var mMessageAdapter: SignalMessageAdapter
    private lateinit var chatAnimIn : Animation
    private lateinit var chatAnimOut : Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_class)
        //configureVariables()
//        loadWhiteBoard()
//        initUI()
//        requestPermissions()



    }

//    private fun loadWhiteBoard() {
//        var boardHtml = "<html><head>\n" +
//                "  <style type=\"text/css\">\n" +
//                "  </style>\n" +
//                "  <script src=\"https://code.jquery.com/jquery-3.3.1.min.js\"></script>\n" +
//                "  <link rel=\"stylesheet\" href=\"https://awwapp.com/static/widget/css/toolbar_style.css\">\n" +
//                "\n" +
//                "</head>\n" +
//                "<body>\n" +
//                "    <div id=\"aww-wrapper\" class=\"aww\"></div>\n" +
//                "    <script src=\"https://awwapp.com/static/widget/js/aww3.min.js\"></script>\n" +
//                "    <script type=\"text/javascript\">\n" +
//                "        var aww = new AwwBoard('#aww-wrapper', {\n" +
//                "            /* make sure you're using your own key here */\n" +
//                "            apiKey: 'd8ef5333-8850-4a93-a637-e15a3cfa306d',\n" +
//                "            /* put a unique text here */\n" +
//                "            boardLink: 'k5263124',\n" +
//                "            multiPage: true,\n" +
//                "            sendUserPointer: true,\n" +
//                "            showUserPointers: true\n" +
//                "        });\n" +
//                "        \$.ajax({\n" +
//                "            'method': 'GET',\n" +
//                "            'url': 'https://awwapp.com/static/widget/sample_toolbar.html'\n" +
//                "          }).done(function(res, status) {\n" +
//                "            \$('#aww-wrapper').append(res);\n" +
//                "            initToolbar();\n" +
//                "          });\n" +
//                "    </script>\n" +
//                "    <script src=\"https://awwapp.com/static/widget/sample_toolbar.js\"></script>\n" +
//                "</body></html>"
//
//
//        var ws = wb_whiteboard.settings;
//
//        ws.javaScriptEnabled = true
//        ws.allowFileAccess = true
//
//        wb_whiteboard.setOnTouchListener { v, event -> (event!!.action == MotionEvent.ACTION_MOVE); }
//        wb_whiteboard.loadData(boardHtml,"text/html","UTF-8")
//    }
//
//    private fun configureVariables() {
////        mVideoRenderer = AnnotationsVideoRenderer(this)
////        val config = OTConfig.OTConfigBuilder(
////            OpenTokConfig.SESSION_ID,
////            OpenTokConfig.TOKEN,
////            OpenTokConfig.API_KEY
////        ).name("begalileo").subscribeAutomatically(true).subscribeToSelf(false).build()
////
////        mWrapper = OTWrapper(this,config)
////            mWrapper.connect()
//    }
//
//    private fun initUI() {
//        iv_call_end.setOnClickListener {
//            disconnectSession()
//        }
//        iv_mic.setOnClickListener {
//            togglePublisherAudio()
//        }
//        iv_video_cam.setOnClickListener {
//            togglePublisherVideo()
//        }
//        iv_switch_camera.setOnClickListener {
//            //mPublisher!!.cycleCamera()
//        }
//        iv_screen_share.setOnClickListener {
//            toggleScreenShare()
//        }
//        iv_chat.setOnClickListener {
//            openChatDialog()
//        }
//        iv_close_chat.setOnClickListener {
//            closeChatDialog()
//        }
//        iv_white_board.setOnClickListener {
//            openWhiteBoard()
//        }
//        iv_close_whiteboard.setOnClickListener {
//            closeWhiteBoard()
//        }
//        mMessageAdapter = SignalMessageAdapter(this)
//        lv_chat_message.adapter = mMessageAdapter
//        btn_send_message.setOnClickListener {
//            sendMessage()
//        }
//        chatAnimIn = AnimationUtils.loadAnimation(this,R.anim.anim_chat_in)
//        chatAnimOut = AnimationUtils.loadAnimation(this,R.anim.anim_chat_out)
//
//    }
//
//    private fun sendMessage() {
//        try {
//            var signal = SignalMessage(ed_message.text.toString())
//            mSession!!.sendSignal(SIGNAL_TYPE,signal.messageText)
//            ed_message.setText("")
//        }
//        catch (ae : Exception)
//        {
//           Log.d(Constants.LOG_TAG,"Chat send failed : ${ae.localizedMessage}")
//        }
//
//
//    }
//
//    private fun toggleScreenShare()
//    {
////        Log.d(Constants.LOG_TAG,mPublisher!!.publisherVideoType.toString())
////        if(mPublisher!!.publisherVideoType == PublisherKit.PublisherKitVideoType.PublisherKitVideoTypeCamera)
////        {
////            mPublisher!!.publisherVideoType = PublisherKit.PublisherKitVideoType.PublisherKitVideoTypeScreen
////            iv_screen_share.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_screen_share_blue))
////        }
////        else
////        {
////            mPublisher!!.publisherVideoType = PublisherKit.PublisherKitVideoType.PublisherKitVideoTypeCamera
////            iv_screen_share.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_stop_screen_share))
////        }
//    }
//
//    fun openChatDialog()
//    {
//        if(rl_chat_container.visibility == View.GONE)
//        {
//            rl_chat_container.visibility = View.VISIBLE
//            rl_chat_container.startAnimation(chatAnimIn)
//            rl_chat_container.bringToFront()
//        }
//
//    }
//    fun closeChatDialog()
//    {
//        if(rl_chat_container.visibility == View.VISIBLE)
//        {
//            rl_chat_container.visibility = View.GONE
//            rl_chat_container.startAnimation(chatAnimOut)
//        }
//
//    }
//
//
//    fun openWhiteBoard()
//    {
//        if(ll_whiteboard_container.visibility == View.GONE)
//        {
//            ll_whiteboard_container.visibility = View.VISIBLE
//            ll_whiteboard_container.startAnimation(chatAnimIn)
//            ll_whiteboard_container.bringToFront()
//        }
//
//    }
//
//    fun closeWhiteBoard()
//    {
//        if(ll_whiteboard_container.visibility == View.VISIBLE)
//        {
//            ll_whiteboard_container.visibility = View.GONE
//            ll_whiteboard_container.startAnimation(chatAnimOut)
//
//        }
//
//    }
//
//
//    /*private val mBasicListener = PausableBasicListener(object : BasicListener<OTWrapper>{
//
//    })*/
//
//
//
//    private fun togglePublisherAudio() {
////       var audioStatus = mPublisher!!.publishAudio
////        Log.d(Constants.LOG_TAG,"Audio Status $audioStatus")
////        mPublisher!!.publishAudio = !audioStatus
////        if(mPublisher!!.publishAudio)
////            iv_mic.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_mic_on))
////        else
////            iv_mic.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_mic_off))
//
//    }
//
//    private fun togglePublisherVideo()
//    {
////        var videoStatus = mPublisher!!.publishVideo
////        mPublisher!!.publishVideo = !videoStatus
////        if(mPublisher!!.publishVideo)
////            iv_video_cam.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_videocam_on))
////        else
////            iv_video_cam.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_videocam_off))
//    }
//
//
//    @AfterPermissionGranted(124)
//    fun requestPermissions() {
//
//        val perms = arrayOf(Manifest.permission.INTERNET, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
//        if (EasyPermissions.hasPermissions(this, *perms)) {
//            // initialize view objects from your layout
//
//            initalizeSession()
//            // initialize and connect to the session
//
//
//        } else {
//
//            EasyPermissions.requestPermissions(
//                this,
//                getString(R.string.video_audio_permission),
//                RC_VIDEO_APP_PERM,
//              *perms
//            )
//        }
//    }
//
//    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
//        Log.d(Constants.LOG_TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size)
//        if(EasyPermissions.somePermissionPermanentlyDenied(this,perms))
//        {
//            AppSettingsDialog.Builder(this)
//                .setTitle(getString(R.string.title_settings_dialog))
//                .setRationale(getString(R.string.rationale_ask_again))
//                .setPositiveButton(getString(R.string.setting))
//                .setNegativeButton(getString(R.string.cancel))
//                .setRequestCode(RC_SETTINGS_SCREEN_PERM)
//                .build()
//                .show()
//        }
//    }
//
////    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
////        Log.d(Constants.LOG_TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size);
////    }
//
//    override fun onResume() {
//        super.onResume()
//        if(mSession!=null)
//             mSession!!.onResume()
//    }
//
//    override fun onPause() {
//        super.onPause()
//        if(mSession!=null)
//            mSession!!.onPause()
//
//    }
//
//    private fun initalizeSession() {
//        mSession = Session.Builder(this, OpenTokConfig.API_KEY, OpenTokConfig.SESSION_ID).build()
//        mSession!!.setSessionListener(this)
//        mSession!!.connect(OpenTokConfig.TOKEN)
//    }
//
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
//    }
//
//    override fun onStreamCreated(p0: PublisherKit?, p1: Stream?) {
//        Log.i(Constants.LOG_TAG, "Publisher onStreamCreated");
//        resurfaceView()
//    }
//
//    override fun onError(annotationError: String?) {
//      Log.d(Constants.LOG_TAG,"Annotation Error $annotationError")
//    }
//
//    override fun onAnnotationsDone() {
//        Log.d(Constants.LOG_TAG,"Annotation Done")
//    }
//
//    override fun onAnnotationsSelected(annotationMode: AnnotationsView.Mode?) {
//        Log.d(Constants.LOG_TAG,"Annotation Selected $annotationMode")
//    }
//
//    override fun onScreencaptureReady(bitmap: Bitmap?) {
//        Log.d(Constants.LOG_TAG,"Annotation Screen Capture $bitmap")
//    }
//
//
//
//
//    fun remoteAnnotaions()
//    {
//        try {
//            var mScreenAnnotationsView = AnnotationsView(this,mWrapper.session,OpenTokConfig.API_KEY,true)
//            mScreenAnnotationsView.attachToolbar(annotations_toolbar)
//            mScreenAnnotationsView.setAnnotationsListener(this)
//            subscriber_container.addView(mScreenAnnotationsView)
//        }
//        catch (ae : Exception)
//        {
//            Log.e(Constants.LOG_TAG,ae.localizedMessage)
//        }
//
//    }
//
//    fun resurfaceView()
//    {
//        tv_lbl_wait_message.visibility = View.GONE
//        try {
//            Log.d(Constants.LOG_TAG,"LAT INDEX : ${subscriberList.size}")
//            if(subscriberList.isEmpty())
//            {
//                subscriber_container.removeAllViews()
//                tv_lbl_wait_message.visibility = View.VISIBLE
//               // showShortToast("Checking for incoming streams",this)
//                return
//            }
//            if(subscriberList.size>1)
//            {
//                subscriber_container.removeAllViews()
//                subscriberview0.removeAllViews()
//                subscriberview0.visibility = View.VISIBLE
//                subscriberview0.addView(subscriberList[0].view)
//
//                subscriberview1.removeAllViews()
//                subscriberview1.visibility = View.GONE
//
//                subscriber_container.addView(subscriberList[1].view)
//
//
//            }
//            else
//            {
//                subscriberview0.removeAllViews()
//                subscriberview0.visibility = View.GONE
//                subscriber_container.removeAllViews()
//                subscriber_container.addView(subscriberList[0].view)
//            }
//            //subscriber_container.addView(subscriberview1.getChildAt(0))
//        }
//        catch (ae : Exception)
//        {
//            Log.e(Constants.LOG_TAG,ae.localizedMessage.toString())
//        }
//    }
//
//    override fun onStreamDestroyed(p0: PublisherKit?, p1: Stream?) {
//        Log.i(Constants.LOG_TAG, "Publisher onStreamDestroyed");
//
//    }
//
//    override fun onStreamDropped(session: Session?, stream: Stream?) {
//        Log.i(Constants.LOG_TAG, "Stream Dropped")
//        var subscriber: Subscriber? = mSubscriberStreams[stream] ?: return
//
//        subscriberList.remove(subscriber)
//        mSubscriberStreams.remove(stream)
//        resurfaceView()
//
//
//    }
//
//    fun getResIdForSubscrberIndex(index : Int) : Int{
//        var arr = resources.obtainTypedArray(R.array.subscriber_view_ids)
//        var subId = arr.getResourceId(index,0)
//        arr.recycle()
//        return subId
//    }
//
//    override fun onStreamReceived(session: Session?, stream: Stream?) {
//        Log.i(Constants.LOG_TAG, "Stream Received")
//        Log.d(Constants.LOG_TAG, "onStreamReceived: New stream " + stream!!.streamId + " in session " + session!!.sessionId);
//
//        val subscriber = Subscriber.Builder(this, stream).build()
//        mSession!!.subscribe(subscriber)
//        subscriberList.add(subscriber)
//        mSubscriberStreams[stream] = subscriber
//
//        subscriber.setStyle(BaseVideoRenderer.STYLE_VIDEO_SCALE,BaseVideoRenderer.STYLE_VIDEO_FILL)
//        if(subscriber_container.childCount==0)
//        {
//            tv_lbl_wait_message.visibility = View.GONE
//            subscriber_container.removeAllViews()
//            subscriber_container.addView(subscriber.view)
//        }
//        else
//        {
//            resurfaceView()
//        }
//
//
//        Log.d(Constants.LOG_TAG,subscriberview1.childCount.toString()+" -- "+subscriberview0.childCount)
//
//
//    }
//
//
//
//
//    override fun onConnected(subscriberKit: SubscriberKit?) {
//        Log.d(Constants.LOG_TAG, "onConnected: Subscriber connected. Stream: "+subscriberKit!!.stream.streamId)
//    }
//
//    override fun onDisconnected(subscriberKit: SubscriberKit?) {
//        Log.d(Constants.LOG_TAG, "onDisconnected: Subscriber disconnected. Stream: "+subscriberKit!!.stream.streamId)
//    }
//
//    override fun onError(p0: SubscriberKit?, opentokError: OpentokError) {
//
//        Log.e(Constants.LOG_TAG, "onError: "+opentokError.errorDomain + " : " +
//                opentokError.errorCode +  " - "+opentokError.message
//        )
//
//    }
//
//
//
//    override fun onConnected(p0: Session?) {
//        Log.i(Constants.LOG_TAG, "Session Connected")
//        mPublisher = Publisher.Builder(this).build()
//        mPublisher!!.publishAudio = true
//
//        mPublisher!!.setPublisherListener(this)
//        mPublisher!!.renderer.setStyle(BaseVideoRenderer.STYLE_VIDEO_SCALE,BaseVideoRenderer.STYLE_VIDEO_FILL)
//        publisher_container.addView(mPublisher!!.view)
//        /*if (mPublisher!!.view is GLSurfaceView) {
//            (mPublisher!!.view as GLSurfaceView).setZOrderOnTop(true)
//        }*/
//      //  mPublisher!!.publisherVideoType = PublisherKit.PublisherKitVideoType.PublisherKitVideoTypeScreen
//        Log.d(Constants.LOG_TAG,mPublisher!!.publisherVideoType.toString())
//        mSession!!.publish(mPublisher)
//
//        configureChatSession()
//    }
//
//    override fun onSignalReceived(session: Session?, type: String?, data: String?, connection: Connection?) {
//
//        val remote = connection!! == mSession!!.connection
//        if(SIGNAL_TYPE.isNotBlank() && type.equals(SIGNAL_TYPE))
//        {
//            showMessage(data,remote)
//        }
//
//
//    }
//
//    private fun showMessage(data: String?, remote: Boolean) {
//        var message = SignalMessage(data,remote)
//        mMessageAdapter.add(message)
//        lv_chat_message.setSelection(mMessageAdapter.count - 1)
//    }
//
//    private fun configureChatSession() {
//        mSession!!.setSignalListener(this)
//    }
//
//
//    override fun onDisconnected(p0: Session?) {
//        Log.i(Constants.LOG_TAG, "Session Disconnected");
//    }
//
//    override fun onError(p0: Session?, opentokError: OpentokError?) {
//        Log.e(Constants.LOG_TAG, "Session error: " + opentokError!!.message);
//    }
//
//    override fun onError(p0: PublisherKit?, opentokError: OpentokError?) {
//        Log.e(Constants.LOG_TAG, "Publisher error: " + opentokError!!.message);
//    }
//
//
//
//
//    private fun disconnectSession()
//    {
//        mSession!!.disconnect()
//        finish()
//    }
//
//


}




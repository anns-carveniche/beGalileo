package com.carveniche.begalileo.ui.fragments


import android.Manifest
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.DialogFragment

import com.carveniche.begalileo.R
import com.carveniche.begalileo.contract.QuizPlayContract
import com.carveniche.begalileo.di.component.DaggerFragmentComponent
import com.carveniche.begalileo.di.module.ContextModule
import com.carveniche.begalileo.di.module.Fragmentmodule
import com.carveniche.begalileo.ui.activities.QuizActivity
import com.carveniche.begalileo.util.Constants
import com.carveniche.begalileo.util.SaveImage
import com.carveniche.begalileo.util.loadBitmapFromView
import com.carveniche.begalileo.util.showShortToast
import kotlinx.android.synthetic.main.fragment_quiz_play.*
import kotlinx.android.synthetic.main.fragment_quiz_play.view.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject


class QuizPlayFragment : Fragment(),QuizPlayContract.View,EasyPermissions.PermissionCallbacks {


    @Inject lateinit var presenter : QuizPlayContract.Presenter
    private lateinit var rootView : View
    private lateinit var bounceAnimation : Animation
    private lateinit var fadeOutAnimation : Animation
    private lateinit var roughImageBitmap: Bitmap
    private lateinit var quizActivity : QuizActivity
    val RC_WRITE_APP_PERM = 124
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        quizActivity = activity as QuizActivity
        injectDependency()
        bounceAnimation = AnimationUtils.loadAnimation(context,R.anim.anim_bounce_in)
        fadeOutAnimation = AnimationUtils.loadAnimation(context,R.anim.anim_fade_out)

    }
    private fun injectDependency() {
        val fragmentComponent = DaggerFragmentComponent.builder()
            .fragmentmodule(Fragmentmodule())
            .contextModule(ContextModule(context!!))
            .build()
        fragmentComponent.inject(this)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_quiz_play, container, false)
        initUI()
        return rootView
    }

    private fun initUI() {
        rootView.iv_rough_board.setOnClickListener {
            showRoughBoard(true)
        }
        rootView.iv_close_rough_board.setOnClickListener {
            showRoughBoard(false)
        }
        rootView.btn_next.setOnClickListener {
           roughImageBitmap =  loadBitmapFromView(rough_board_view)

            requestPermission()
            quizActivity.showQuizHomeFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attach(this)
    }

    override fun showProgress(boolean: Boolean) {

    }
    companion object {
        var TAG = "QuizPlayFragment"
    }


    private fun showRoughBoard(show : Boolean)
    {
        if(show)
        {
          //  rl_rough_board_container.animation = bounceAnimation

            rl_rough_board_container.visibility = View.VISIBLE

        }
        else
        {
            //rl_rough_board_container.animation = fadeOutAnimation
            rl_rough_board_container.visibility = View.GONE

        }

    }

    @AfterPermissionGranted(124)
    fun requestPermission(){
        val perms = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if(EasyPermissions.hasPermissions(context!!,*perms))
        {
           saveImageInLocal()
        }
        else
        {
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.video_audio_permission),
                RC_WRITE_APP_PERM,
                *perms
            )
        }
    }
        override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
            Log.d(Constants.LOG_TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size);
        }

        override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
            Log.d(Constants.LOG_TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size);
            saveImageInLocal()
        }


    fun saveImageInLocal()
    {
        quizActivity.listRoughBitmap.add(roughImageBitmap)
       // SaveImage(roughImageBitmap)
      //  showShortToast("Image Saved In local",context!!)
    }

}

package com.carveniche.begalileo.ui.fragments


import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.carveniche.begalileo.R
import com.carveniche.begalileo.contract.QuizHomeContract
import com.carveniche.begalileo.di.component.DaggerFragmentComponent
import com.carveniche.begalileo.di.module.ContextModule
import com.carveniche.begalileo.di.module.Fragmentmodule
import com.carveniche.begalileo.ui.activities.QuizActivity
import com.carveniche.begalileo.util.SaveImage
import com.carveniche.begalileo.util.loadBitmapFromView
import com.carveniche.begalileo.util.showShortToast
import kotlinx.android.synthetic.main.fragment_quiz_home.*
import kotlinx.android.synthetic.main.fragment_quiz_home.view.*
import javax.inject.Inject


class QuizHomeFragment : Fragment(),QuizHomeContract.View {

    @Inject
    lateinit var presenter : QuizHomeContract.Presenter
    private lateinit var rootView : View
    private lateinit var quizActivity : QuizActivity
    private lateinit var roughImageBitmap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
        quizActivity = activity as QuizActivity
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
        rootView =  inflater.inflate(R.layout.fragment_quiz_home, container, false)
        initUI()
        return rootView
    }

    private fun initUI() {

        rootView.iv_rough_board.setOnClickListener {
            showRoughBoard(true)
        }
        var ob =  BitmapDrawable(resources,quizActivity.listRoughBitmap[0])
        rootView.rl_student_rough_conatiner.background = ob
        rootView.iv_close_rough_board.setOnClickListener {
            showRoughBoard(false)
        }
        rootView.btn_next.setOnClickListener {
            roughImageBitmap =  loadBitmapFromView(rl_verified_board)
           // SaveImage(roughImageBitmap)
            quizActivity.verifiedImage = roughImageBitmap
          //  showShortToast("Image Saved In local",context!!)
            quizActivity.showQuizResultFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attach(this)
    }

    override fun showProgress(boolean: Boolean) {

    }
    companion object {
        var TAG = "QuizHomeFragment"
    }



}

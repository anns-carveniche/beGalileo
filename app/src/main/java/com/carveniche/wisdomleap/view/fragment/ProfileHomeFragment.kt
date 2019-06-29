package com.carveniche.wisdomleap.view.fragment


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.GridView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.carveniche.wisdomleap.R
import com.carveniche.wisdomleap.adapter.AvatarListAdapter
import com.carveniche.wisdomleap.contract.ProfileHomeContract
import com.carveniche.wisdomleap.di.component.DaggerFragmentComponent
import com.carveniche.wisdomleap.di.module.ContextModule
import com.carveniche.wisdomleap.di.module.FragmentModule
import com.carveniche.wisdomleap.di.module.SharedPreferenceModule
import com.carveniche.wisdomleap.model.MySharedPreferences
import com.carveniche.wisdomleap.model.StudentProfileModel
import com.carveniche.wisdomleap.model.UserCoinModel
import com.carveniche.wisdomleap.util.Constants
import com.carveniche.wisdomleap.util.showLoadingProgress
import com.carveniche.wisdomleap.util.showLongToast
import com.carveniche.wisdomleap.util.showShortToast
import com.carveniche.wisdomleap.view.activity.MainActivity
import kotlinx.android.synthetic.main.dialog_avatar_list.view.*
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import kotlinx.android.synthetic.main.fragment_edit_profile.view.*
import kotlinx.android.synthetic.main.fragment_profile_home.*
import kotlinx.android.synthetic.main.layout_progressbar.*
import kotlinx.android.synthetic.main.nav_header.*
import javax.inject.Inject


class ProfileHomeFragment : Fragment(), ProfileHomeContract.View {



    private lateinit var rootView: View
    @Inject
    lateinit var presenter: ProfileHomeContract.Presenter
    @Inject
    lateinit var mySharedPreferences: MySharedPreferences
    var studentId = 0
    var isEdit = false
    var imageId = 0
    private lateinit var studentProfileModel: StudentProfileModel
    private lateinit var dialog : DialogFragment
    private lateinit var mainActivity: MainActivity


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_profile_home, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity = activity as MainActivity
        initUI()

        presenter.attach(this)
        presenter.subscribe()
        presenter.loadProfileDatas(studentId)
        presenter.loadUserCoins(studentId)

    }

    private fun initUI() {
        imageId = mySharedPreferences.getIntData(Constants.AVATAR_IMAGE_ID)
        setProfileImage()
        btn_edit_profile.setOnClickListener {
            openEditProfileDialog()
        }
        iv_edit_avatar.setOnClickListener {
            openEditAvatarDialog()
        }


    }

    private fun setProfileImage() {
        if(imageId==0)
        {
            iv_profile_image.setImageDrawable(context!!.getDrawable(R.drawable.ic_profile))
        }
        else
        {
            iv_profile_image.setImageDrawable(context!!.getDrawable(imageId))
        }
    }
    override fun updateProfileAvatar(imageId: Int) {
        Log.d(Constants.LOG_TAG,"Image $imageId")
        this.imageId = imageId
        dialog.dismiss()
        mySharedPreferences.putIntData(Constants.AVATAR_IMAGE_ID,imageId)
        setProfileImage()
        mainActivity.setDrawerHeaderDetails()
    }

    private fun openEditAvatarDialog() {
        var ft = childFragmentManager.beginTransaction()
        var prev = childFragmentManager.findFragmentByTag("avatarDialog")
        if (prev != null)
            ft.remove(prev)
        ft.addToBackStack(null)

        dialog = AvatarListDialogFragment(this)
        dialog.show(ft, "avatarDialog")
    }

    private fun openEditProfileDialog() {
        var ft = childFragmentManager.beginTransaction()
        var prev = childFragmentManager.findFragmentByTag("editDialog")
        if (prev != null)
            ft.remove(prev)
        ft.addToBackStack(null)
        var dialogFragment = EditProfileDialogFragment(studentId,studentProfileModel,presenter)
        dialogFragment.show(ft, "editDialog")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
        studentId = mySharedPreferences.getIntData(Constants.STUDENT_ID)
    }

    override fun showProgress(show: Boolean) {
        showLoadingProgress(progressBar, show)
    }

    companion object {
        const val TAG = "ProfileHomeContract"
    }

    private fun injectDependency() {
        val fragmentComponent = DaggerFragmentComponent.builder()
            .fragmentModule(FragmentModule())
            .sharedPreferenceModule(SharedPreferenceModule())
            .contextModule(ContextModule(context!!))
            .build()
        fragmentComponent.inject(this)
    }
    override fun onLoadCoinSuccess(userCoinModel: UserCoinModel) {
        tv_gold_coins.text = "${userCoinModel.coins} Coins"
    }

    override fun onLoadCoinFailed(error: String) {

    }
    override fun onProfileLoadSucess(studentProfileModel: StudentProfileModel) {
        Log.d(Constants.LOG_TAG, studentProfileModel.toString())
        this.studentProfileModel = studentProfileModel
        tv_user_name.text = """${studentProfileModel.first_name} ${studentProfileModel.last_name}"""
        tv_school.text = studentProfileModel.school_name
        tv_grade.text = studentProfileModel.grade_name
        tv_city.text = studentProfileModel.place
        tv_email.text = studentProfileModel.email
        tv_contact_number.text = studentProfileModel.contact_no

        edFirstName.setText(studentProfileModel.first_name)
        edLastName.setText(studentProfileModel.last_name)
        edSchoolName.setText(studentProfileModel.school_name)
        tvPlaceName.setText(studentProfileModel.place)
        tvGrade.setText(studentProfileModel.grade_name)
        tvEmail.setText(studentProfileModel.email)
        tvContactNumber.setText(studentProfileModel.contact_no)
    }

    override fun onProfileLoadFailed(msg: String) {
        Log.d(Constants.LOG_TAG, msg)
    }

    override fun submitProfileDataSuccess(firstName: String, lastName: String, schoolName: String) {
        showShortToast("Profile updated successfully",context!!)
        presenter.loadProfileDatas(studentId)
        mySharedPreferences.putString(Constants.FIRST_NAME,firstName)
        mySharedPreferences.putString(Constants.LAST_NAME,lastName)
        mainActivity.setDrawerHeaderDetails()
    }

    override fun submitProfileDataFailed(msg: String) {
        showLongToast(msg,context!!)
    }


    override fun onPause() {
        super.onPause()
        presenter.unSubscribe()
    }

    class EditProfileDialogFragment(
        var studentId: Int,
        var studentProfileModel: StudentProfileModel,
        var profileHomePresenter: ProfileHomeContract.Presenter
    ) : DialogFragment() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setStyle(STYLE_NO_FRAME, android.R.style.Theme_Holo_Light)
        }

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            var view = inflater.inflate(R.layout.fragment_edit_profile, container, false)
            view.edFirstName.setText(studentProfileModel.first_name)
            view.edLastName.setText(studentProfileModel.last_name)
            view.edSchoolName.setText(studentProfileModel.school_name)
            view.btnEditProfile.setOnClickListener {
                profileHomePresenter.submitProfileDatas(studentId,view.edFirstName.text.toString(),view.edLastName.text.toString(),view.edSchoolName.text.toString())
                dialog.dismiss()
            }
            return view
        }
    }

    class AvatarListDialogFragment(var profileView : ProfileHomeContract.View) : DialogFragment()
    {
        var imageList = mutableListOf<Int>()
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            loadImageDatas()
        }

        private fun loadImageDatas() {
            imageList.add(R.drawable.avatar_1)
            imageList.add(R.drawable.avatar_2)
            imageList.add(R.drawable.avatar_3)
            imageList.add(R.drawable.avatar_4)
            imageList.add(R.drawable.avatar_5)
            imageList.add(R.drawable.avatar_6)
            imageList.add(R.drawable.avatar_7)
            imageList.add(R.drawable.avatar_8)
            imageList.add(R.drawable.avatar_9)
            imageList.add(R.drawable.avatar_10)
            imageList.add(R.drawable.avatar_11)
            imageList.add(R.drawable.avatar_12)
        }

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            var view = inflater.inflate(R.layout.dialog_avatar_list,container,false)
            var gvList = view.findViewById<GridView>(R.id.gridContainer)
            gvList.adapter = AvatarListAdapter(context!!,imageList,profileView)

            return view
        }
    }


}


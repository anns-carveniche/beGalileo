package com.carveniche.begalileo.ui.main

import com.carveniche.begalileo.ui.base.BaseContract

class MainContract  {

    interface View: BaseContract.View
    {
        fun showHomeFragment()
        fun showPracticeFragment()
    }
    interface Presenter: BaseContract.Presenter<MainContract.View>
    {
        fun onDrawerOptionsClick()
    }

}
package com.carveniche.begalileo.contract

class MainContract  {

    interface View: BaseContract.View
    {
        fun showHomeFragment()
        fun showPracticeFragment()
    }
    interface Presenter: BaseContract.Presenter<View>
    {
        fun onDrawerOptionsClick()
    }

}
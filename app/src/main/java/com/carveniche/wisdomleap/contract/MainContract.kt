package com.carveniche.wisdomleap.contract

import com.carveniche.wisdomleap.base.BaseContract

class MainContract {
    interface View : BaseContract.View
    {
        fun showDashboardFragment()
        fun showQuizFragment()
        fun showProfileFragment()
        fun showNotificationFragment()
    }
    interface Presenter : BaseContract.Presenter<View>
    {

    }
}
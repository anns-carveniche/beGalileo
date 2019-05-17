package com.carveniche.wisdomleap.contract

import com.carveniche.wisdomleap.base.BaseContract

class DashboardContract {
    interface View : BaseContract.View{

    }
    interface Presenter : BaseContract.Presenter<DashboardContract.View>
    {

    }
}
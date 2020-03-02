package com.carveniche.begalileo.contract

import com.carveniche.begalileo.models.DashboardDataModel

class HomeContract  {

    interface View : BaseContract.View
    {
        fun loadMessageSuccess(message: String)
        fun onItemClick(position : Int)
        fun onDashboardLoadSuccess(dashboardDataModel: DashboardDataModel)
        fun onDashboardLoadFailed(msg : String)

    }
    interface Presenter : BaseContract.Presenter<View>
    {
        fun loadMessage()
        fun loadDashboardDatas(parentId : Int)
    }
}
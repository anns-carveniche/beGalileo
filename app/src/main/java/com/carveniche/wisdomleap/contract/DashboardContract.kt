package com.carveniche.wisdomleap.contract

import com.carveniche.wisdomleap.base.BaseContract
import com.carveniche.wisdomleap.model.SubjectListModel

class DashboardContract {
    interface View : BaseContract.View{
        fun onLoadDataSucess(subjectListModel: SubjectListModel)
        fun onLoadDataFailed(msg : String)
    }
    interface Presenter : BaseContract.Presenter<DashboardContract.View>
    {
        fun loadDashboardData(studentId : Int)
    }
}
package com.carveniche.wisdomleap.contract

import com.carveniche.wisdomleap.base.BaseContract
import com.carveniche.wisdomleap.model.SubjectListModel

class ListSubjctContract {
    interface View : BaseContract.View
    {
        fun onSubjectLoadSucess(subjectListModel: SubjectListModel)
        fun onSubjectLoadFailed(msg : String)
    }
    interface Presenter : BaseContract.Presenter<ListSubjctContract.View>
    {
        fun loadSubjectData(studentId : Int)
    }
}
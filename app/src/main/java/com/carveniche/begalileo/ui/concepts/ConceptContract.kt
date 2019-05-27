package com.carveniche.begalileo.ui.concepts

import com.carveniche.begalileo.ui.base.BaseContract

class ConceptContract {
    interface View : BaseContract.View
    {
        fun showConceptHome()
        fun showConceptList(boardId:Int,gradeId : Int)
    }
    interface Presenter  : BaseContract.Presenter<ConceptContract.View>
    {

    }

}
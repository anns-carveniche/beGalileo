package com.carveniche.begalileo.contract

class ConceptContract {
    interface View : BaseContract.View
    {
        fun showConceptHome()
        fun showConceptList(boardId:Int,gradeId : Int)
    }
    interface Presenter  : BaseContract.Presenter<View>
    {

    }

}
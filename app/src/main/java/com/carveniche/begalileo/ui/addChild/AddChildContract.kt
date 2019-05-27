package com.carveniche.begalileo.ui.addChild

import com.carveniche.begalileo.models.AddChildModel
import com.carveniche.begalileo.models.GradeBoardModel
import com.carveniche.begalileo.ui.base.BaseContract
import io.reactivex.Observable
import java.lang.Exception


class AddChildContract
{
    interface View: BaseContract.View{
        fun loadDataSucess(gradeBoardModel: GradeBoardModel)
        fun loadDataError(msg : String)

        fun name(): Observable<CharSequence>
        fun updateNameButtonState(state: Boolean)
        fun schoolName(): Observable<CharSequence>
        fun schoolCity() : Observable<CharSequence>
        fun updateSchoolSubmitButtonState(state: Boolean)
        fun addChildSuccess(childModel: AddChildModel)
        fun addChildFailed(msg: String)
    }
    interface Presenter: BaseContract.Presenter<AddChildContract.View>
    {
        fun loadData()
        fun addChild(parentId: Int,
                     firstName: String,
                     lastName: String,
                     gender: String,
                     grade: Int,
                     goal: String,
                     board: Int,
                     schoolName: String,
                     schoolCity: String)
    }

}
package com.carveniche.wisdomleap.contract

import com.carveniche.wisdomleap.base.BaseContract
import com.carveniche.wisdomleap.model.ChapterListModel

class ListChapterContract {
    interface View : BaseContract.View
    {
        fun onLoadDataSucess(chapterList : ChapterListModel)
        fun onLoadDataError(msg : String)
        fun onChapterClick(conceptId : Int,subconceptId : Int,videoUrl : String)
    }
    interface Presenter : BaseContract.Presenter<ListChapterContract.View>
    {
        fun loadData(studentId : Int,courseId : Int)
    }
}
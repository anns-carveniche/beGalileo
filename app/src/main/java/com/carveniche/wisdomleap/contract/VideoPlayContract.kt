package com.carveniche.wisdomleap.contract

import com.carveniche.wisdomleap.base.BaseContract
import com.carveniche.wisdomleap.model.ChapterVideosModel
import java.lang.Error

class VideoPlayContract {
    interface View : BaseContract.View
    {
        fun onChapterLoadSuccess(chapterVideosModel: ChapterVideosModel)
        fun onChapterLoadFailed(error : String)
    }
    interface Presenter : BaseContract.Presenter<VideoPlayContract.View>
    {
        fun updateVideoStatus(studentId : Int,courseId : Int,conceptId : Int,subConceptId : Int,isCompleted : Boolean,duration : Long)
        fun loadChapterVideos(studentId: Int,chapterId : Int)
    }
}
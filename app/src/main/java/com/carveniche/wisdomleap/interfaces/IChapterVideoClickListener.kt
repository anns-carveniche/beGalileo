package com.carveniche.wisdomleap.interfaces

interface IChapterVideoClickListener {
    fun onChapterClick(conceptId : Int,subconceptId : Int,videoUrl : String,videoTitle : String)
}
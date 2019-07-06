package com.carveniche.wisdomleap.model

data class SubjectListModel(
    val course_details: List<CourseDetail>,
    val inprogress_practice: List<InprogressPractice>,
    val inprogress_video: List<InprogressVideo>,
    val message: String,
    val recent_practice: List<RecentPractice>,
    val recent_video: List<RecentVideo>,
    val status: Boolean
)
{
    constructor() : this(emptyList(), emptyList(), emptyList(),"", emptyList(), emptyList(),false)
}


data class RecentPractice(
    val chapter_id: Int,
    val chapter_name: String,
    val course_id: Int,
    val quiz_id: Int
)

data class RecentVideo(
    val chapter_id: Int,
    val course_id: Int,
    val image: String,
    val link: String,
    val name: String,
    val sub_concept_id: Int
)

data class InprogressVideo(
    val chapter_id: Int,
    val course_id: Int,
    val image: String,
    val link: String,
    val name: String,
    val sub_concept_id: Int
)

data class CourseDetail(
    val course_id: Int,
    val image_url: String,
    val chapters_count : Int,
    val lessons_count : Int,
    val name: String
)

data class InprogressPractice(
    val chapter_id: Int,
    val chapter_name: String,
    val course_id: Int,
    val quiz_id: Int
)
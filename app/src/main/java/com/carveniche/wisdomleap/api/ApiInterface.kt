package com.carveniche.wisdomleap.api

import com.carveniche.wisdomleap.model.*
import com.carveniche.wisdomleap.util.Constants
import com.carveniche.wisdomleap.util.URL
import com.google.gson.JsonElement
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiInterface {
    @GET("api.php?amount=15&difficulty=easy&type=multiple")
    fun getQuizQuestions() : Observable<QuizQuestionModel>

    @FormUrlEncoded
    @POST("app_users/register")
    fun registerMobileNumber(
        @Field("country_code") countryCode : String,
        @Field("mobile") mobile: String

                             ) : Observable<RegisterModel>

    @FormUrlEncoded
    @POST("app_users/resend_otp")
    fun resendOTP(@Field("mobile") mobile: String) : Observable<RegisterModel>

    @FormUrlEncoded
    @POST("app_users/update_email")
    fun updateEmail(@Field("mobile") mobile: String,
                    @Field("email") email: String) : Observable<RegisterModel>

    @FormUrlEncoded
    @POST("app_users/update_email")
    fun submitRegsiterDetails(@Field("mobile") mobile: String,
                              @Field("email") email: String,
                              @Field("first_name") firstName: String,
                              @Field("last_name") lastName: String,
                              @Field("grade_id") gradeId : Int,
                              @Field("city") city: String) : Observable<RegisterModel>

    @GET("app_users/grade_details")
    fun gradeList() : Observable<GradeListModel>

    @GET("app_students/dashboard")
    fun getSubjectList(@Query("student_id") studentId : Int) : Observable<SubjectListModel>

    @GET("app_students/view_profile")
    fun getStudentProfile(@Query("student_id") studentId : Int) : Observable<StudentProfileModel>

    @GET("app_students/course_details")
    fun getChapterList(@Query("student_id") studentId : Int,
                       @Query("course_id") courseId : Int
                       ) : Observable<ChapterListModel>

    @POST("app_students/start_quiz")
    fun getChapterQuizQuestions(@Query("student_id") studentId : Int,
                                @Query("course_id") courseId : Int,
                                @Query("chapter_id") chapterId : Int
                                ) : Observable<ChapterQuizModel>

    @POST("app_students/save_quiz")
    fun saveQuiz(@Query("student_id") studentId : Int,
                 @Query("quiz_id") quizId : Int,
                 @Query("question_id") questionId : Int,
                 @Query("question_index") questionIndex : Int,
                 @Query("choice_id") choiceId : Int,
                 @Query("time_spent") timeSpent: Int,
                 @Query("is_correct") isCorrect : Boolean,
                 @Query("is_completed") isCompleted : Boolean
    ) : Observable<JsonElement>


    @POST("app_students/video_logs")
    fun videoStatus(@Query("student_id") studentId : Int,
                    @Query("course_id") courseId: Int,
                    @Query("chapter_id") conceptId : Int,
                    @Query("sub_concept_id") subConceptId : Int,
                    @Query("is_completed") isCompleted : Boolean,
                    @Query("duration") duration : Long
    ) : Observable<JsonElement>


    @POST("app_students/save_category_quiz")
    fun saveCategoryQuiz(@Query("student_id") studentId : Int,
                 @Query("category_id") categoryId : Int,
                 @Query("level") level : String,
                 @Query("total") total : Int,
                 @Query("correct") correct : Int,
                 @Query("time_spent") timeSpent : Int
    ) : Observable<JsonElement>

    @POST("app_students/update_profile")
    fun updateProfile(@Query("student_id") studentId : Int,
                         @Query("first_name") firstName : String,
                         @Query("last_name") lastName : String,
                         @Query("school_name") schoolName : String
    ) : Observable<BasicResponseModel>



    companion object {
        fun create():ApiInterface {
            var httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            var client = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()
            val retrofit = retrofit2.Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(URL.BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }
}
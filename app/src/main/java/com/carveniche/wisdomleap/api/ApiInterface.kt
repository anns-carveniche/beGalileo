package com.carveniche.wisdomleap.api

import com.carveniche.wisdomleap.model.*
import com.carveniche.wisdomleap.util.Constants
import com.carveniche.wisdomleap.util.URL
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiInterface {
    @GET("api.php?amount=15&difficulty=easy&type=multiple")
    fun getQuizQuestions() : Observable<QuizQuestionModel>

    @FormUrlEncoded
    @POST("app_users/register")
    fun registerMobileNumber(@Field("mobile") mobile: String) : Observable<RegisterModel>

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
                              @Field("grade_id") gradeId : Int,
                              @Field("city") city: String) : Observable<RegisterModel>

    @GET("app_users/grade_details")
    fun gradeList() : Observable<GradeListModel>

    @GET("app_students/dashboard")
    fun getSubjectList(@Query("student_id") studentId : Int) : Observable<SubjectListModel>

    @GET("app_students/course_details")
    fun getChapterList(@Query("student_id") studentId : Int,
                       @Query("course_id") courseId : Int
                       ) : Observable<ChapterListModel>



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
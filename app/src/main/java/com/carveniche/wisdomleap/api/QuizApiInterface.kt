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

interface QuizApiInterface {


    @GET("api.php")
    fun getQuizQuestions(@Query("amount") amount : Int,
                       @Query("category") category : Int,
                       @Query("difficulty") difficulty: String,
                       @Query("type") type: String
                       ) : Observable<QuizQuestionModel>



    companion object {
        fun create():QuizApiInterface {
            var httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            var client = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()
            val retrofit = retrofit2.Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(URL.DEMO_QUIZ_URL)
                .build()
            return retrofit.create(QuizApiInterface::class.java)
        }
    }
}
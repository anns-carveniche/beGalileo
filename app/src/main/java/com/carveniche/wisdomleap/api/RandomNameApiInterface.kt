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

interface RandomNameApiInterface {


    @GET("api/")
    fun getRandomName(@Query("region") region: String) : Observable<RandomNameModel>



    companion object {
        fun create():RandomNameApiInterface {
            var httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            var client = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()
            val retrofit = retrofit2.Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(URL.RANDOM_NAME_URL)
                .build()
            return retrofit.create(RandomNameApiInterface::class.java)
        }
    }
}
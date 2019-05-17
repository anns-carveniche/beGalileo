package com.carveniche.wisdomleap.di.module

import com.carveniche.wisdomleap.BuildConfig
import com.carveniche.wisdomleap.api.ApiInterface
import com.carveniche.wisdomleap.util.URL
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
open class HttpModule {

    @Provides
    @Singleton
    open fun provideHttpLogging(): OkHttpClient{
        val logging = HttpLoggingInterceptor()
        logging.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    open fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit = Retrofit.Builder()
        .baseUrl(URL.DEMO_QUIZ_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    open fun provideApiService(retrofit: Retrofit) : ApiInterface = retrofit.create(ApiInterface::class.java)
}
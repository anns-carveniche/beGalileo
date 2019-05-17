package com.carveniche.wisdomleap.di.module

import android.app.Application
import android.content.Context
import android.view.inputmethod.InputMethodManager
import dagger.Module

import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application : Application) {

    @Provides
    @Singleton
    fun provideContext() : Application = this.application

    @Provides
    @Singleton
    fun provideInputMethod() = application.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
}
package com.carveniche.begalileo.di.module

import android.content.Context

import android.content.SharedPreferences
import com.carveniche.begalileo.Constants
import com.carveniche.begalileo.di.scope.PerApplication
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module
class SharedPreferenceModule {

    @Provides
    @Singleton
    @Inject
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(Constants.MY_PREFS, Context.MODE_PRIVATE)
    }
}
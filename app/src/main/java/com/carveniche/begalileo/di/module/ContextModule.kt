package com.carveniche.begalileo.di.module

import android.content.Context
import com.carveniche.begalileo.di.scope.PerApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContextModule(private var context: Context) {
    @Provides
    @Singleton
    fun provideContext() : Context
    {
        return context
    }
}
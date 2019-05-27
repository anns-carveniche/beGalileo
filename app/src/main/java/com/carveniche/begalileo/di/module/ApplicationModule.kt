package com.carveniche.begalileo.di.module

import android.app.Application
import com.carveniche.begalileo.util.BaseApp
import com.carveniche.begalileo.di.scope.PerApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private var baseApp: BaseApp) {

    @Provides
    @Singleton
    @PerApplication
    fun provideApplication(): Application{
        return baseApp
    }
}
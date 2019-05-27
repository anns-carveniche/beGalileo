package com.carveniche.begalileo

import android.app.Application
import com.carveniche.begalileo.di.component.ApplicationComponent
import com.carveniche.begalileo.di.component.DaggerApplicationComponent
import com.carveniche.begalileo.di.module.ApplicationModule
import com.carveniche.begalileo.di.module.ContextModule
import com.carveniche.begalileo.di.module.SharedPreferenceModule
import dagger.internal.DaggerCollections


class BaseApp : Application() {

    lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        instance  = this
        setup()
    }

    private fun setup() {
        component = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
        component.inject(this)
    }

    fun getApplicationComponent(): ApplicationComponent
    {
        return component
    }

    companion object {
        lateinit var instance : BaseApp private set
    }
}

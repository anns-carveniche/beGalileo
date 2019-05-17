package com.carveniche.wisdomleap

import android.app.Application
import com.carveniche.wisdomleap.di.component.ApplicationComponent
import com.carveniche.wisdomleap.di.component.DaggerApplicationComponent
import com.carveniche.wisdomleap.di.module.ApplicationModule


class BaseApp : Application() {

    lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        setup()
    }

    private fun setup() {
        component = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
        component.inject(this)
    }

    companion object {
        lateinit var instance : BaseApp private set
    }

}
package com.carveniche.begalileo.di.component

import com.carveniche.begalileo.util.BaseApp
import com.carveniche.begalileo.di.module.ApplicationModule
import dagger.Component


@Component(modules = [ApplicationModule::class])
interface ApplicationComponent{
    fun inject(application: BaseApp)
}

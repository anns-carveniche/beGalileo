package com.carveniche.begalileo.di.component

import com.carveniche.begalileo.BaseApp
import com.carveniche.begalileo.di.module.ApplicationModule
import com.carveniche.begalileo.di.module.ContextModule
import com.carveniche.begalileo.di.module.SharedPreferenceModule
import dagger.Component


@Component(modules = [ApplicationModule::class])
interface ApplicationComponent{
    fun inject(application:BaseApp)
}

package com.carveniche.wisdomleap.di.component

import com.carveniche.wisdomleap.BaseApp
import com.carveniche.wisdomleap.di.module.ApplicationModule
import com.carveniche.wisdomleap.di.module.HttpModule
import com.carveniche.wisdomleap.di.module.RxThreadModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [HttpModule::class,
                        ApplicationModule::class,
                        RxThreadModule::class])
interface ApplicationComponent {
   fun inject(application : BaseApp)
}
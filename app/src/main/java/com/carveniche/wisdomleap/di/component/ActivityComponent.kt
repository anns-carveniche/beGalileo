package com.carveniche.wisdomleap.di.component

import com.carveniche.wisdomleap.di.PerApplication
import com.carveniche.wisdomleap.di.module.ActivityModule
import com.carveniche.wisdomleap.di.module.ContextModule
import com.carveniche.wisdomleap.di.module.HttpModule
import com.carveniche.wisdomleap.di.module.SharedPreferenceModule
import com.carveniche.wisdomleap.view.activity.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@PerApplication
@Component(modules = [ActivityModule::class,ContextModule::class,SharedPreferenceModule::class])
interface ActivityComponent{
    fun inject(mainActivity: MainActivity)
    fun inject(loginActivity: LoginActivity)
    fun inject(registerActivity: RegisterActivity)
    fun inject(subjectActivity: SubjectActivity)
    fun inject(quizActivity: QuizActivity)
    fun inject(splashActivity: SplashActivity)
    fun inject(videoPlayActivity: VideoPlayActivity)
}
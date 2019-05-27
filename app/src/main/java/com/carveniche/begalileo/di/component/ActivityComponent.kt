package com.carveniche.begalileo.di.component

import com.carveniche.begalileo.di.module.ActivityModule
import com.carveniche.begalileo.di.module.ContextModule
import com.carveniche.begalileo.di.module.SharedPreferenceModule
import com.carveniche.begalileo.di.scope.PerApplication
import com.carveniche.begalileo.ui.activities.SplashActivity
import com.carveniche.begalileo.ui.activities.LoginActivity
import com.carveniche.begalileo.ui.activities.MainActivity
import com.carveniche.begalileo.ui.activities.AddChildActivity
import com.carveniche.begalileo.ui.activities.ConceptActivity
import com.carveniche.begalileo.ui.activities.NearbyCenterActivity
import com.carveniche.begalileo.ui.activities.SpeedMathActivity
import com.carveniche.begalileo.ui.activities.GameResultActivity
import com.carveniche.begalileo.ui.activities.UserLocationActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ActivityModule::class, SharedPreferenceModule::class, ContextModule::class])
@PerApplication
interface ActivityComponent{
    fun inject(mainActivity: MainActivity)
    fun inject(loginActivity: LoginActivity)
    fun inject(splashActivity: SplashActivity)
    fun inject(addChildActivity: AddChildActivity)
    fun inject(userLocationActivity: UserLocationActivity)
    fun inject(conceptActivity: ConceptActivity)
    fun inject(nearbyCenterActivity: NearbyCenterActivity)
    fun inject(speedMathActivity: SpeedMathActivity)
    fun inject(gameResultActivity: GameResultActivity)
}
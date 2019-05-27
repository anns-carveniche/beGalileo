package com.carveniche.begalileo.di.module

import android.app.Activity
import com.carveniche.begalileo.ui.login.LoginContract
import com.carveniche.begalileo.ui.login.LoginPresenter
import com.carveniche.begalileo.ui.main.MainContract
import com.carveniche.begalileo.ui.main.MainPresenter
import com.carveniche.begalileo.ui.addChild.AddChildContract
import com.carveniche.begalileo.ui.addChild.AddChildPresenter
import com.carveniche.begalileo.ui.concepts.ConceptContract
import com.carveniche.begalileo.ui.concepts.ConceptPresenter
import com.carveniche.begalileo.ui.nearbyCenters.NearbyCenterContract
import com.carveniche.begalileo.ui.nearbyCenters.NearbyCenterPresenter
import com.carveniche.begalileo.ui.speedMath.SpeedMathContract
import com.carveniche.begalileo.ui.speedMath.SpeedMathPresenter
import com.carveniche.begalileo.ui.speedMath.gameResultActivity.GameResultContractor
import com.carveniche.begalileo.ui.speedMath.gameResultActivity.GameResultPresenter
import com.carveniche.begalileo.ui.userLocation.UserLocationContractor
import com.carveniche.begalileo.ui.userLocation.UserLocationPresenter
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private var activity: Activity)
{

    @Provides
    fun provideActivity():Activity{
        return activity
    }

    @Provides
    fun providePresenter(): MainContract.Presenter{
        return MainPresenter()
    }

    @Provides
    fun provideLoginPresenter() : LoginContract.Presenter{
        return LoginPresenter()
    }

    @Provides
    fun provideRegisterPresenter() : AddChildContract.Presenter
    {
        return AddChildPresenter()
    }

    @Provides
    fun provideUserLocationPresenter() : UserLocationContractor.Presenter
    {
        return UserLocationPresenter()
    }

    @Provides
    fun provideConceptPresenter() : ConceptContract.Presenter
    {
        return ConceptPresenter()
    }

    @Provides
    fun provideNearbyCenterPresenter() : NearbyCenterContract.Presenter
    {
        return NearbyCenterPresenter()
    }

    @Provides
    fun provideSpeedMathCenter() : SpeedMathContract.Presenter
    {
        return SpeedMathPresenter()
    }

    @Provides
    fun provideGameResult(): GameResultContractor.Presenter
    {
        return GameResultPresenter()
    }

}
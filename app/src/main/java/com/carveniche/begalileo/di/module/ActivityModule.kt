package com.carveniche.begalileo.di.module

import android.app.Activity
import com.carveniche.begalileo.contract.*
import com.carveniche.begalileo.presenter.*
import com.carveniche.begalileo.ui.activities.NearbyCenterContract
import com.carveniche.begalileo.ui.activities.NearbyCenterPresenter
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

    @Provides
    fun providePracticeMath() : PracticeMathContract.Presenter
    {
        return PracticeMathPresenter()
    }



}
package com.carveniche.begalileo.di.module

import android.app.Activity
import com.carveniche.begalileo.contract.LoginContract
import com.carveniche.begalileo.presenter.LoginPresenter
import com.carveniche.begalileo.contract.MainContract
import com.carveniche.begalileo.presenter.MainPresenter
import com.carveniche.begalileo.contract.AddChildContract
import com.carveniche.begalileo.presenter.AddChildPresenter
import com.carveniche.begalileo.contract.ConceptContract
import com.carveniche.begalileo.presenter.ConceptPresenter
import com.carveniche.begalileo.ui.activities.NearbyCenterContract
import com.carveniche.begalileo.ui.activities.NearbyCenterPresenter
import com.carveniche.begalileo.contract.SpeedMathContract
import com.carveniche.begalileo.presenter.SpeedMathPresenter
import com.carveniche.begalileo.contract.GameResultContractor
import com.carveniche.begalileo.presenter.GameResultPresenter
import com.carveniche.begalileo.contract.UserLocationContractor
import com.carveniche.begalileo.presenter.UserLocationPresenter
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
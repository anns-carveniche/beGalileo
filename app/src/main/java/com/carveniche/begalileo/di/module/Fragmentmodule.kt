package com.carveniche.begalileo.di.module

import com.carveniche.begalileo.api.ApiServiceInterface
import com.carveniche.begalileo.models.PlayerGameStatusModel
import com.carveniche.begalileo.ui.fragments.ConceptHomeContract
import com.carveniche.begalileo.ui.fragments.ConceptHomePresenter
import com.carveniche.begalileo.ui.fragments.ConceptListContract
import com.carveniche.begalileo.ui.fragments.ConceptListPresenter
import com.carveniche.begalileo.contract.HomeContract
import com.carveniche.begalileo.presenter.HomePresenter
import com.carveniche.begalileo.ui.fragments.EnterEmailContract
import com.carveniche.begalileo.ui.fragments.EnterEmailPresenter
import com.carveniche.begalileo.contract.EnterOtpContract
import com.carveniche.begalileo.presenter.EnterOtpPresenter
import com.carveniche.begalileo.contract.SignInContract
import com.carveniche.begalileo.presenter.SignInPresenter
import com.carveniche.begalileo.contract.MainContract
import com.carveniche.begalileo.presenter.MainPresenter
import com.carveniche.begalileo.ui.fragments.FragmentMapMarkerContract
import com.carveniche.begalileo.ui.fragments.FragmentMapMarkerPresenter

import com.carveniche.begalileo.contract.PracticeContract
import com.carveniche.begalileo.presenter.PracticePresenter
import com.carveniche.begalileo.ui.fragments.GameLevelContractor
import com.carveniche.begalileo.ui.fragments.GameLevelPresenter
import com.carveniche.begalileo.contract.MathWithComputerContract
import com.carveniche.begalileo.presenter.MathWithComputerPresenter

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class Fragmentmodule  {
    @Provides
    fun provideHomePresenter() : HomeContract.Presenter
    {
        return HomePresenter()
    }

    @Provides
    fun providePracticeScreen() : PracticeContract.Presenter
    {
        return PracticePresenter()
    }

    @Provides
    fun provideMainPresenter() : MainContract.Presenter
    {
        return MainPresenter()
    }



    @Provides
    fun provideSignInPresenter() : SignInContract.Presenter
    {
        return SignInPresenter()
    }

    @Provides
    fun provideEnterOtpPresenter() : EnterOtpContract.Presenter
    {
        return EnterOtpPresenter()
    }

    @Provides
    fun provideApiService() : ApiServiceInterface
    {
        return ApiServiceInterface.create()
    }

    @Provides
    fun provideEnterEmailPresenter() : EnterEmailContract.Presenter
    {
        return EnterEmailPresenter()
    }

    @Provides
    fun provideConceptHomePreseneter() : ConceptHomeContract.Presenter
    {
        return ConceptHomePresenter()
    }
    @Provides
    fun provideConceptListPresenter() : ConceptListContract.Presenter
    {
        return ConceptListPresenter()
    }

    @Provides
    fun provideMapMarkerFragment() : FragmentMapMarkerContract.Presenter
    {
        return FragmentMapMarkerPresenter()
    }

    @Provides
    fun provideMathWithComputerFragment() : MathWithComputerContract.Presenter
    {
        return MathWithComputerPresenter()
    }

    @Provides
    @Singleton
    fun providePlayerGameStatusModel() : PlayerGameStatusModel
    {
        return PlayerGameStatusModel()
    }

    @Provides
    fun provideGameLevelPresenter() : GameLevelContractor.Presenter
    {
        return GameLevelPresenter()
    }





}
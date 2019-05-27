package com.carveniche.begalileo.di.module

import com.carveniche.begalileo.api.ApiServiceInterface
import com.carveniche.begalileo.models.PlayerGameStatusModel
import com.carveniche.begalileo.ui.concepts.fragments.ConceptHomeContract
import com.carveniche.begalileo.ui.concepts.fragments.ConceptHomePresenter
import com.carveniche.begalileo.ui.concepts.fragments.ConceptListContract
import com.carveniche.begalileo.ui.concepts.fragments.ConceptListPresenter
import com.carveniche.begalileo.ui.home.HomeContract
import com.carveniche.begalileo.ui.home.HomePresenter
import com.carveniche.begalileo.ui.login.EnterEmailContract
import com.carveniche.begalileo.ui.login.EnterEmailPresenter
import com.carveniche.begalileo.ui.login.enterOtp.EnterOtpContract
import com.carveniche.begalileo.ui.login.enterOtp.EnterOtpPresenter
import com.carveniche.begalileo.ui.login.signIn.SignInContract
import com.carveniche.begalileo.ui.login.signIn.SignInPresenter
import com.carveniche.begalileo.ui.main.MainContract
import com.carveniche.begalileo.ui.main.MainPresenter
import com.carveniche.begalileo.ui.nearbyCenters.fragments.FragmentMapMarkerContract
import com.carveniche.begalileo.ui.nearbyCenters.fragments.FragmentMapMarkerPresenter

import com.carveniche.begalileo.ui.practical.PracticeContract
import com.carveniche.begalileo.ui.practical.PracticePresenter
import com.carveniche.begalileo.ui.speedMath.fragments.gameLevelFragment.GameLevelContractor
import com.carveniche.begalileo.ui.speedMath.fragments.gameLevelFragment.GameLevelPresenter
import com.carveniche.begalileo.ui.speedMath.fragments.mathWithComputer.MathWithComputerContract
import com.carveniche.begalileo.ui.speedMath.fragments.mathWithComputer.MathWithComputerPresenter

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
package com.carveniche.wisdomleap.di.module

import android.app.Activity
import android.content.Context
import com.carveniche.wisdomleap.contract.MainContract
import com.carveniche.wisdomleap.contract.RegisterContract
import com.carveniche.wisdomleap.contract.VideoPlayContract
import com.carveniche.wisdomleap.di.PerApplication
import com.carveniche.wisdomleap.presenter.MainPresenter
import com.carveniche.wisdomleap.presenter.RegisterPresenter
import com.carveniche.wisdomleap.presenter.VideoPlayPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module
class ActivityModule(private val activity: Activity)
{

    @PerApplication
    @Provides
    fun provideContext() : Context = activity

    @Provides
    fun provideRegisterPresenter() : RegisterContract.Presenter
    {
        return RegisterPresenter()
    }

    @Provides
    fun provideVidePlayPresenter() : VideoPlayContract.Presenter
    {
        return VideoPlayPresenter()
    }

    @Provides
    fun provideMainPresenter() : MainContract.Presenter
    {
        return MainPresenter()
    }


}
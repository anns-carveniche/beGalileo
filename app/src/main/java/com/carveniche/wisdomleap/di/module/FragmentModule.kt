package com.carveniche.wisdomleap.di.module

import com.carveniche.wisdomleap.api.ApiInterface
import com.carveniche.wisdomleap.contract.*
import com.carveniche.wisdomleap.presenter.*
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
class FragmentModule {

    @Provides
    fun provideMobileNumberPresenter():MobileNumberContract.Presenter
    {
        return MobileNumberPresenter()
    }

    @Provides
    fun provideEnterOtpPresenter() : EnterOtpContract.Presenter
    {
        return EnterOtpPresenter()
    }

    @Provides
    fun provideListSubjectPresenter() : ListSubjctContract.Presenter
    {
        return ListSubjectPresenter()
    }

    @Provides
    fun provideListChapterPresenter() : ListChapterContract.Presenter
    {
        return ListChapterPresenter()
    }

    @Provides
    fun provideQuizQuestionPresenter() : QuizQuestionContract.Presenter
    {
        return QuizQuestionPresenter()
    }

    @Provides
    fun provideQuizResultPresenter() : QuizResultContract.Presenter
    {
        return QuizResultPresenter()
    }

    @Provides
    fun provideEnterEmailPresenter() : EnterEmailContract.Presenter
    {
        return EnterEmailPresenter()
    }

    @Provides
    fun provideDashboardPresenter() : DashboardContract.Presenter
    {
        return DashboardPresenter()
    }

    @Provides
    fun provideQuizHomePresenter() : QuizHomeContract.Presenter
    {
        return QuizHomePresenter()
    }

    @Provides
    fun provideProfileHomePresenter() : ProfileHomeContract.Presenter
    {
        return ProfileHomePresenter()
    }

    @Provides
    fun provideChapterQuizFragment() : ChapterQuizContract.Presenter
    {
        return ChapterQuizPresenter()
    }

    @Provides
    fun provideChapterQuizResultFragment() : ChapterQuizResultContract.Presenter
    {
        return ChapterQuizResultPresenter()
    }

}
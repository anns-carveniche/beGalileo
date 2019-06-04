package com.carveniche.wisdomleap.di.component

import com.carveniche.wisdomleap.di.module.ContextModule
import com.carveniche.wisdomleap.di.module.FragmentModule
import com.carveniche.wisdomleap.di.module.HttpModule
import com.carveniche.wisdomleap.di.module.SharedPreferenceModule
import com.carveniche.wisdomleap.view.fragment.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [FragmentModule::class,ContextModule::class,SharedPreferenceModule::class,HttpModule::class])
interface FragmentComponent {
    fun inject(mobileNumberFragment: MobileNumberFragment)
    fun inject(enterOtpFragment: EnterOtpFragment)
    fun inject(listSubjectFragment: ListSubjectFragment)
    fun inject(listChaptersFragment: ListChaptersFragment)
    fun inject(quizQuestionFragment: QuizQuestionFragment)
    fun inject(quizResultFragment: QuizResultFragment)
    fun inject(enterEmailFragment: EnterEmailFragment)
    fun inject(dashboardFragment: DashboardFragment)
    fun inject(quizHomeFragment: QuizHomeFragment)
    fun inject(profileHomeFragment: ProfileHomeFragment)
    fun inject(chapterQuizFragment: ChapterQuizFragment)
    fun inject(chapterQuizResultFragment: ChapterQuizResultFragment)
    fun inject(recentViewedVideosFragment: RecentViewedVideosFragment)
    fun inject(recentViewedQuizFragment: RecentViewedQuizFragment)
}
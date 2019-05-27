package com.carveniche.begalileo.di.component

import com.carveniche.begalileo.di.module.ContextModule
import com.carveniche.begalileo.di.module.Fragmentmodule
import com.carveniche.begalileo.di.module.SharedPreferenceModule
import com.carveniche.begalileo.ui.fragments.ConceptHomeFragment
import com.carveniche.begalileo.ui.fragments.ConceptListFragment
import com.carveniche.begalileo.ui.fragments.HomeFragment
import com.carveniche.begalileo.ui.fragments.EnterEmailFragment
import com.carveniche.begalileo.ui.fragments.EnterOtpFragment
import com.carveniche.begalileo.ui.fragments.SignInFragment
import com.carveniche.begalileo.ui.fragments.FragmentMapMarker
import com.carveniche.begalileo.ui.fragments.PracticeFragment
import com.carveniche.begalileo.util.ComputerPlayer
import com.carveniche.begalileo.ui.fragments.GameLevelFragment
import com.carveniche.begalileo.ui.fragments.MathWithComputerFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [Fragmentmodule::class, SharedPreferenceModule::class, ContextModule::class])
interface FragmentComponent {
    fun inject(homeFragment: HomeFragment)
    fun inject(practiceFragment: PracticeFragment)
    fun inject(signInFragment: SignInFragment)
    fun inject(enterOtpFragment: EnterOtpFragment)
    fun inject(enterEmailFragment: EnterEmailFragment)
    fun inject(conceptHomeFragment: ConceptHomeFragment)
    fun inject(conceptListFragment: ConceptListFragment)
    fun inject(mapMarker: FragmentMapMarker)
    fun inject(mathWithComputerFragment: MathWithComputerFragment)
    fun inject(computerPlayer: ComputerPlayer)
    fun inject(gameLevelFragment: GameLevelFragment)
}
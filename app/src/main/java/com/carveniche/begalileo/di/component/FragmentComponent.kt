package com.carveniche.begalileo.di.component

import com.carveniche.begalileo.di.module.ContextModule
import com.carveniche.begalileo.di.module.Fragmentmodule
import com.carveniche.begalileo.di.module.SharedPreferenceModule
import com.carveniche.begalileo.ui.concepts.fragments.ConceptHomeFragment
import com.carveniche.begalileo.ui.concepts.fragments.ConceptListFragment
import com.carveniche.begalileo.ui.home.HomeFragment
import com.carveniche.begalileo.ui.login.EnterEmailFragment
import com.carveniche.begalileo.ui.login.enterOtp.EnterOtpFragment
import com.carveniche.begalileo.ui.login.signIn.SignInFragment
import com.carveniche.begalileo.ui.nearbyCenters.fragments.FragmentMapMarker
import com.carveniche.begalileo.ui.practical.PracticeFragment
import com.carveniche.begalileo.ui.speedMath.fragments.ComputerPlayer
import com.carveniche.begalileo.ui.speedMath.fragments.gameLevelFragment.GameLevelFragment
import com.carveniche.begalileo.ui.speedMath.fragments.mathWithComputer.MathWithComputerFragment
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
package com.carveniche.begalileo.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.carveniche.begalileo.util.Constants
import com.carveniche.begalileo.R
import com.carveniche.begalileo.contract.ConceptContract
import com.carveniche.begalileo.di.component.DaggerActivityComponent
import com.carveniche.begalileo.di.module.ActivityModule
import com.carveniche.begalileo.di.module.ContextModule
import com.carveniche.begalileo.di.module.SharedPreferenceModule
import com.carveniche.begalileo.ui.fragments.ConceptHomeFragment
import com.carveniche.begalileo.ui.fragments.ConceptListFragment
import javax.inject.Inject

class ConceptActivity : AppCompatActivity(), ConceptContract.View {



    @Inject lateinit var presenter: ConceptContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_concept)
        injectDependency()
        if(savedInstanceState==null)
        showConceptHome()
    }

    private fun injectDependency() {
        var activityComponent = DaggerActivityComponent.builder()
            .activityModule(ActivityModule(this))
            .sharedPreferenceModule(SharedPreferenceModule())
            .contextModule(ContextModule(this))
            .build()
        activityComponent.inject(this)
    }

    override fun showProgress(boolean: Boolean) {

    }

    override fun showConceptHome() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame, ConceptHomeFragment().newInstance(),
                ConceptHomeFragment.TAG)
            .commit()
    }

    override fun showConceptList(boardId: Int, gradeId: Int) {
        var ldf = ConceptListFragment()
        var args = Bundle()
        args.putInt(Constants.BOARD_ID,boardId)
        args.putInt(Constants.GRADE_ID,gradeId)
        ldf.arguments =args
        supportFragmentManager.beginTransaction().addToBackStack(ConceptHomeFragment.TAG)
            .addToBackStack(null)
            .replace(R.id.frame,ldf, ConceptListFragment.TAG)
            .commit()
    }


    override fun onResume() {
        super.onResume()

    }
}

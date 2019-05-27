package com.carveniche.begalileo.ui.practical

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.carveniche.begalileo.R
import com.carveniche.begalileo.di.component.DaggerFragmentComponent
import com.carveniche.begalileo.di.module.ContextModule
import com.carveniche.begalileo.di.module.Fragmentmodule
import com.carveniche.begalileo.ui.home.HomeFragment
import com.carveniche.begalileo.ui.main.MainContract
import com.carveniche.begalileo.ui.main.MainPresenter
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable
import kotlinx.android.synthetic.main.fragment_practice.view.*
import java.util.*
import javax.inject.Inject

class PracticeFragment : Fragment(),PracticeContract.View  {

    @Inject lateinit var presenter: PracticeContract.Presenter
    lateinit var mainPresenter: MainContract.View

    private lateinit var rootView : View

    fun newInstance() :PracticeFragment
    {
        return PracticeFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
        mainPresenter = activity as MainContract.View
    }

    private fun injectDependency() {
        var fragmentComponent = DaggerFragmentComponent.builder()
            .fragmentmodule(Fragmentmodule())
            .contextModule(ContextModule(context!!))
            .build()
        fragmentComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_practice,container,false);
        rootView.btnHome.setOnClickListener {
            mainPresenter.showHomeFragment()
        }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attach(this)
        presenter.subscribe()
    }

    override fun showProgress(boolean: Boolean) {

    }
    companion object {
        val TAG : String = "PracticeFragment"
    }




}
package com.carveniche.begalileo.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.carveniche.begalileo.R
import com.carveniche.begalileo.api.ApiServiceInterface
import com.carveniche.begalileo.di.component.DaggerFragmentComponent
import com.carveniche.begalileo.di.module.ContextModule
import com.carveniche.begalileo.di.module.Fragmentmodule
import com.carveniche.begalileo.contract.BaseContract
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class FragmentMapMarker : Fragment(),OnMapReadyCallback,
    FragmentMapMarkerContract.View {


    lateinit var rootView : View

    @Inject lateinit var presenter: FragmentMapMarkerContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()

    }
    override fun showProgress(boolean: Boolean) {

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_map_marker,container,false)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iniUI()
    }

    private fun iniUI() {

    }

    private fun injectDependency() {
        val fragmentComponent = DaggerFragmentComponent.builder()
            .fragmentmodule(Fragmentmodule())
            .contextModule(ContextModule(context!!))
            .build()
        fragmentComponent.inject(this)
    }


    override fun onMapReady(p0: GoogleMap?) {

    }

    companion object {
        var TAG  = "FragmentMapMarker"
    }


}
class FragmentMapMarkerContract
{
    interface View : BaseContract.View
    {

    }
    interface Presenter : BaseContract.Presenter<View>
    {

    }
}
class FragmentMapMarkerPresenter : FragmentMapMarkerContract.Presenter
{

    @Inject lateinit var view : FragmentMapMarkerContract.View
    private val apiServiceInterface: ApiServiceInterface = ApiServiceInterface.create()
    private val subscriptions  = CompositeDisposable()

    override fun subscribe() {

    }

    override fun unsubscrbe() {
        subscriptions.clear()
    }

    override fun attach(view: FragmentMapMarkerContract.View) {
        this.view = view
    }

}

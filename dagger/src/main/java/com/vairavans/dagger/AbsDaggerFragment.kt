package com.vairavans.dagger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import javax.inject.Inject

/**
 * A [AbsTestableDaggerFragment] abstracting boiler plate associated with setting up a [ViewModel] meant to be used
 * with [DaggerViewModelFactory] and [ViewModelKey]
 */
abstract class AbsDaggerFragment< T : ViewModel > : AbsTestableDaggerFragment() {

    @Inject
    lateinit var viewModelFactory : DaggerViewModelFactory

    lateinit var viewModel : T

    abstract var viewModelClass : Class<T>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewModel = getInitializedViewModel()
        return onCreateView(inflater, container, savedInstanceState, viewModel )
    }

    protected open fun getInitializedViewModel() : T =
        ViewModelProviders.of( this, viewModelFactory ).get( viewModelClass )

    protected abstract fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
                                        viewModel : T ) : View
}

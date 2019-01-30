package com.vairavans.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.UnsupportedOperationException
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class DaggerViewModelFactory @Inject constructor( private val viewModelMap : Map<Class<out ViewModel>,
        @JvmSuppressWildcards Provider<ViewModel>> ) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        val viewModelProvider = viewModelMap[ modelClass ]
            ?: throw UnsupportedOperationException( "Unsupported request for $modelClass" )

        @Suppress("UNCHECKED_CAST")
        return viewModelProvider.get() as T
    }

}

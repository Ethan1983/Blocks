package com.vairavans.dagger

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import javax.inject.Inject
import javax.inject.Provider

class DaggerFragmentFactory @Inject constructor(private val fragmentProviderMap : Map<Class<out Fragment>,
        @JvmSuppressWildcards Provider<Fragment>> ): FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

        val fragmentClass = loadFragmentClass(classLoader, className)

        val fragmentProvider = fragmentProviderMap[ fragmentClass ]

        // Super call as this Factory will be used for other fragments as well like NavHostFragment
        return fragmentProvider?.get() ?: super.instantiate(classLoader, className)
    }
}

package com.vairavans.dagger

import android.content.Context
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * A [Fragment] to abstract injection. Also supports disabling injection using [enableInjection] which is enabled by
 * default.
 */
abstract class AbsTestableDaggerFragment : Fragment(), HasSupportFragmentInjector {

    @Inject
    lateinit var childFragmentInjector : DispatchingAndroidInjector<Fragment>

    protected open var enableInjection = true

    override fun onAttach(context: Context) {

        if( enableInjection ) {
            AndroidSupportInjection.inject(this)
        }

        super.onAttach(context)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment>? {
        return childFragmentInjector
    }
}

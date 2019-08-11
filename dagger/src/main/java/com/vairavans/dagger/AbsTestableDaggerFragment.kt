package com.vairavans.dagger

import android.content.Context
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 * A [Fragment] to abstract injection. Also supports disabling injection using [enableInjection] which is enabled by
 * default.
 */
abstract class AbsTestableDaggerFragment : Fragment(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    protected open var enableInjection = true

    override fun onAttach(context: Context) {

        if( enableInjection ) {
            AndroidSupportInjection.inject(this)
        }

        super.onAttach(context)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }
}

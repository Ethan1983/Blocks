package com.vairavans.analytics

import androidx.annotation.CallSuper
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import com.google.firebase.analytics.FirebaseAnalytics
import com.vairavans.dagger.AbsDaggerFragment
import javax.inject.Inject

/**
 * A [AbsDaggerFragment] with support for [FirebaseAnalytics] Use [enableAnalytics] to disable analytics when not
 * necessary like in debug or test builds.
 */
abstract class AbsDaggerAnalyticsFragment< T : ViewModel> : AbsDaggerFragment<T>() {

    @Inject
    lateinit var firebaseAnalytics: FirebaseAnalytics

    abstract var firebaseAnalyticsScreenName : String

    abstract var firebaseAnalyticsScreenClassOverride : String

    protected open var enableAnalytics : Boolean = true

    protected fun logAnalyticsEvent( event : String ) {
        if( enableAnalytics ) {
            firebaseAnalytics.logEvent( event )
        }
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        if( enableAnalytics ) {
            firebaseAnalytics.setCurrentScreen( getActivityInternal(), firebaseAnalyticsScreenName,
                firebaseAnalyticsScreenClassOverride )
        }
    }

    @CallSuper
    override fun onPause() {
        super.onPause()
        if( enableAnalytics ) {
            firebaseAnalytics.setCurrentScreen( getActivityInternal(), null, null )
        }
    }

    internal open fun getActivityInternal() : FragmentActivity = requireActivity()
}

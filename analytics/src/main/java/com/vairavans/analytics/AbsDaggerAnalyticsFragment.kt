package com.vairavans.analytics

import androidx.annotation.CallSuper
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import com.google.firebase.analytics.FirebaseAnalytics
import com.vairavans.dagger.AbsDaggerFragment
import javax.inject.Inject

/**
 * A [AbsDaggerFragment] with support for [FirebaseAnalytics]
 */
abstract class AbsDaggerAnalyticsFragment< T : ViewModel> : AbsDaggerFragment<T>() {

    @Inject
    lateinit var firebaseAnalytics: FirebaseAnalytics

    abstract var firebaseAnalyticsScreenName : String

    abstract var firebaseAnalyticsScreenClassOverride : String

    @CallSuper
    override fun onResume() {
        super.onResume()
        firebaseAnalytics.setCurrentScreen( getActivityInternal(), firebaseAnalyticsScreenName,
            firebaseAnalyticsScreenClassOverride )
    }

    @CallSuper
    override fun onPause() {
        super.onPause()
        firebaseAnalytics.setCurrentScreen( getActivityInternal(), null, null )
    }

    internal open fun getActivityInternal() : FragmentActivity = requireActivity()
}

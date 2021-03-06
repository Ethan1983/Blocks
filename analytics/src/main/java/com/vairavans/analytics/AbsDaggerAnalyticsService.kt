package com.vairavans.analytics

import com.google.firebase.analytics.FirebaseAnalytics
import dagger.android.DaggerService
import javax.inject.Inject

/**
 * A [DaggerService] with support for [FirebaseAnalytics]
 */
abstract class AbsDaggerAnalyticsService : DaggerService() {

    @Inject
    lateinit var firebaseAnalytics: FirebaseAnalytics

    protected open var enableAnalytics : Boolean = true

    protected fun logAnalyticsEvent( event : String ) {
        if( enableAnalytics ) {
            firebaseAnalytics.logEvent( event )
        }
    }
}

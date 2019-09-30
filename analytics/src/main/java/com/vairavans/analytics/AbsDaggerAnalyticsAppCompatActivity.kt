package com.vairavans.analytics

import com.google.firebase.analytics.FirebaseAnalytics
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class AbsDaggerAnalyticsAppCompatActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var firebaseAnalytics: FirebaseAnalytics

    protected open var enableAnalytics : Boolean = true

    protected fun logAnalyticsEvent( event : String ) {
        if( enableAnalytics ) {
            firebaseAnalytics.logEvent( event )
        }
    }
}
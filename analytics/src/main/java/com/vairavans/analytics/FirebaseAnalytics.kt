package com.vairavans.analytics

import com.google.firebase.analytics.FirebaseAnalytics

/**
 * Extension of [FirebaseAnalytics] to pass null bundle for [FirebaseAnalytics.logEvent]
 */
fun FirebaseAnalytics.logEvent( event : String ) = logEvent( event, null )

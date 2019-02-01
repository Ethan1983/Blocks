package com.vairavans.analytics

import com.google.firebase.analytics.FirebaseAnalytics

fun FirebaseAnalytics.logEvent( event : String ) = logEvent( event, null )

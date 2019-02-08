package com.vairavans.analytics

import com.google.firebase.analytics.FirebaseAnalytics
import dagger.android.DaggerService
import javax.inject.Inject

abstract class AbsDaggerAnalyticsService : DaggerService() {

    @Inject
    lateinit var firebaseAnalytics: FirebaseAnalytics
}

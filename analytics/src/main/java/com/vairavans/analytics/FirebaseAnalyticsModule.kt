package com.vairavans.analytics

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.Module
import dagger.Provides

/**
 * Dagger Module for [FirebaseAnalytics]
 */
@Module
abstract class FirebaseAnalyticsModule {

    @Module
    companion object {

        @Provides
        fun provideFirebaseAnalytics( context : Context) : FirebaseAnalytics =
            FirebaseAnalytics.getInstance( context )

    }
}

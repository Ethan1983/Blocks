package com.vairavans.analytics

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.Module
import dagger.Provides

@Module
abstract class FirebaseAnalyticsModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        fun provideFirebaseAnalytics( context : Context) : FirebaseAnalytics =
            FirebaseAnalytics.getInstance( context )

    }
}

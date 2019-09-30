package com.vairavans.analytics

import android.content.Intent
import android.os.IBinder
import io.mockk.Called
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class AbsDaggerAnalyticsServiceTest {

    open class TestEnabledService : AbsDaggerAnalyticsService() {
        override fun onBind(p0: Intent?): IBinder? {
            TODO("not implemented")
        }

        fun performSomeActionGeneratingAnalyticsEvent( event : String ) =
            logAnalyticsEvent( event )
    }

    class TestDisabledService : TestEnabledService() {
        override var enableAnalytics: Boolean = false
    }

    private val analyticsEnabledService = TestEnabledService().apply {
        firebaseAnalytics = mockk(relaxed = true)
    }

    private val analyticsDisabledService = TestDisabledService().apply {
        firebaseAnalytics = mockk(relaxed = true)
    }

    @Test
    fun `AbsDaggerAnalyticsService logs requested event when enabled`() {

        analyticsEnabledService.performSomeActionGeneratingAnalyticsEvent("Event")

        verify { analyticsEnabledService.firebaseAnalytics.logEvent( "Event" ) }
    }

    @Test
    fun `AbsDaggerAnalyticsService ignores event when disabled`() {

        analyticsDisabledService.performSomeActionGeneratingAnalyticsEvent("Event")

        verify { analyticsEnabledService.firebaseAnalytics wasNot Called }
    }
}
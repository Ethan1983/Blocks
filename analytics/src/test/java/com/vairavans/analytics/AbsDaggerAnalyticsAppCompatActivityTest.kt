package com.vairavans.analytics

import io.mockk.Called
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class AbsDaggerAnalyticsAppCompatActivityTest {

    open class TestEnabledActivity : AbsDaggerAnalyticsAppCompatActivity() {

        fun performSomeActionGeneratingAnalyticsEvent( event : String ) =
            logAnalyticsEvent( event )
    }

    class TestDisabledActivity : TestEnabledActivity() {
        override var enableAnalytics: Boolean = false
    }

    private val analyticsEnabledActivity = TestEnabledActivity().apply {
        firebaseAnalytics = mockk(relaxed = true)
    }

    private val analyticsDisabledActivity = TestDisabledActivity().apply {
        firebaseAnalytics = mockk(relaxed = true)
    }

    @Test
    fun `AbsDaggerAnalyticsAppCompatActivity logs requested event when enabled`() {

        analyticsEnabledActivity.performSomeActionGeneratingAnalyticsEvent("Event")

        verify { analyticsEnabledActivity.firebaseAnalytics.logEvent( "Event" ) }
    }

    @Test
    fun `AbsDaggerAnalyticsAppCompatActivity ignores event when disabled`() {

        analyticsDisabledActivity.performSomeActionGeneratingAnalyticsEvent("Event")

        verify { analyticsDisabledActivity.firebaseAnalytics wasNot Called }
    }
}
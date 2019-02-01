package com.vairavans.analytics

import com.google.firebase.analytics.FirebaseAnalytics
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class FirebaseAnalyticsTest {

    private val firebaseAnalytics = mockk<FirebaseAnalytics>()

    @Test
    fun `logEvent extension relays a null bundle`() {

        every { firebaseAnalytics.logEvent( any(), any() ) } answers { }

        firebaseAnalytics.logEvent( "Launch" )

        verify { firebaseAnalytics.logEvent( "Launch", null ) }
    }
}
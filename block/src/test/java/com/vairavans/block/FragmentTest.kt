package com.vairavans.block

import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.fragment.app.Fragment
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class FragmentTest {

    private val fragment = mockk<Fragment>()

    @Test
    fun `startActivity handles unresolved intent`() {

        every { fragment.startActivity(any()) } throws ActivityNotFoundException()

        var activityNotFoundHandlerInvoked = false
        val intent = mockk<Intent>()
        fragment.startActivity( intent ) {
            activityNotFoundHandlerInvoked = true
        }

        assert( activityNotFoundHandlerInvoked ) {
            "Handler not invoked upon activity not found"
        }
    }

    @Test
    fun `startActivity gives same fragment in activityNotFoundHandler`() {

        every { fragment.startActivity(any()) } throws ActivityNotFoundException()

        var sameFragmentReceived = false
        val intent = mockk<Intent>()
        fragment.startActivity( intent ) { receivedFragment ->
            sameFragmentReceived = receivedFragment == fragment
        }

        assert( sameFragmentReceived ) {
            "Handler received a different fragment upon activity not found"
        }
    }

    @Test
    fun `startActivity handles resolved intent`() {

        every { fragment.startActivity(any()) } just Runs

        var activityNotFoundHandlerInvoked = false
        val intent = mockk<Intent>()
        fragment.startActivity( intent ) {
            activityNotFoundHandlerInvoked = true
        }

        assert( !activityNotFoundHandlerInvoked ) {
            "Handler invoked upon activity found"
        }
    }
}

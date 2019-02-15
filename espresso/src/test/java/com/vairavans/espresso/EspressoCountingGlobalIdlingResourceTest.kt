package com.vairavans.espresso

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class EspressoCountingGlobalIdlingResourceTest {

    @Test
    fun `test EspressoCountingIdlingResource increment`() {

        assert( EspressoCountingGlobalIdlingResource.getIdlingResource().isIdleNow ) {
            "EspressoCountingIdlingResource is not idle at test start"
        }

        EspressoCountingGlobalIdlingResource.increment()

        assert( !EspressoCountingGlobalIdlingResource.getIdlingResource().isIdleNow ) {
            "EspressoCountingIdlingResource is idle after increment"
        }

        // cleanup as EspressoCountingIdlingResource is a global resource
        EspressoCountingGlobalIdlingResource.decrement()
    }

    @Test
    fun `test EspressoCountingIdlingResource decrement`() {

        assert( EspressoCountingGlobalIdlingResource.getIdlingResource().isIdleNow ) {
            "EspressoCountingIdlingResource is not idle at test start"
        }

        EspressoCountingGlobalIdlingResource.increment()
        EspressoCountingGlobalIdlingResource.decrement()

        assert( EspressoCountingGlobalIdlingResource.getIdlingResource().isIdleNow ) {
            "EspressoCountingIdlingResource is not idle after decrement"
        }
    }
}
package com.vairavans.block

import com.vairavans.espresso.EspressoCountingGlobalIdlingResource
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CoroutineScopeTest {

    @Test
    fun `idlingLaunch increments and decrements EspressoCountingGlobalIdlingResource`() {

        assert( EspressoCountingGlobalIdlingResource.getIdlingResource().isIdleNow ) {
            "EspressoCountingIdlingResource is not idle at test start"
        }

        runBlocking {
            idlingLaunch {

                assert( !EspressoCountingGlobalIdlingResource.getIdlingResource().isIdleNow ) {
                    "EspressoCountingIdlingResource is idle while at idlingLaunch"
                }

            }
        }

        assert( EspressoCountingGlobalIdlingResource.getIdlingResource().isIdleNow ) {
            "EspressoCountingIdlingResource is not idle after scope idlingLaunch"
        }
    }
}

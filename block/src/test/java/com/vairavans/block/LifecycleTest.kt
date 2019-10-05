package com.vairavans.block

import androidx.lifecycle.Lifecycle
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class LifecycleTest {

    @Test(expected = IllegalStateException::class)
    fun `Lifecycle startedStateInvoke fails in INITIALIZED state`() =
        verifyFailingLifecycleStateInvoke( Lifecycle.State.INITIALIZED )

    @Test(expected = IllegalStateException::class)
    fun `Lifecycle startedStateInvoke fails in CREATED state`() =
        verifyFailingLifecycleStateInvoke( Lifecycle.State.CREATED )

    @Test
    fun `Lifecycle startedStateInvoke works in STARTED state`() =
        verifySuccessLifecycleStateInvoke( Lifecycle.State.STARTED )

    @Test
    fun `Lifecycle startedStateInvoke fails in RESUMED state`() =
        verifySuccessLifecycleStateInvoke( Lifecycle.State.RESUMED )

    @Test(expected = IllegalStateException::class)
    fun `Lifecycle startedStateInvoke fails in DESTROYED state`() =
        verifyFailingLifecycleStateInvoke( Lifecycle.State.DESTROYED )

    private fun verifySuccessLifecycleStateInvoke( state : Lifecycle.State ) {

        val lifeCycle = mockk<Lifecycle>()
        every { lifeCycle.currentState } returns state

        var lambdaInvoked = false

        lifeCycle.startedStateInvoke {
            lambdaInvoked = true
        }

        // startedStateInvoke should not throw an exception
        assert(lambdaInvoked) {
            "lambda to startedStateInvoke not invoked"
        }
    }

    private fun verifyFailingLifecycleStateInvoke(state : Lifecycle.State ) {

        val lifeCycle = mockk<Lifecycle>()
        every { lifeCycle.currentState } returns state

        var lambdaNotInvoked = true

        lifeCycle.startedStateInvoke {
            lambdaNotInvoked = false
        }

        assert(lambdaNotInvoked) {
            "lambda to startedStateInvoke invoked"
        }
    }
}
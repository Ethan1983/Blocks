package com.vairavans.ads

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UtilTest {

    @Test
    fun `adRequest invokes specified lambda`() {

        var lambdaInvoked = false

        adRequest {
            lambdaInvoked = true
        }

        assert( lambdaInvoked ) {
            "Lambda not called by adRequest"
        }
    }
}

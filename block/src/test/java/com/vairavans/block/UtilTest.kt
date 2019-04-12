package com.vairavans.block

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UtilTest {

    @Test
    fun `repeatForever invokes specified lambda`() {

        repeatForever {
            assert( true )
            return
        }

        assert( false )
    }
}

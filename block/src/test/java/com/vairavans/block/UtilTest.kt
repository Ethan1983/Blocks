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

    @Test
    fun `repeatUntil(times) breaks as expected`() {

        var latestValue = 0
        var iterationCount = 0

        repeatUntil( 10 ) {
            iterationCount++
            val continueIteration = it <= 5

            if( continueIteration ) {
                latestValue = it
            }
            continueIteration
        }

        assert( latestValue == 5 ) {
            "repeatUntil continued post break point"
        }

        assert( iterationCount == 7 ) {
            "block not invoked expected times"
        }
    }

    @Test
    fun `repeatUntil(times) behaves as repeat when block returns true`() {

        var latestValue = 0
        var iterationCount = 0

        repeatUntil( 10 ) {
            iterationCount++
            latestValue = it
            true
        }

        assert( latestValue == 9 ) {
            "repeatUntil quite earlier"
        }

        assert( iterationCount == 10 ) {
            "block not invoked expected times"
        }
    }

    @Test
    fun `repeatUntil breaks as expected`() {

        var latestValue = 0
        var iterationCount = 0

        repeatUntil {
            iterationCount++
            val continueIteration = it <= 5

            if( continueIteration ) {
                latestValue = it
            }
            continueIteration
        }

        assert( latestValue == 5 ) {
            "repeatUntil continued post break point"
        }

        assert( iterationCount == 7 ) {
            "block not invoked expected times"
        }
    }

    @Test
    fun `repeatUntil behaves as repeat when block returns true`() {

        repeatUntil {
            assert( true )
            true
            return
        }

        assert( false ) {
            "repeatUntil failed to loop forever"
        }
    }
}

package com.vairavans.block

import io.reactivex.Observable
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ObservableTest {

    @Test
    fun `doOnNextIf invokes block when requested`() {

        var onNextLambdaInvoked = false

        Observable.just(1)
            .doOnNextIf( true ) {
                onNextLambdaInvoked = true
            }
            .subscribe()

        assert( onNextLambdaInvoked ) {
            "Block for doOnNextIf not called"
        }
    }

    @Test
    fun `doOnNextIf ignores block when not requested`() {

        var onNextLambdaNotInvoked = true

        Observable.just(1)
            .doOnNextIf( false ) {
                onNextLambdaNotInvoked = false
            }
            .subscribe()

        assert( onNextLambdaNotInvoked ) {
            "Block for doOnNextIf not called"
        }
    }

    @Test
    fun `doOnNextIf invokes block when requested by predicate`() {

        var onNextLambdaInvoked = false

        val predicate = {
            true
        }

        Observable.just(1)
            .doOnNextIf( predicate ) {
                onNextLambdaInvoked = true
            }
            .subscribe()

        assert( onNextLambdaInvoked ) {
            "Block for doOnNextIf not called"
        }
    }

    @Test
    fun `doOnNextIf ignores block when not requested by predicate`() {

        var onNextLambdaNotInvoked = true

        val predicate = {
            false
        }

        Observable.just(1)
            .doOnNextIf( predicate ) {
                onNextLambdaNotInvoked = false
            }
            .subscribe()

        assert( onNextLambdaNotInvoked ) {
            "Block for doOnNextIf not called"
        }
    }
}

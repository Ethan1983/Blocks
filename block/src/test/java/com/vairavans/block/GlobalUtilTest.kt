/*
 * Copyright (C) 2019 Vairavan Srinivasan.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vairavans.block

import org.junit.Test

class GlobalUtilTest {

    @Test
    fun `executeOrHandleNull throws exception on null input`() {

        var exceptionRaised = false

        try {
            executeOrHandleNull<Int>( null )
        } catch( e : RuntimeException ) {
            exceptionRaised = true
        }

        assert( exceptionRaised ) {
            "executeOrHandleNull didn't throw an exception on null input"
        }

    }

    @Test
    fun `executeOrHandleNull does not throw exception on non-null input`() {

        var exceptionNotRaised = true

        try {
            executeOrHandleNull( 3 )
        } catch( e : RuntimeException ) {
            exceptionNotRaised = false
        }

        assert( exceptionNotRaised ) {
            "executeOrHandleNull throws an exception on non-null input"
        }

    }

    @Test
    fun `executeOrHandleNull executes provided lambda on non-null input`() {

        var lambdaInvoked = false

        executeOrHandleNull( "String" ) {
            lambdaInvoked = true
        }

        assert( lambdaInvoked ) {
            "executeOrHandleNull did not execute lambda on non-null input"
        }

    }

    @Test
    fun `executeOrHandleNull returns Any for Any? Input`() {

        val value : Int? = 5

        @Suppress("USELESS_IS_CHECK")
        assert( executeOrHandleNull( value ) is Int )

    }

    @Test
    fun `multiLet should execute lambda for non-null inputs`() {

        var lambdaInvoked = false
        val input1 : Int? = 1
        val input2 : Int? = 2

        multiLet( input1,input2 ) { _, _ ->
            lambdaInvoked = true
        }

        assert( lambdaInvoked ) {
            "multiLet did not execute lambda for non-null inputs"
        }

    }

    @Test
    fun `multiLet should not execute lambda for first null input`() {

        var lambdaNotInvoked = true
        val input1 : Int? = null
        val input2 : Int? = 2

        multiLet( input1, input2 ) { _, _ ->
            lambdaNotInvoked = false
        }

        assert( lambdaNotInvoked ) {
            "multiLet executed lambda for first null input"
        }

    }

    @Test
    fun `multiLet should not execute lambda for second null input`() {

        var lambdaNotInvoked = true
        val input1 : Int? = 1
        val input2 : Int? = null

        multiLet( input1, input2 ) { _, _ ->
            lambdaNotInvoked = false
        }

        assert( lambdaNotInvoked ) {
            "multiLet executed lambda for second null input"
        }

    }

    @Test
    fun `multiLet should not execute lambda for both inputs`() {

        var lambdaNotInvoked = true
        val input1 : Int? = null
        val input2 : Int? = null

        multiLet( input1, input2 ) { _, _ ->
            lambdaNotInvoked = false
        }

        assert( lambdaNotInvoked ) {
            "multiLet executed lambda for both null inputs"
        }

    }
}
package com.vairavans.block

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CharSequenceTest {

    @Test
    fun `toTypedArray for single string input`() {
        val input = "foobar"
        val output = input.toTypedArray()

        assertEquals( output.size, 1 )
        assertEquals( output[0], input )
    }

    @Test
    fun `toTypedArray for string input with non space delimiter`() {
        val input = "foo:bar"
        val output = input.toTypedArray()

        assertEquals( output.size, 1 )
        assertEquals( output[0], input )
    }

    @Test
    fun `toTypedArray for string input with space delimiter`() {
        val input = "foo bar"
        val output = input.toTypedArray()

        assertEquals( output.size, 2 )
        assertEquals( output[0], "foo" )
        assertEquals( output[1], "bar" )
    }
}
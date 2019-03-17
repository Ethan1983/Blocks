package com.vairavans.block

import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import timber.log.Timber

@RunWith(JUnit4::class)
class DebugTagTreeTest {

    class TestDebugTagTree( tag : String? = null ) : DebugTagTree( tag ) {

        fun getStackElementTag( element: StackTraceElement): String =
            createStackElementTag( element )

    }

    @Test
    fun `DebugTagTree is of Timber DebugTree type`() {

        val tree = DebugTagTree()

        @Suppress("USELESS_IS_CHECK")
        assert( tree is Timber.DebugTree )
    }

    @Test
    fun `DebugTagTree uses provided tag`() {

        val customTAG = "TAG"
        val element = mockk<StackTraceElement>( relaxed = true )
        val tree = TestDebugTagTree( customTAG )
        val createdTAG = tree.getStackElementTag(element )

        assert( createdTAG == customTAG )
    }

    @Test
    fun `DebugTagTree uses StackElementTag in default config`() {

        val element = mockk<StackTraceElement>( relaxed = true )
        val tree = TestDebugTagTree()
        tree.getStackElementTag(element )

        verify { element.lineNumber }
    }
}
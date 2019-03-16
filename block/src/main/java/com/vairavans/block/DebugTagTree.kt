package com.vairavans.block

import timber.log.Timber

/**
 * Default [Timber.Tree] returned by [getTimberDebugTree] supporting line numbers for logs.
 */
open class DebugTagTree( val stackElementTag : String? ) : Timber.DebugTree() {

    override fun createStackElementTag(element: StackTraceElement): String =
        stackElementTag ?: super.createStackElementTag(element) + ":" + element.lineNumber
}

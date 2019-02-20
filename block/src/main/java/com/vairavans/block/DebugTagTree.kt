package com.vairavans.block

import timber.log.Timber

class DebugTagTree( val stackElementTag : String? ) : Timber.DebugTree() {

    override fun createStackElementTag(element: StackTraceElement): String =
        stackElementTag ?: super.createStackElementTag(element) + ":" + element.lineNumber
}

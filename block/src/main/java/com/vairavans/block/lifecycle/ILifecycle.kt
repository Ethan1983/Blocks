package com.vairavans.block.lifecycle

import androidx.lifecycle.Lifecycle

/**
 * Interface for [Lifecycle] events.
 *
 * @see [AbsLifecycleManager]
 */
interface ILifecycle {

    fun onLifecycleResume()

    fun onLifecyclePause()

    fun onLifecycleDestroy()

    fun onLifecycleCreate()

    fun onLifecycleStart()

    fun onLifecycleStop()

    fun onLifecycleAny()
}

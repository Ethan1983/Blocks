package com.vairavans.block.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * Abstract Lifecycle manager for being notified of Lifecycle events. This abstracts the setup and
 * clean up of [LifecycleObserver]
 *
 * @see [ILifecycle]
 */
abstract class AbsLifecycleManager constructor(private val lifeCycle: Lifecycle) : ILifecycle,
    LifecycleObserver {

    init {
        @Suppress("LeakingThis")
        lifeCycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    override fun onLifecycleCreate() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    override fun onLifecycleStart() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    override fun onLifecycleResume() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    override fun onLifecyclePause() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    override fun onLifecycleStop() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun destroy() {
        onLifecycleDestroy()
        lifeCycle.removeObserver(this)
    }

    override fun onLifecycleDestroy() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    override fun onLifecycleAny() {
    }
}

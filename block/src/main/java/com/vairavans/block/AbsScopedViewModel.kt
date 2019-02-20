package com.vairavans.block

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

/**
 * A [ViewModel] for Structured concurrency.
 */
abstract class AbsScopedViewModel constructor(dispatcher : CoroutineDispatcher) : ViewModel() {

    private val viewModelJob = Job()
    protected val viewModelScope = CoroutineScope(dispatcher + viewModelJob)

    @CallSuper
    override fun onCleared() {
        viewModelJob.cancel()
    }
}

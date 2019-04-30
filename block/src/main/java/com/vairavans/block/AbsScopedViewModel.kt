package com.vairavans.block

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

/**
 * A [ViewModel] for Structured concurrency.
 */
abstract class AbsScopedViewModel constructor(dispatcher : CoroutineDispatcher) : ViewModel() {

    protected val viewModelScope = CoroutineScope(dispatcher + SupervisorJob() )

    @ExperimentalCoroutinesApi
    @CallSuper
    override fun onCleared() {
        viewModelScope.cancel()
    }
}

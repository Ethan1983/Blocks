package com.vairavans.block

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

/**
 * A [ViewModel] for Structured concurrency.
 */
@Deprecated(message = "viewModelScope is provided by lifecycle-viewmodel-ktx",
    level = DeprecationLevel.WARNING)
abstract class AbsScopedViewModel constructor(dispatcher : CoroutineDispatcher) : ViewModel() {

    protected val viewModelScope = CoroutineScope(dispatcher + SupervisorJob() )

    @CallSuper
    override fun onCleared() {
        viewModelScope.cancel()
    }
}

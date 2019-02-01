package com.vairavans.block

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

abstract class AbsScopedViewModel constructor(dispatcher : CoroutineDispatcher) : ViewModel() {

    private val viewModelJob = Job()
    protected val viewModelScope = CoroutineScope(dispatcher + viewModelJob)

    @CallSuper
    override fun onCleared() {
        viewModelJob.cancel()
    }
}

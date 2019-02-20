package com.vairavans.block

import com.vairavans.espresso.EspressoCountingGlobalIdlingResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * A version of [CoroutineScope.launch] supporting [EspressoCountingGlobalIdlingResource].
 */
fun CoroutineScope.idlingLaunch(context: CoroutineContext = EmptyCoroutineContext,
                                start: CoroutineStart = CoroutineStart.DEFAULT,
                                block: suspend CoroutineScope.() -> Unit ): Job {

    EspressoCountingGlobalIdlingResource.increment()
    val job = launch( context, start, block )
    job.invokeOnCompletion {
        EspressoCountingGlobalIdlingResource.decrement()
    }

    return job
}

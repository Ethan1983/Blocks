package com.vairavans.espresso

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource

object EspressoCountingGlobalIdlingResource {
    private const val resource = "GLOBAL"
    private val countingIdlingResource = CountingIdlingResource(resource)

    fun increment() = countingIdlingResource.increment()

    fun decrement() = countingIdlingResource.decrement()

    fun getIdlingResource(): IdlingResource = countingIdlingResource
}

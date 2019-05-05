@file:Suppress("MatchingDeclarationName")
package com.vairavans.block

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

data class LiveDataContainer<T>(val liveData : LiveData<T>, val owner: LifecycleOwner)

/*
 * Infix notation for observing LiveData
 *
 * lifecycleOwner observes someLiveData then {
 * }
 */
infix fun <T> LifecycleOwner.observes(data : LiveData<T>) : LiveDataContainer<T> =
    LiveDataContainer( data, this )

/*
 * Infix notation for observing LiveData
 *
 * lifecycleOwner observes someLiveData then {
 * }
 */
inline infix fun <T> LiveDataContainer<T>.then( crossinline block : (T) -> Unit ) {

    liveData.observe( owner, Observer {
        block( it )
    } )
}

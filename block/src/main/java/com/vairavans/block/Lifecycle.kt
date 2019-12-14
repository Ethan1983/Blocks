package com.vairavans.block

import androidx.lifecycle.Lifecycle

/**
 * Extension on [Lifecycle] to invoke specified block only if it is in a [Lifecycle.State.STARTED]
 * state and throw an [IllegalStateException] otherwise.
 *
 * Use this to perform UI updates to ensure they can be restored, if needed. UI updates in a
 * pre-started state will be lost should the process get killed and restored later.
 */
fun Lifecycle.startedStateInvoke( block : () -> Unit ) {

    if( currentState.isAtLeast( Lifecycle.State.STARTED ) ) {
        block()
    } else {
        //Lifecycle is always stopped before state is saved, onSaveInstanceState()
        throw IllegalStateException( "Navigate request with STOPPED lifecycle state" )
    }
}

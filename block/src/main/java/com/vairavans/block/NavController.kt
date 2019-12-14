package com.vairavans.block

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigator

// Do not use Kotlin defaults to avoid function overloads as the relayed overload logic is an implementation detail
// of NavController and can change in future.

/**
 * Same as [NavController.navigate] but throws an [IllegalStateException] when lifecycle is not at least
 * [Lifecycle.State.STARTED] state.
 */
fun NavController.stateSafeNavigate(lifecycle: Lifecycle,
                                    @IdRes resID : Int ) {
    lifecycle.startedStateInvoke {
        navigate( resID )
    }
}

/**
 * Same as [NavController.navigate] but throws an [IllegalStateException] when lifecycle is not at least
 * [Lifecycle.State.STARTED] state.
 */
fun NavController.stateSafeNavigate(lifecycle: Lifecycle,
                                    @IdRes resID : Int,
                                    args : Bundle? = null ) {
    lifecycle.startedStateInvoke {
        navigate( resID, args )
    }
}

/**
 * Same as [NavController.navigate] but throws an [IllegalStateException] when lifecycle is not at least
 * [Lifecycle.State.STARTED] state.
 */
fun NavController.stateSafeNavigate(lifecycle: Lifecycle,
                                    @IdRes resID : Int,
                                    args : Bundle? = null,
                                    navOptions : NavOptions? = null ) {
    lifecycle.startedStateInvoke {
        navigate( resID, args, navOptions )
    }
}

/**
 * Same as [NavController.navigate] but throws an [IllegalStateException] when lifecycle is not at least
 * [Lifecycle.State.STARTED] state.
 */
fun NavController.stateSafeNavigate(lifecycle: Lifecycle,
                                    @IdRes resID : Int,
                                    args : Bundle? = null,
                                    navOptions : NavOptions? = null,
                                    navigatorExtras : Navigator.Extras? = null ) {
    lifecycle.startedStateInvoke {
        navigate( resID, args, navOptions, navigatorExtras )
    }
}

/**
 * Same as [NavController.navigate] but throws an [IllegalStateException] when lifecycle is not at least
 * [Lifecycle.State.STARTED] state.
 */
fun NavController.stateSafeNavigate(lifecycle : Lifecycle,
                                    directions : NavDirections ) {
    lifecycle.startedStateInvoke {
        navigate( directions )
    }
}

/**
 * Same as [NavController.navigate] but throws an [IllegalStateException] when lifecycle is not at least
 * [Lifecycle.State.STARTED] state.
 */
fun NavController.stateSafeNavigate(lifecycle : Lifecycle,
                                    directions : NavDirections,
                                    navOptions : NavOptions? = null ) {
    lifecycle.startedStateInvoke {
        navigate( directions, navOptions )
    }
}

/**
 * Same as [NavController.navigate] but throws an [IllegalStateException] when lifecycle is not at
 * least [Lifecycle.State.STARTED] state.
 */
fun NavController.stateSafeNavigate(lifecycle : Lifecycle,
                                    directions : NavDirections,
                                    navigatorExtras : Navigator.Extras ) {
    lifecycle.startedStateInvoke {
        navigate( directions, navigatorExtras )
    }
}

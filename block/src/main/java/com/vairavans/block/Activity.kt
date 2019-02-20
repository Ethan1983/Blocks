package com.vairavans.block

import android.app.Activity
import android.content.Intent

/**
 * Extension of [Activity] to start activity by class name
 */
inline fun < reified T : Activity> Activity.startActivity() {
    Intent( this, T::class.java ).also {
        startActivity( it )
    }
}

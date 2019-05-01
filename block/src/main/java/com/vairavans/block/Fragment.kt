package com.vairavans.block

import android.content.ActivityNotFoundException
import android.content.Intent
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

/**
 * Extension of [Fragment] to start activity.
 */
inline fun Fragment.startActivity(intent : Intent, activityNotFoundHandler : (Fragment) -> Unit ) {

    try {
        startActivity( intent )
    } catch( e : ActivityNotFoundException ) {
        activityNotFoundHandler(this)
    }
}

/**
 * Same as [Fragment.toast] but takes a string resource id over a [String].
 */
fun Fragment.toast(@StringRes messageResId : Int, duration : Int = Toast.LENGTH_SHORT ) =
    toast( getString( messageResId ), duration )

/**
 * Extension of [Fragment] to display a toast.
 *
 * This should be called only after initialization (needs valid context from [Fragment.requireContext]
 */
fun Fragment.toast(message : String, duration : Int = Toast.LENGTH_SHORT ) =
    requireContext().toast( message, duration )

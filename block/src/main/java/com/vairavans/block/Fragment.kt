package com.vairavans.block

import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.fragment.app.Fragment

/**
 * Extension of [Fragment] to start activity.
 */
inline fun Fragment.startActivity(intent : Intent, activityNotFoundHandler : (Fragment) -> Unit ) {

    try {
        startActivity( intent )
    } catch( e : ActivityNotFoundException) {
        activityNotFoundHandler(this)
    }
}

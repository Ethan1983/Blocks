package com.vairavans.block

import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.fragment.app.Fragment

inline fun Fragment.startActivity(intent : Intent, activityNotFoundHandler : (Fragment) -> Unit ) {

    try {
        startActivity( intent )
    } catch( e : ActivityNotFoundException) {
        activityNotFoundHandler(this)
    }
}

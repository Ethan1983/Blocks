/*
 * Copyright (C) 2019 Vairavan Srinivasan.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vairavans.block

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat

/**
 * Same as [toast] but takes a string resource id over a [String].
 */
fun Context.toast( @StringRes messageResId : Int, duration : Int = Toast.LENGTH_SHORT ) =
    toast( getString( messageResId ), duration )

/**
 * Extension of [Context] to display a toast.
 */
fun Context.toast( message : String, duration : Int = Toast.LENGTH_SHORT ) =
    Toast.makeText( this, message, duration ).show()

/**
 * Extension of [Context] to create and shown an [AlertDialog].
 */
inline fun Context.createAndShowAlertDialog( crossinline block : AlertDialog.Builder.() -> Unit ) : AlertDialog {

    return AlertDialog.Builder( this ).apply {
        block()
    }.show()
}

/**
 * Extension of [Context] to create a [Notification].
 */
inline fun Context.notification( channelId : String, crossinline block : NotificationCompat.Builder.() -> Unit ) :
        Notification {
    return with( NotificationCompat.Builder( this, channelId ) ) {
        block()
        build()
    }
}

/**
 * Extension of [Context] to create a [NotificationChannel].
 */
@RequiresApi(Build.VERSION_CODES.O)
inline fun Context.createNotificationChannel( id : String, name : CharSequence, importance : Int,
                                              block : NotificationChannel.() -> Unit = {} ) : NotificationChannel {

    val channel = NotificationChannel( id, name, importance ).apply( block )
    val notificationManager = getSystemService( Context.NOTIFICATION_SERVICE ) as NotificationManager
    notificationManager.createNotificationChannel( channel )
    return channel
}

/**
 * Extension of [Context] to start an activity using an [Intent].
 */
inline fun Context.startActivity(intent : Intent, activityNotFoundHandler : (Context) -> Unit ) {

    intent.resolveActivity( packageManager )?.let {
        startActivity( intent )
    } ?: run {
        activityNotFoundHandler(this)
    }
}

/**
 * Extension of [Context] to start a foreground service with support for optional parameters.
 */
inline fun <reified T : Service> Context.startForegroundService( block : Intent.() -> Unit = {} ) : Intent {

    return Intent( this, T::class.java ).also {
        block( it )
        ContextCompat.startForegroundService( this, it )
    }
}

/**
 * Extension of [Context] to enable/disable an android component.
 */
inline fun <reified T> Context.setComponentEnabledSetting( newState: Int,
                                                           flags : Int = PackageManager.DONT_KILL_APP ) {
    packageManager.setComponentEnabledSetting(
        ComponentName( this, T::class.java ),
        newState,
        flags
    )
}

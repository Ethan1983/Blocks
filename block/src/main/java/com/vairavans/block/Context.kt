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

fun Context.toast( @StringRes messageResId : Int, duration : Int = Toast.LENGTH_SHORT ) =
    toast( getString( messageResId ), duration )

fun Context.toast( message : String, duration : Int = Toast.LENGTH_SHORT ) =
    Toast.makeText( this, message, duration ).show()

inline fun Context.createAndShowAlertDialog( crossinline block : AlertDialog.Builder.() -> Unit ) : AlertDialog {

    return AlertDialog.Builder( this ).apply {
        block()
    }.show()
}

inline fun Context.notification( channelId : String, crossinline block : NotificationCompat.Builder.() -> Unit ) : Notification {
    return with( NotificationCompat.Builder( this, channelId ) ) {
        block()
        build()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
inline fun Context.createNotificationChannel( id : String, name : CharSequence, importance : Int,
                                              block : NotificationChannel.() -> Unit = {} ) : NotificationChannel {

    val channel = NotificationChannel( id, name, importance ).apply( block )
    val notificationManager = getSystemService( Context.NOTIFICATION_SERVICE ) as NotificationManager
    notificationManager.createNotificationChannel( channel )
    return channel
}

inline fun Context.startActivity(intent : Intent, activityNotFoundHandler : () -> Unit ) {

    intent.resolveActivity( packageManager )?.let {
        startActivity( intent )
    } ?: run {
        activityNotFoundHandler()
    }

}

inline fun <reified T> Context.setComponentEnabledSetting( newState: Int,
                                                           flags : Int = PackageManager.DONT_KILL_APP ) {
    packageManager.setComponentEnabledSetting(
        ComponentName( this, T::class.java ),
        newState,
        flags
    )
}
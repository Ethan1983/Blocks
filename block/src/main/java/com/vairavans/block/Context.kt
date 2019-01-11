/*
 * Copyright (C) 2017 Vairavan Srinivasan.
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
import android.content.Context
import android.widget.Toast
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
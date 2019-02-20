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

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

inline fun getBroadcastReceiver( crossinline handler : BroadcastReceiver.(Context, Intent) -> Unit ) :
        BroadcastReceiver {

    return object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) = handler( context, intent )
    }

}

inline fun < reified T : Any > executeOrHandleNull( value : T?, block : T.() -> Unit = {} ) : T {

    return value?.let {
        block( it )
        it
    } ?: run {
        throw NullPointerException( "Unexpected null type for ${T::class.java}" )
    }

}

inline fun <T, U> multiLet( param1 : T?, param2 : U?, block : (T, U) -> Unit ) {

    if( param1 != null && param2 != null ) {
        block( param1, param2 )
    }

}

@Suppress("PROTECTED_CALL_FROM_PUBLIC_INLINE")
inline fun getTimberDebugTree( crossinline block : () -> String? = { null } ) : DebugTagTree =
    DebugTagTree( block() )

fun showSnackBar(view : View,
                 @StringRes messageResId : Int,
                 @StringRes undoResId : Int,
                 duration : Int = Snackbar.LENGTH_SHORT,
                 undoActionHandler : (() -> Unit)? = null ) : Snackbar {

    val snackBar = Snackbar.make( view, messageResId, duration )

    undoActionHandler?.let {
        snackBar.setAction( undoResId ) {
            undoActionHandler()
        }
    }

    snackBar.show()

    return snackBar
}

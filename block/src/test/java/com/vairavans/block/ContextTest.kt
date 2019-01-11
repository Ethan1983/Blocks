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

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest=Config.NONE)
class ContextTest {

    private lateinit var context : Context

    private class SampleActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setTheme( R.style.Theme_AppCompat )
        }
    }

    @Before
    fun setup() {

        context = Robolectric.buildActivity( SampleActivity::class.java )
            .create()
            .get()

    }

    @Test
    fun `notification should invoke provided lambda to initialize Builder`() {

        var lambdaInvoked = false

        context.notification( "ChannelId" ) {
            lambdaInvoked = true
        }

        assert( lambdaInvoked ) {
            "Context.notification did not invoke the provided lambda to update NotificationCompat.Builder"
        }
    }

    @Test
    fun `notification should use provided values to create notification`() {

        val notification = context.notification( "ChannelId" ) {
            priority = NotificationCompat.PRIORITY_MAX
        }

        @Suppress("DEPRECATION")
        assert( notification.priority == NotificationCompat.PRIORITY_MAX ) {
            "Context.notification did not use the provided values to initialize Notification"
        }
    }

    @Test
    fun `notification should use provided channel id to create notification`() {

        val notification = context.notification( "ChannelId" ) {

        }

        @Suppress("DEPRECATION")
        assert(notification.channelId == "ChannelId") {
            "Context.notification did not use the provided channelId to create Notification"
        }
    }

    @Test
    fun `createAndShowAlertDialog should invoke provided lambda to initialize Builder`() {

        var lambdaInvoked = false

        context.createAndShowAlertDialog {
            lambdaInvoked = true
        }

        assert( lambdaInvoked ) {
            "Context.createAndShowAlertDialog did not invoke the provided lambda to update AlertDialog.Builder"
        }
    }

    @Test
    fun `createAndShowAlertDialog should show the created dialog`() {

        val dialog = context.createAndShowAlertDialog {
        }

        assert( dialog.isShowing ) {
            "Context.createAndShowAlertDialog did not show the created dialog"
        }
    }
}
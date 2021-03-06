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

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat.IMPORTANCE_HIGH
import androidx.core.app.NotificationManagerCompat.IMPORTANCE_LOW
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE,sdk = [Build.VERSION_CODES.P])
class ContextTest {

    private lateinit var context : Context

    private class SampleActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setTheme( R.style.Theme_AppCompat )
        }

        override fun startActivity(intent: Intent) {
            // Empty Implementation
        }
    }

    private class SampleService : Service() {
        override fun onBind(intent: Intent?): IBinder? {
            throw UnsupportedOperationException( "Bind is not supported" )
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

    @Test
    fun `startActivity handles unresolved intent`() {

        var activityNotFoundHandlerInvoked = false
        val intent = mockk<Intent>()
        every { intent.resolveActivity( context.packageManager ) } returns null

        context.startActivity( intent ) {
            activityNotFoundHandlerInvoked = true
        }

        assert(activityNotFoundHandlerInvoked)
    }

    @Test
    fun `startActivity gives same context in activityNotFoundHandler`() {

        var sameContextReceived = false
        val intent = mockk<Intent>()
        every { intent.resolveActivity( context.packageManager ) } returns null

        context.startActivity( intent ) { receivedContext ->
            sameContextReceived = receivedContext == context
        }

        assert(sameContextReceived)
    }

    @Test
    fun `startActivity handles resolved intent`() {

        var activityNotFoundHandlerInvoked = false
        val intent = mockk<Intent>()
        every { intent.resolveActivity( context.packageManager ) } returns mockk()

        context.startActivity( intent ) {
            activityNotFoundHandlerInvoked = true
        }

        assert(!activityNotFoundHandlerInvoked)
    }

    @Test
    fun `createNotificationChannel should invoke provided lambda on specified channel`() {

        var lambdaInvoked = false
        val channelId = NotificationChannel.DEFAULT_CHANNEL_ID
        val channelName = "Channel Name"
        val importance = IMPORTANCE_LOW

        context.createNotificationChannel( channelId, channelName, importance ) {
            lambdaInvoked = true
        }

        assert( lambdaInvoked ) {
            "Context.createNotificationChannel did not invoke provided lambda on the specified channel"
        }
    }

    @Test
    fun `createNotificationChannel should apply provided values on specified channel`() {

        val channelId = NotificationChannel.DEFAULT_CHANNEL_ID
        val channelName = "Channel Name"
        val importance = IMPORTANCE_HIGH

        val channel = context.createNotificationChannel( channelId, channelName, importance )

        assert( channel.id == channelId ) {
            "Context.createNotificationChannel did not apply provided ID on the specified channel"
        }

        assert( channel.name == channelName ) {
            "Context.createNotificationChannel did not apply provided Name on the specified channel"
        }

        assert( channel.importance == importance ) {
            "Context.createNotificationChannel did not apply provided Importance on the specified channel"
        }
    }

    @Test
    fun `createNotificationChannel should apply provided lambda on specified channel`() {

        val channelId = NotificationChannel.DEFAULT_CHANNEL_ID
        val channelName = "Channel Name"
        val channelDescription = "Channel Description"
        val importance = IMPORTANCE_LOW

        val channel = context.createNotificationChannel( channelId, channelName, importance ) {
            description = channelDescription
        }

        assert( channel.description == channelDescription ) {
            "Context.createNotificationChannel did not apply provided lambda on the specified channel"
        }
    }

    @Test
    fun `createNotificationChannel registers channel with NotificationManager`() {

        val channelId = NotificationChannel.DEFAULT_CHANNEL_ID
        val channelName = "Channel Name"
        val channelDescription = "Channel Description"
        val importance = IMPORTANCE_LOW
        val notificationManager = context.getSystemService( Context.NOTIFICATION_SERVICE ) as NotificationManager

        context.createNotificationChannel( channelId, channelName, importance ) {
            description = channelDescription
        }

        val createdChannel = notificationManager.getNotificationChannel( channelId )

        assertNotNull( "Channel not registered with NotificationManager", createdChannel )

        assert( createdChannel.id == channelId ) {
            "Context.createNotificationChannel did not apply provided ID on the specified channel"
        }

        assert( createdChannel.name == channelName ) {
            "Context.createNotificationChannel did not apply provided Name on the specified channel"
        }

        assert( createdChannel.importance == importance ) {
            "Context.createNotificationChannel did not apply provided Importance on the specified channel"
        }

        assert( createdChannel.description == channelDescription ) {
            "Context.createNotificationChannel did not apply provided Description on the specified channel"
        }
    }

    @Test
    fun `startForegroundService invokes provided lambda`() {

        var lambdaInvoked = false

        context.startForegroundService<SampleService> {
            lambdaInvoked = true
        }

        assert( lambdaInvoked ) {
            "Context.startForegroundService did not invoke provided lambda"
        }
    }

    @Test
    fun `startForegroundService applies provided values on returned intent`() {

        val key = "Key"
        val value = "Value"

        val intent = context.startForegroundService<SampleService> {
            putExtra( key, value )
        }

        assertNotNull( intent )

        assert( intent.getStringExtra( key ) == value ) {
            "Context.startForegroundService did not apply provided value on returned intent"
        }
    }

    @Test
    fun `setComponentEnabledSetting enables disabled component`() {

        val component = ComponentName( context, SampleService::class.java )

        context.packageManager.setComponentEnabledSetting( component, PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP )

        context.setComponentEnabledSetting<SampleService>( PackageManager.COMPONENT_ENABLED_STATE_ENABLED )

        assert( context.packageManager.getComponentEnabledSetting( component ) ==
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED )
    }

    @Test
    fun `setComponentEnabledSetting does not enable on disable request of disabled component`() {

        val component = ComponentName( context, SampleService::class.java )

        context.packageManager.setComponentEnabledSetting( component, PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP )

        context.setComponentEnabledSetting<SampleService>( PackageManager.COMPONENT_ENABLED_STATE_DISABLED )

        assert( context.packageManager.getComponentEnabledSetting( component ) !=
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED )
    }

    @Test
    fun `setComponentEnabledSetting disables enabled component`() {

        val component = ComponentName( context, SampleService::class.java )

        context.packageManager.setComponentEnabledSetting( component, PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP )

        context.setComponentEnabledSetting<SampleService>( PackageManager.COMPONENT_ENABLED_STATE_DISABLED )

        assert( context.packageManager.getComponentEnabledSetting( component ) ==
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED )

    }

    @Test
    fun `setComponentEnabledSetting does not disable on enable request of enabled component`() {

        val component = ComponentName( context, SampleService::class.java )

        context.packageManager.setComponentEnabledSetting( component, PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP )

        context.setComponentEnabledSetting<SampleService>( PackageManager.COMPONENT_ENABLED_STATE_ENABLED )

        assert( context.packageManager.getComponentEnabledSetting( component ) !=
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED )
    }
}

package com.vairavans.block

import android.os.Build
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE,sdk = [Build.VERSION_CODES.P])
class DrawerLayoutTest {

    private val drawerLayout = DrawerLayout(mockk(relaxed = true))
    lateinit var listeners : List<DrawerLayout.DrawerListener>

    @Before
    fun setup() {

        val listenersField = DrawerLayout::class.java.getDeclaredField("mListeners").also {
            it.isAccessible = true
        }

        // Add a dummy listener to ensure DrawerLayout creates mListeners
        drawerLayout.addDrawerListener( object: DrawerLayout.DrawerListener {
            override fun onDrawerStateChanged(newState: Int) {
            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            }

            override fun onDrawerClosed(drawerView: View) {
            }

            override fun onDrawerOpened(drawerView: View) {
            }
        })

        @Suppress("UNCHECKED_CAST")
        listeners = listenersField.get(drawerLayout) as List<DrawerLayout.DrawerListener>
    }

    @Test
    fun `registerDrawerOpenListener invokes callback with true when navDoor is opened`() {

        verifyDrawerOpenBehavior(expectedDoorOpen = true) {
            it.onDrawerOpened(mockk())
        }
    }

    @Test
    fun `registerDrawerOpenListener invokes callback with false when navDoor is closed`() {

        verifyDrawerOpenBehavior(expectedDoorOpen = false) {
            it.onDrawerClosed(mockk())
        }
    }

    private fun verifyDrawerOpenBehavior(expectedDoorOpen: Boolean, block: (DrawerLayout.DrawerListener) -> Unit) {

        var isDoorOpen = false

        drawerLayout.registerDrawerOpenListener( mockk(relaxed = true) ) { _, doorOpen ->
            isDoorOpen = doorOpen
        }

        listeners.forEach {
            block(it)
        }

        assertThat(isDoorOpen).isEqualTo(expectedDoorOpen)
    }

    @Test
    fun `registerDrawerOpenListener ignores callback for destroyed LifeCycle`() {
        verifyCallback( state = Lifecycle.State.DESTROYED, expectedCallbackInvoked = false )
    }

    @Test
    fun `registerDrawerOpenListener ignores callback for created LifeCycle`() {
        verifyCallback( state = Lifecycle.State.CREATED, expectedCallbackInvoked = true )
    }

    @Test
    fun `registerDrawerOpenListener ignores callback for started LifeCycle`() {
        verifyCallback( state = Lifecycle.State.STARTED, expectedCallbackInvoked = true )
    }

    @Test
    fun `registerDrawerOpenListener ignores callback for resumed LifeCycle`() {
        verifyCallback( state = Lifecycle.State.RESUMED, expectedCallbackInvoked = true )
    }

    @Test
    fun `registerDrawerOpenListener ignores callback for initialized LifeCycle`() {
        verifyCallback( state = Lifecycle.State.INITIALIZED, expectedCallbackInvoked = true )
    }

    private fun verifyCallback(state : Lifecycle.State, expectedCallbackInvoked: Boolean) {

        val lifecycleOwner = mockk<LifecycleOwner>(relaxed = true)
        var callbackInvoked = false

        every { lifecycleOwner.lifecycle.currentState } returns state

        drawerLayout.registerDrawerOpenListener(lifecycleOwner) { _, _ ->
            callbackInvoked = true
        }

        listeners.forEach {
            it.onDrawerOpened(mockk())
        }

        assertThat(callbackInvoked).isEqualTo(expectedCallbackInvoked)
    }

    @Test
    fun `registerDrawerOpenListener invokes callback with expected view`() {

        lateinit var navView : View
        val actualNavView = mockk<View>()

        drawerLayout.registerDrawerOpenListener( mockk(relaxed = true) ) { view, _ ->
            navView = view
        }

        listeners.forEach {
            it.onDrawerOpened(actualNavView)
        }

        assertThat(navView).isSameInstanceAs(actualNavView)
    }

}
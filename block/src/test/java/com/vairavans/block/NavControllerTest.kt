package com.vairavans.block

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.lang.IllegalStateException

@RunWith(JUnit4::class)
class NavControllerTest {

    private val navController = mockk<NavController>( relaxed = true )
    private val lifeCycle = mockk<Lifecycle>()

    @Test(expected = IllegalStateException::class)
    fun `stateSafeNavigate resId fails in INITIALIZED State`() {
        callStateSafeNavigateResId( Lifecycle.State.INITIALIZED )
    }

    @Test(expected = IllegalStateException::class)
    fun `stateSafeNavigate resId fails in CREATED State`() {
        callStateSafeNavigateResId( Lifecycle.State.CREATED )
    }

    @Test
    fun `stateSafeNavigate resId works as expected in STARTED State`() {
        callStateSafeNavigateResId( Lifecycle.State.STARTED )
    }

    @Test
    fun `stateSafeNavigate resId works as expected in RESUMED State`() {
        callStateSafeNavigateResId( Lifecycle.State.RESUMED )
    }

    @Test(expected = IllegalStateException::class)
    fun `stateSafeNavigate resId fails in DESTROYED State`() {
        callStateSafeNavigateResId( Lifecycle.State.DESTROYED )
    }

    private fun callStateSafeNavigateResId( state : Lifecycle.State ) {
        every { lifeCycle.currentState } returns state
        navController.stateSafeNavigate( lifeCycle, 12 )
    }

    @Test(expected = IllegalStateException::class)
    fun `stateSafeNavigate resId Bundle fails in INITIALIZED State`() {
        callStateSafeNavigateResIdAndBundle( Lifecycle.State.INITIALIZED )
    }

    @Test(expected = IllegalStateException::class)
    fun `stateSafeNavigate resId Bundle fails in CREATED State`() {
        callStateSafeNavigateResIdAndBundle( Lifecycle.State.CREATED )
    }

    @Test
    fun `stateSafeNavigate resId Bundle works as expected in STARTED State`() {
        callStateSafeNavigateResIdAndBundle( Lifecycle.State.STARTED )
    }

    @Test
    fun `stateSafeNavigate resId Bundle works as expected in RESUMED State`() {
        callStateSafeNavigateResIdAndBundle( Lifecycle.State.RESUMED )
    }

    @Test(expected = IllegalStateException::class)
    fun `stateSafeNavigate resId Bundle fails in DESTROYED State`() {
        callStateSafeNavigateResIdAndBundle( Lifecycle.State.DESTROYED )
    }

    private fun callStateSafeNavigateResIdAndBundle( state : Lifecycle.State ) {
        every { lifeCycle.currentState } returns state
        navController.stateSafeNavigate( lifeCycle, 12, Bundle() )
    }

    @Test(expected = IllegalStateException::class)
    fun `stateSafeNavigate resId Bundle Options fails in INITIALIZED State`() {
        callStateSafeNavigateResIdBundleAndNavOptions( Lifecycle.State.INITIALIZED )
    }

    @Test(expected = IllegalStateException::class)
    fun `stateSafeNavigate resId Bundle Options fails in CREATED State`() {
        callStateSafeNavigateResIdBundleAndNavOptions( Lifecycle.State.CREATED )
    }

    @Test
    fun `stateSafeNavigate resId Bundle Options works as expected in STARTED State`() {
        callStateSafeNavigateResIdBundleAndNavOptions( Lifecycle.State.STARTED )
    }

    @Test
    fun `stateSafeNavigate resId Bundle Options works as expected in RESUMED State`() {
        callStateSafeNavigateResIdBundleAndNavOptions( Lifecycle.State.RESUMED )
    }

    @Test(expected = IllegalStateException::class)
    fun `stateSafeNavigate resId Bundle Options fails in DESTROYED State`() {
        callStateSafeNavigateResIdBundleAndNavOptions( Lifecycle.State.DESTROYED )
    }

    private fun callStateSafeNavigateResIdBundleAndNavOptions( state : Lifecycle.State ) {
        every { lifeCycle.currentState } returns state
        navController.stateSafeNavigate( lifeCycle, 12, Bundle(), navOptions = null )
    }

    @Test(expected = IllegalStateException::class)
    fun `stateSafeNavigate resId Bundle Options Extras fails in INITIALIZED State`() {
        callStateSafeNavigateResIdBundleNavOptionsAndNavExtras( Lifecycle.State.INITIALIZED )
    }

    @Test(expected = IllegalStateException::class)
    fun `stateSafeNavigate resId Bundle Options Extras fails in CREATED State`() {
        callStateSafeNavigateResIdBundleNavOptionsAndNavExtras( Lifecycle.State.CREATED )
    }

    @Test
    fun `stateSafeNavigate resId Bundle Options Extras works as expected in STARTED State`() {
        callStateSafeNavigateResIdBundleNavOptionsAndNavExtras( Lifecycle.State.STARTED )
    }

    @Test
    fun `stateSafeNavigate resId Bundle Options Extras works as expected in RESUMED State`() {
        callStateSafeNavigateResIdBundleNavOptionsAndNavExtras( Lifecycle.State.RESUMED )
    }

    @Test(expected = IllegalStateException::class)
    fun `stateSafeNavigate resId Bundle Options Extras fails in DESTROYED State`() {
        callStateSafeNavigateResIdBundleNavOptionsAndNavExtras( Lifecycle.State.DESTROYED )
    }

    private fun callStateSafeNavigateResIdBundleNavOptionsAndNavExtras( state : Lifecycle.State ) {
        every { lifeCycle.currentState } returns state
        navController.stateSafeNavigate( lifeCycle, 12, Bundle(), navOptions = null, navigatorExtras = null )
    }

    @Test(expected = IllegalStateException::class)
    fun `stateSafeNavigate directions in INITIALIZED State`() {
        callStateSafeNavigateDirections( Lifecycle.State.INITIALIZED )
    }

    @Test(expected = IllegalStateException::class)
    fun `stateSafeNavigate directions fails in CREATED State`() {
        callStateSafeNavigateDirections( Lifecycle.State.CREATED )
    }

    @Test
    fun `stateSafeNavigate directions works as expected in STARTED State`() {
        callStateSafeNavigateDirections( Lifecycle.State.STARTED )
    }

    @Test
    fun `stateSafeNavigate directions works as expected in RESUMED State`() {
        callStateSafeNavigateDirections( Lifecycle.State.RESUMED )
    }

    @Test(expected = IllegalStateException::class)
    fun `stateSafeNavigate directions fails in DESTROYED State`() {
        callStateSafeNavigateDirections( Lifecycle.State.DESTROYED )
    }

    private fun callStateSafeNavigateDirections( state : Lifecycle.State ) {
        every { lifeCycle.currentState } returns state
        navController.stateSafeNavigate( lifeCycle, mockk<NavDirections>() )
    }

    @Test(expected = IllegalStateException::class)
    fun `stateSafeNavigate directions Options in INITIALIZED State`() {
        callStateSafeNavigateDirectionsAndOptions( Lifecycle.State.INITIALIZED )
    }

    @Test(expected = IllegalStateException::class)
    fun `stateSafeNavigate directions Options fails in CREATED State`() {
        callStateSafeNavigateDirectionsAndOptions( Lifecycle.State.CREATED )
    }

    @Test
    fun `stateSafeNavigate directions Options works as expected in STARTED State`() {
        callStateSafeNavigateDirectionsAndOptions( Lifecycle.State.STARTED )
    }

    @Test
    fun `stateSafeNavigate directions Options works as expected in RESUMED State`() {
        callStateSafeNavigateDirectionsAndOptions( Lifecycle.State.RESUMED )
    }

    @Test(expected = IllegalStateException::class)
    fun `stateSafeNavigate directions Options fails in DESTROYED State`() {
        callStateSafeNavigateDirectionsAndOptions( Lifecycle.State.DESTROYED )
    }

    private fun callStateSafeNavigateDirectionsAndOptions( state : Lifecycle.State ) {
        every { lifeCycle.currentState } returns state
        navController.stateSafeNavigate( lifeCycle, mockk<NavDirections>(), null )
    }

    @Test(expected = IllegalStateException::class)
    fun `stateSafeNavigate directions Extras in INITIALIZED State`() {
        callStateSafeNavigateDirectionsAndExtras( Lifecycle.State.INITIALIZED )
    }

    @Test(expected = IllegalStateException::class)
    fun `stateSafeNavigate directions Extras fails in CREATED State`() {
        callStateSafeNavigateDirectionsAndExtras( Lifecycle.State.CREATED )
    }

    @Test
    fun `stateSafeNavigate directions Extras works as expected in STARTED State`() {
        callStateSafeNavigateDirectionsAndExtras( Lifecycle.State.STARTED )
    }

    @Test
    fun `stateSafeNavigate directions Extras works as expected in RESUMED State`() {
        callStateSafeNavigateDirectionsAndExtras( Lifecycle.State.RESUMED )
    }

    @Test(expected = IllegalStateException::class)
    fun `stateSafeNavigate directions Extras fails in DESTROYED State`() {
        callStateSafeNavigateDirectionsAndExtras( Lifecycle.State.DESTROYED )
    }

    private fun callStateSafeNavigateDirectionsAndExtras( state : Lifecycle.State ) {
        every { lifeCycle.currentState } returns state
        navController.stateSafeNavigate( lifeCycle, mockk(), mockk<Navigator.Extras>() )
    }

    // Tests to ensure stateSafeNavigate avoids Kotlin defaults assuming implementation detail of NavController

    @Test
    fun `stateSafeNavigate resId relays to corresponding navigate version`() {
        every { lifeCycle.currentState } returns Lifecycle.State.RESUMED
        navController.stateSafeNavigate( lifeCycle, 12 )
        verify { navController.navigate( 12 ) }
    }

    @Test
    fun `stateSafeNavigate resId Bundles relays to corresponding navigate version`() {
        every { lifeCycle.currentState } returns Lifecycle.State.RESUMED
        val bundle = mockk<Bundle>()
        navController.stateSafeNavigate( lifeCycle, 12, bundle )
        verify { navController.navigate( 12, bundle ) }
    }

    @Test
    fun `stateSafeNavigate resId Bundles navOptions relays to corresponding navigate version`() {
        every { lifeCycle.currentState } returns Lifecycle.State.RESUMED
        val bundle = mockk<Bundle>()
        val navOptions = mockk<NavOptions>()
        navController.stateSafeNavigate( lifeCycle, 12, bundle, navOptions )
        verify { navController.navigate( 12, bundle, navOptions ) }
    }

    @Test
    fun `stateSafeNavigate resId Bundles navOptions extras relays to corresponding navigate version`() {
        every { lifeCycle.currentState } returns Lifecycle.State.RESUMED
        val bundle = mockk<Bundle>()
        val navOptions = mockk<NavOptions>()
        val navExtras = mockk<Navigator.Extras>()
        navController.stateSafeNavigate( lifeCycle, 12, bundle, navOptions, navExtras )
        verify { navController.navigate( 12, bundle, navOptions, navExtras ) }
    }

    @Test
    fun `stateSafeNavigate directions relays to corresponding navigate version`() {
        every { lifeCycle.currentState } returns Lifecycle.State.RESUMED
        val directions = mockk<NavDirections>()
        navController.stateSafeNavigate( lifeCycle, directions )
        verify { navController.navigate( directions ) }
    }

    @Test
    fun `stateSafeNavigate directions navOptions relays to corresponding navigate version`() {
        every { lifeCycle.currentState } returns Lifecycle.State.RESUMED
        val directions = mockk<NavDirections>()
        val navOptions = mockk<NavOptions>()
        navController.stateSafeNavigate( lifeCycle, directions, navOptions )
        verify { navController.navigate( directions, navOptions ) }
    }

    @Test
    fun `stateSafeNavigate directions extras relays to corresponding navigate version`() {
        every { lifeCycle.currentState } returns Lifecycle.State.RESUMED
        val directions = mockk<NavDirections>()
        val navExtras = mockk<Navigator.Extras>()
        navController.stateSafeNavigate( lifeCycle, directions, navExtras )
        verify { navController.navigate( directions, navExtras ) }
    }
}

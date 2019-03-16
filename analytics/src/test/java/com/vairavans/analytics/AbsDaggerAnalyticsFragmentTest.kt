package com.vairavans.analytics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import io.mockk.Called
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class AbsDaggerAnalyticsFragmentTest {

    class TestViewModel : ViewModel()

    open class TestEnabledFragment : AbsDaggerAnalyticsFragment<TestViewModel>() {

        override var firebaseAnalyticsScreenName: String = "TestFragment"
        override var firebaseAnalyticsScreenClassOverride: String = "TestFragmentOverride"
        override var viewModelClass: Class<TestViewModel> = TestViewModel::class.java

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?,
            viewModel: TestViewModel
        ): View {
            return View(inflater.context)
        }

        fun performSomeActionGeneratingAnalyticsEvent( event : String ) {
            logAnalyticsEvent( event )
        }

        override fun getActivityInternal() = mockk<FragmentActivity>()
    }

    class TestDisabledFragment : TestEnabledFragment() {
        override var enableAnalytics: Boolean = false
    }

    private val analyticsEnabledFragment = TestEnabledFragment().apply {
        firebaseAnalytics = mockk()
        every { firebaseAnalytics.setCurrentScreen( any(), any(), any() ) } answers {}
        every { firebaseAnalytics.logEvent( any(), any() ) } answers {}
    }

    private val analyticsDisabledFragment = TestDisabledFragment().apply {
        firebaseAnalytics = mockk()
        every { firebaseAnalytics.setCurrentScreen( any(), any(), any() ) } answers {}
        every { firebaseAnalytics.logEvent( any(), any() ) } answers {}
    }

    @Test
    fun `fragment resume should set analytics screen`() {

        analyticsEnabledFragment.onResume()

        verify { analyticsEnabledFragment.firebaseAnalytics.setCurrentScreen( any(), "TestFragment", "TestFragmentOverride" ) }
    }

    @Test
    fun `fragment pause should reset analytics screen`() {

        analyticsEnabledFragment.onPause()

        verify { analyticsEnabledFragment.firebaseAnalytics.setCurrentScreen( any(), null, null ) }
    }

    @Test
    fun `fragment pause after resume should reset analytics screen`() {

        analyticsEnabledFragment.onResume()

        verify { analyticsEnabledFragment.firebaseAnalytics.setCurrentScreen( any(), "TestFragment", "TestFragmentOverride" ) }

        analyticsEnabledFragment.onPause()

        verify { analyticsEnabledFragment.firebaseAnalytics.setCurrentScreen( any(), null, null ) }
    }

    @Test
    fun `fragment resume after pause should set analytics screen`() {

        analyticsEnabledFragment.onPause()

        verify { analyticsEnabledFragment.firebaseAnalytics.setCurrentScreen( any(), null, null ) }

        analyticsEnabledFragment.onResume()

        verify { analyticsEnabledFragment.firebaseAnalytics.setCurrentScreen( any(), "TestFragment", "TestFragmentOverride" ) }
    }

    @Test
    fun `Event is logged when requested`() {

        val event = "Event"

        analyticsEnabledFragment.performSomeActionGeneratingAnalyticsEvent( event )

        verify { analyticsEnabledFragment.firebaseAnalytics.logEvent( event, null ) }
    }

    @Test
    fun `fragment resume should not set analytics screen on disabled analytics`() {

        analyticsDisabledFragment.onResume()

        verify { analyticsDisabledFragment.firebaseAnalytics wasNot Called }
    }

    @Test
    fun `fragment pause should not reset analytics screen on disabled analytics`() {

        analyticsDisabledFragment.onPause()

        verify { analyticsDisabledFragment.firebaseAnalytics wasNot Called }
    }

    @Test
    fun `Event is not logged when requested on disabled analytics`() {

        val event = "Event"

        analyticsDisabledFragment.performSomeActionGeneratingAnalyticsEvent( event )

        verify { analyticsDisabledFragment.firebaseAnalytics wasNot Called }
    }

}

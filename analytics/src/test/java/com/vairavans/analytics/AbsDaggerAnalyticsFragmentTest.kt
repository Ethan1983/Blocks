package com.vairavans.analytics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class AbsDaggerAnalyticsFragmentTest {

    class TestViewModel : ViewModel()

    class TestFragment : AbsDaggerAnalyticsFragment<TestViewModel>() {

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

        override fun getActivityInternal() = mockk<FragmentActivity>()
    }

    private val fragment = TestFragment().apply {
        firebaseAnalytics = mockk()
        every { firebaseAnalytics.setCurrentScreen( any(), any(), any() ) } answers {}
    }

    @Test
    fun `fragment resume should set analytics screen`() {

        fragment.onResume()

        verify { fragment.firebaseAnalytics.setCurrentScreen( any(), "TestFragment", "TestFragmentOverride" ) }
    }

    @Test
    fun `fragment pause should reset analytics screen`() {

        fragment.onPause()

        verify { fragment.firebaseAnalytics.setCurrentScreen( any(), null, null ) }
    }

    @Test
    fun `fragment pause after resume should reset analytics screen`() {

        fragment.onResume()

        verify { fragment.firebaseAnalytics.setCurrentScreen( any(), "TestFragment", "TestFragmentOverride" ) }

        fragment.onPause()

        verify { fragment.firebaseAnalytics.setCurrentScreen( any(), null, null ) }
    }

    @Test
    fun `fragment resume after pause should set analytics screen`() {

        fragment.onPause()

        verify { fragment.firebaseAnalytics.setCurrentScreen( any(), null, null ) }

        fragment.onResume()

        verify { fragment.firebaseAnalytics.setCurrentScreen( any(), "TestFragment", "TestFragmentOverride" ) }
    }
}

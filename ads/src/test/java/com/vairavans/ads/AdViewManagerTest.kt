package com.vairavans.ads

import androidx.lifecycle.Lifecycle
import com.google.android.gms.ads.AdView
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class AdViewManagerTest {

    private val adView = mockk<AdView> {
        every{ resume() } just Runs
        every{ pause() } just Runs
        every{ destroy() } just Runs
    }

    private val lifeCycle = mockk<Lifecycle> {
        every { addObserver( any() ) } just Runs
        every { removeObserver( any() ) } just Runs
    }

    private val adViewManager = AdViewManager( lifeCycle, adView )

    @Test
    fun `AdViewManager invokes resume on AdView`() {

        adViewManager.onLifecycleResume()

        verify { adView.resume() }
    }

    @Test
    fun `AdViewManager invokes pause on AdView`() {

        adViewManager.onLifecyclePause()

        verify { adView.pause() }
    }

    @Test
    fun `AdViewManager invokes destroy on AdView`() {

        adViewManager.onLifecycleDestroy()

        verify { adView.destroy() }
    }
}
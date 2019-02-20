package com.vairavans.ads

import android.content.Context
import androidx.lifecycle.Lifecycle
import com.google.android.gms.ads.reward.RewardedVideoAd
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RewardedVideoAdManagerTest {

    private val rewardedVideoAd = mockk<RewardedVideoAd> {
        every{ resume( any() ) } just Runs
        every{ pause( any() ) } just Runs
        every{ destroy( any() ) } just Runs
    }

    private val context = mockk<Context>()

    private val lifeCycle = mockk<Lifecycle> {
        every { addObserver( any() ) } just Runs
        every { removeObserver( any() ) } just Runs
    }

    private val rewardedVideoAdManager = RewardedVideoAdManager( context, lifeCycle, rewardedVideoAd )

    @Test
    fun `RewardedVideoAdManager invokes respective methods on RewardedVideoAd`() {

        rewardedVideoAdManager.resume()
        verify { rewardedVideoAd.resume( any() ) }

        rewardedVideoAdManager.pause()
        verify { rewardedVideoAd.pause( any() ) }

        rewardedVideoAdManager.destroy()
        verify { rewardedVideoAd.destroy( any() ) }
    }

    @Test
    fun `RewardedVideoAdManager uses provided context for methods on RewardedVideoAd`() {

        rewardedVideoAdManager.resume()
        verify { rewardedVideoAd.resume( context ) }

        rewardedVideoAdManager.pause()
        verify { rewardedVideoAd.pause( context ) }

        rewardedVideoAdManager.destroy()
        verify { rewardedVideoAd.destroy( context ) }
    }
}

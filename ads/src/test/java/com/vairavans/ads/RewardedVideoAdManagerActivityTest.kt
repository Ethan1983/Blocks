package com.vairavans.ads

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.reward.RewardedVideoAd
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE,sdk = [Build.VERSION_CODES.P])
class RewardedVideoAdManagerActivityTest {

    private class SampleActivity : AppCompatActivity() {

        lateinit var rewardedVideoAdManager: RewardedVideoAdManager

        val rewardedVideoAd = mockk<RewardedVideoAd> {
            every{ resume( any() ) } just Runs
            every{ pause( any() ) } just Runs
            every{ destroy( any() ) } just Runs
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setTheme(R.style.Theme_AppCompat)
            rewardedVideoAdManager = RewardedVideoAdManager( this, lifecycle, rewardedVideoAd )
        }
    }

    @Test
    fun `RewardedVideoAd methods are invoked upon Activity lifecycle events`() {

        val controller = Robolectric.buildActivity( SampleActivity::class.java ).create()
        val activity = controller.get()
        val rewardedVideoAd = activity.rewardedVideoAd

        controller.resume()

        verify { rewardedVideoAd.resume( any() ) }

        controller.pause()

        verify { rewardedVideoAd.pause( any() ) }

        controller.destroy()

        verify { rewardedVideoAd.destroy( any() ) }
    }
}

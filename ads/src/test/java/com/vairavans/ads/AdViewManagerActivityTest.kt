package com.vairavans.ads

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdView
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
class AdViewManagerActivityTest {

    private class SampleActivity : AppCompatActivity() {

        lateinit var adViewManager: AdViewManager

        val adView = mockk<AdView> {
            every{ resume() } just Runs
            every{ pause() } just Runs
            every{ destroy() } just Runs
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            setTheme(R.style.Theme_AppCompat)

            adViewManager = AdViewManager( lifecycle, adView )
        }
    }

    @Test
    fun `AdView methods are invoked upon Activity lifecycle events`() {

        val controller = Robolectric.buildActivity( SampleActivity::class.java ).create()
        val activity = controller.get()
        val adView = activity.adView

        controller.resume()

        verify { adView.resume() }

        controller.pause()

        verify { adView.pause() }

        controller.destroy()

        verify { adView.destroy() }
    }
}

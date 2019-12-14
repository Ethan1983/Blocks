package com.vairavans.ads

import androidx.lifecycle.Lifecycle
import com.google.android.gms.ads.AdView
import com.vairavans.block.lifecycle.AbsLifecycleManager

/**
 * Lifecycle manager for [AdView]
 */
class AdViewManager constructor(lifeCycle : Lifecycle, private val adView : AdView)
    : AbsLifecycleManager(lifeCycle) {

    override fun onLifecycleResume() {
        adView.resume()
    }

    override fun onLifecyclePause() {
        adView.pause()
    }

    override fun onLifecycleDestroy() {
        adView.destroy()
    }
}

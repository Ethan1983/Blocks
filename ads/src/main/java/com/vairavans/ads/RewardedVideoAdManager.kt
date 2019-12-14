package com.vairavans.ads

import android.content.Context
import androidx.lifecycle.Lifecycle
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.vairavans.block.lifecycle.AbsLifecycleManager

/**
 * Lifecycle manager for [RewardedVideoAd]
 */
class RewardedVideoAdManager constructor(private val context : Context, lifeCycle: Lifecycle,
                                         private val videoAd : RewardedVideoAd )
    : AbsLifecycleManager(lifeCycle) {

    override fun onLifecycleResume() {
        videoAd.resume( context )
    }

    override fun onLifecyclePause() {
        videoAd.pause( context )
    }

    override fun onLifecycleDestroy() {
        videoAd.destroy( context )
    }
}

package com.vairavans.ads

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.gms.ads.reward.RewardedVideoAd

/**
 * Lifecycle manager for [RewardedVideoAd]
 */
class RewardedVideoAdManager constructor( private val context : Context,
                                          private val lifeCycle: Lifecycle,
                                          private val videoAd : RewardedVideoAd ) : LifecycleObserver {

    init {
        lifeCycle.addObserver( this )
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun resume() {
        videoAd.resume( context )
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun pause() {
        videoAd.pause( context )
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun destroy() {
        videoAd.destroy( context )
        lifeCycle.removeObserver( this )
    }
}

package com.vairavans.ads

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.gms.ads.AdView

class AdViewManager constructor( private val lifeCycle : Lifecycle, private val adView :AdView ) : LifecycleObserver {

    init {
        lifeCycle.addObserver( this )
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun resume() {
        adView.resume()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun pause() {
        adView.pause()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun destroy() {
        adView.destroy()
        lifeCycle.removeObserver( this )
    }
}

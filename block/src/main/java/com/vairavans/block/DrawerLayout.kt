package com.vairavans.block

import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

inline fun DrawerLayout.registerDrawerOpenListener(owner: LifecycleOwner, crossinline callback: (View,Boolean) -> Unit) {

    val lifecycle = owner.lifecycle
    if (lifecycle.currentState == Lifecycle.State.DESTROYED) {
        return
    }

    val listener = object : DrawerLayout.DrawerListener {
        override fun onDrawerStateChanged(newState: Int) {
        }

        override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
        }

        override fun onDrawerClosed(drawerView: View) {
            callback(drawerView, false)
        }

        override fun onDrawerOpened(drawerView: View) {
            callback(drawerView, true)
        }
    }

    lifecycle.addObserver(object: LifecycleObserver {

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroyed(source: LifecycleOwner) {
            removeDrawerListener(listener)
        }
    })

    addDrawerListener(listener)
}

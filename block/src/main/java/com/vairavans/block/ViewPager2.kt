package com.vairavans.block

import androidx.viewpager2.widget.ViewPager2

/**
 * An extension of [ViewPager2] to register [onPageScrollStateChanged],[onPageScrolled] and [onPageSelected] listeners.
 */
inline fun ViewPager2.registerOnPageChangeCallback(
    crossinline onPageSelected : (Int) -> Unit = {},
    crossinline onPageScrollStateChanged : (Int) -> Unit = {},
    crossinline onPageScrolled : (Int, Float, Int) -> Unit = { _,_,_ -> } ) {

    registerOnPageChangeCallback( object : ViewPager2.OnPageChangeCallback() {

        override fun onPageSelected(position: Int) {
            onPageSelected(position)
        }

        override fun onPageScrollStateChanged(state: Int) {
            onPageScrollStateChanged(state)
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            onPageScrolled(position, positionOffset, positionOffsetPixels)
        }

    })
}

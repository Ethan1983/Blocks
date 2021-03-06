/*
 * Copyright (C) 2019 Vairavan Srinivasan.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vairavans.block

import androidx.viewpager.widget.ViewPager

/**
 * An extension of [ViewPager] to register [onPageScrollStateChanged],[onPageScrolled] and [onPageSelected] listeners.
 */
inline fun ViewPager.addOnPageChangeListener ( crossinline onPageScrollStateChanged : (Int) -> Unit = {},
                                               crossinline onPageScrolled : (Int, Float, Int) -> Unit = { _, _, _ -> },
                                               crossinline onPageSelected : (Int) -> Unit = {} )
{
    addOnPageChangeListener( object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {
            onPageScrollStateChanged( state )
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            onPageScrolled( position, positionOffset, positionOffsetPixels )
        }

        override fun onPageSelected(position: Int) {
            onPageSelected( position )
        }
    })
}

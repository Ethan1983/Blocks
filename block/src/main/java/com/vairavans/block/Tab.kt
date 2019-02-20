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

import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBar.Tab
import androidx.fragment.app.FragmentTransaction

/**
 * Extension of [Tab] to register [onTabSelected], [onTabUnselected] and [onTabReselected] listeners.
 */
inline fun Tab.setTabListener(crossinline onTabSelected : (Tab, FragmentTransaction ) -> Unit = { _, _ -> },
                              crossinline onTabUnselected : (Tab, FragmentTransaction ) -> Unit = {_,_ -> },
                              crossinline onTabReselected : (Tab, FragmentTransaction ) -> Unit = {_,_ -> } ) : Tab {

    return setTabListener( object : ActionBar.TabListener {

        override fun onTabSelected( tab : Tab , ft : FragmentTransaction ) = onTabSelected( tab, ft )

        override fun onTabUnselected(tab: Tab, ft: FragmentTransaction) = onTabUnselected( tab, ft )

        override fun onTabReselected(tab: Tab, ft: FragmentTransaction) = onTabReselected( tab, ft )

    } )

}

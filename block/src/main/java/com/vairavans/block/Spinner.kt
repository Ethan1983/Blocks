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

import android.view.View
import android.widget.AdapterView
import android.widget.Spinner

inline fun Spinner.setOnItemSelectedListener(crossinline onItemSelected : (AdapterView<*>, View?, Int, Long) -> Unit = {_,_,_,_ -> },
                                             crossinline onNothingSelected: (AdapterView<*>) -> Unit = {}) {

    onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

        override fun onItemSelected(adapterView: AdapterView<*>, view: View?, pos: Int, id: Long) {
            onItemSelected( adapterView, view, pos, id )
        }

        override fun onNothingSelected(adapterView: AdapterView<*>) {
            onNothingSelected( adapterView )
        }

    }

}
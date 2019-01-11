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

import android.os.Bundle
import android.view.View
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest=Config.NONE)
class SpinnerTest {

    private lateinit var spinner : Spinner

    private class SampleActivity : AppCompatActivity() {

        lateinit var spinner : Spinner

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setTheme( R.style.Theme_AppCompat )
            spinner = Spinner( this )
            setContentView( spinner )
        }
    }

    @Before
    fun setup() {

        val activity = Robolectric.buildActivity( SampleActivity::class.java )
            .create()
            .resume()
            .get()

        spinner = activity.spinner

    }

    @Test
    fun `setOnItemSelectedListener should invoke provided lambda upon item selection events`() {

        var onItemSelectedLambdaInvoked = false
        var onNothingSelectedLambdaInvoked = false

        spinner.setOnItemSelectedListener( onItemSelected = {_,_,_,_ -> onItemSelectedLambdaInvoked = true },
            onNothingSelected = { onNothingSelectedLambdaInvoked = true } )

        spinner.onItemSelectedListener?.let {
            it.onItemSelected(spinner, View(spinner.context), 0, 1)
            it.onNothingSelected( spinner )
        }


        assert( onItemSelectedLambdaInvoked ) {
            "Spinner.setOnItemSelectedListener did not invoke the onItemSelected lambda upon item selection"
        }

        assert( onNothingSelectedLambdaInvoked ) {
            "Spinner.setOnItemSelectedListener did not invoke the onNothingSelected lambda upon item non selection"
        }
    }
}
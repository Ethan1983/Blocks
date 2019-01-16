package com.vairavans.block

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.vairavans.block.util.RecyclerViewFragment
import org.hamcrest.CoreMatchers.`is`

@RunWith(AndroidJUnit4::class)
class RecyclerViewFragmentTest {

    private lateinit var scenario: FragmentScenario<RecyclerViewFragment>

    @Before
    fun init() {
        scenario = launchFragmentInContainer<RecyclerViewFragment>()
    }

    @Test
    fun empty_view_should_be_hidden_on_valid_data_set() {

        val list = listOf( "one", "two", "three", "four", "five" )

        scenario.onFragment {
            it.listAdapter.submitList( list )
        }

        onView( withTagValue( `is`( "TextView" ) ) ).check( matches( not( isDisplayed() ) ) )
        onView( withTagValue( `is`( "RecyclerView" ) ) ).check( matches( isDisplayed() ) )
        onView( withTagValue( `is`( "ImageView" ) ) ).check( matches( isDisplayed() ) )
    }

    @Test
    fun empty_view_should_be_shown_on_empty_data_set() {

        scenario.onFragment {
            it.listAdapter.submitList( emptyList() )
        }

        onView( withTagValue( `is`( "TextView" ) ) ).check( matches( ( isDisplayed() ) ) )
        onView( withTagValue( `is`( "RecyclerView" ) ) ).check( matches( not( isDisplayed() ) ) )
        onView( withTagValue( `is`( "ImageView" ) ) ).check( matches( not( isDisplayed() ) ) )
    }
}
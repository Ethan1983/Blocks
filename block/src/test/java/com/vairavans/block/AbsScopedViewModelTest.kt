package com.vairavans.block

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController

@RunWith(RobolectricTestRunner::class)
class AbsScopedViewModelTest {

    private lateinit var viewModel : TestScopedViewModel
    private lateinit var controller : ActivityController<FragmentActivity>

    class TestScopedViewModel : AbsScopedViewModel( Dispatchers.Default ) {

        val isScopeActive : Boolean
            get() = viewModelScope.isActive

    }

    @Before
    fun init() {
        controller = Robolectric.buildActivity(FragmentActivity::class.java).setup()
        viewModel = ViewModelProviders.of(controller.get()).get(TestScopedViewModel::class.java)
    }

    @Test
    fun `viewModelScope should be active after create`() {

        assert( viewModel.isScopeActive ) {
            "viewModelScope is not active after create"
        }
    }

    @Test
    fun `viewModelScope should be inactive after destroy`() {
        controller.destroy()

        assert( !viewModel.isScopeActive ) {
            "viewModelScope is active after destroy"
        }
    }
}
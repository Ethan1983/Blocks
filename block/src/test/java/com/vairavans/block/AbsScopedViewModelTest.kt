package com.vairavans.block

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
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

        var backgroundWorkCanceled : Boolean = false

        var backgroundWorkCompleted : Boolean = false

        fun doSomeCancelableBackgroundTask() = viewModelScope.launch {

            try {
                backgroundWorkCanceled = false
                delay( 3000 )
            } catch( e : CancellationException ) {
                backgroundWorkCanceled = true
            }
        }

        fun doMultipleBackgroundTaskWithOneFailing() = with( viewModelScope ) {

            backgroundWorkCompleted = false

            launch {
                delay( 1000 )
                throw AssertionError()
            }

            launch {
                delay( 2000 )
                backgroundWorkCompleted = true
            }
        }

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

    @Test
    fun `AbsScopedViewModel should cancel coroutines on destroy`() {

        runBlocking<Unit> {
            viewModel.doSomeCancelableBackgroundTask()
            controller.destroy()
        }

        assert( viewModel.backgroundWorkCanceled ) {
            "Background work is not canceled after destroy"
        }
    }

    @Test
    fun `AbsScopedViewModel should use SupervisorJob for unidirectional flow for exceptions`() {

        runBlocking {
            viewModel.doMultipleBackgroundTaskWithOneFailing().join()
        }

        assert( viewModel.backgroundWorkCompleted ) {
            "Sibling coroutines are terminated on a failure"
        }

    }
}
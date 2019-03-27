package com.vairavans.block

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import io.mockk.mockk
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.junit.rules.TestRule
import org.junit.Rule

@RunWith(JUnit4::class)
class LifecycleOwnerTest {

    private val fragment = mockk<Fragment>( relaxed = true )

    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun `observes uses provided lifecycleowner and livedata`() {

        val liveData = MutableLiveData<Int>()

        val liveDataContainer = fragment observes liveData

        assert( liveDataContainer.owner == fragment )
        assert( liveDataContainer.liveData == liveData )
    }

    @Test
    fun `then adds observer to provided livedata`() {

        val liveData = MutableLiveData<Int>()

        assert( !liveData.hasObservers() )

        fragment observes liveData then {
        }

        assert( liveData.hasObservers() )
    }

    @Test
    fun `observes and then invokes lambda on value`() {

        val liveData = MutableLiveData<Int>()

        var handlerInvoked = false

        (fragment observes liveData).liveData.observeForever {
            handlerInvoked = true
        }

        liveData.postValue( 1 )

        assert( handlerInvoked )
    }
}

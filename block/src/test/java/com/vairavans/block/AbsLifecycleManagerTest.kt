package com.vairavans.block

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import com.vairavans.block.lifecycle.AbsLifecycleManager
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE,sdk = [Build.VERSION_CODES.P])
class AbsLifecycleManagerTest {

    private class TestActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setTheme(R.style.Theme_AppCompat)
        }
    }

    private class LifeCycleManager(lifecycle: Lifecycle) : AbsLifecycleManager(lifecycle) {

        var created = false
        var started = false
        var resumed = false
        var paused = false
        var stopped = false
        var destroyed = false
        var any = false

        override fun onLifecycleCreate() {
            created = true
        }

        override fun onLifecycleStart() {
            started = true
        }

        override fun onLifecycleResume() {
            resumed = true
        }

        override fun onLifecyclePause() {
            paused = true
        }

        override fun onLifecycleStop() {
            stopped = true
        }

        override fun onLifecycleDestroy() {
            destroyed = true
        }

        override fun onLifecycleAny() {
            any = true
        }

        fun reset() {
            created = false
            started = false
            resumed = false
            paused = false
            stopped = false
            destroyed = false
            any = false
        }
    }

    @Test
    fun `AbsLifecycleManager should invoke callbacks for respective lifecycle create`() {

        val controller = Robolectric.buildActivity( TestActivity::class.java ).create()
        val lifeCycleManager = LifeCycleManager(controller.get().lifecycle)

        assert(lifeCycleManager.created)
        assert(lifeCycleManager.any)

        lifeCycleManager.reset()
        controller.start()
        assert(lifeCycleManager.started)
        assert(lifeCycleManager.any)

        lifeCycleManager.reset()
        controller.resume()
        assert(lifeCycleManager.resumed)
        assert(lifeCycleManager.any)

        lifeCycleManager.reset()
        controller.pause()
        assert(lifeCycleManager.paused)
        assert(lifeCycleManager.any)

        lifeCycleManager.reset()
        controller.stop()
        assert(lifeCycleManager.stopped)
        assert(lifeCycleManager.any)

        lifeCycleManager.reset()
        controller.destroy()
        assert(lifeCycleManager.destroyed)
        assert(lifeCycleManager.any)
    }
}
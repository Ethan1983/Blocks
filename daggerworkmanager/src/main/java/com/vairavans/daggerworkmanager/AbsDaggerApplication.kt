package com.vairavans.daggerworkmanager

import androidx.annotation.CallSuper
import androidx.work.Configuration
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import dagger.android.support.DaggerApplication
import javax.inject.Inject

abstract class AbsDaggerApplication : DaggerApplication() {

    @Inject
    lateinit var workerFactory : WorkerFactory

    @CallSuper
    override fun onCreate() {
        super.onCreate()
        setupWorkManagerForDependencyInjection()
    }

    private fun setupWorkManagerForDependencyInjection() {
        val config = Configuration.Builder()
            .setWorkerFactory( workerFactory )
            .build()

        WorkManager.initialize( applicationContext, config )
    }

}

package com.vairavans.daggerworkmanager

import android.app.Application
import androidx.annotation.CallSuper
import androidx.work.Configuration
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import javax.inject.Inject

abstract class AbsDaggerApplication : Application() {

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
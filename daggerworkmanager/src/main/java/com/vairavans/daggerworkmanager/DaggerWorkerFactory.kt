package com.vairavans.dagger

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import java.lang.IllegalArgumentException
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class DaggerWorkerFactory @Inject constructor( private val internalWorkerFactoryMap : Map<Class<out ListenableWorker>,
        @JvmSuppressWildcards Provider<InternalWorkerFactory<out ListenableWorker>>>) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker {

        val internalWorkerFactoryProvider = internalWorkerFactoryMap[ Class.forName(workerClassName) ]
            ?: throw IllegalArgumentException( "Unsupported worker $workerClassName" )

        return internalWorkerFactoryProvider.get().create( appContext, workerParameters )
    }
}

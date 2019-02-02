package com.vairavans.daggerworkmanager

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters

interface InternalWorkerFactory< T : ListenableWorker> {
    fun create(context : Context, workerParameters: WorkerParameters) : T
}

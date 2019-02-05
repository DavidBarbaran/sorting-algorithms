package com.david.barbaran.sortingalgorithms.application

import android.app.Application
import com.david.barbaran.sortingalgorithms.module.adapterModule
import com.david.barbaran.sortingalgorithms.module.presenterModule
import com.david.barbaran.sortingalgorithms.module.taskModule
import org.koin.android.ext.android.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(applicationContext, listOf(
            presenterModule,
            adapterModule,
            taskModule
        ))
    }
}
package com.david.barbaran.sortingalgorithms

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val presenterModule = module {
    factory { MainPresenter(androidContext()) }
}
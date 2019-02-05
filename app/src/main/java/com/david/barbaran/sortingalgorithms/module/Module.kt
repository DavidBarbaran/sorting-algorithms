package com.david.barbaran.sortingalgorithms.module

import com.david.barbaran.sortingalgorithms.task.LoadTask
import com.david.barbaran.sortingalgorithms.presenter.MainPresenter
import com.david.barbaran.sortingalgorithms.adapter.UserAdapter
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val presenterModule = module {
    factory { MainPresenter(androidContext()) }
}

val adapterModule = module {
    factory { UserAdapter() }
}

val taskModule = module {
    factory { LoadTask() }
}
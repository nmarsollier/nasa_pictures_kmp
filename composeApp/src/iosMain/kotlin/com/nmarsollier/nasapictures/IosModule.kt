package com.nmarsollier.nasapictures

import com.nmarsollier.nasapictures.models.modelsModule
import org.koin.core.context.startKoin
import org.koin.dsl.module

val iosModule = module {
    single { getDatabaseBuilder() }
}

fun initKoin() = startKoin {
    modules(
        iosModule,
        modelsModule,
        //koinViewModelModule
    )
}
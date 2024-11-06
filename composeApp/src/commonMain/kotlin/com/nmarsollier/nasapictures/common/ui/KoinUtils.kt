package com.nmarsollier.nasapictures.common.ui

import androidx.compose.runtime.Composable
import com.nmarsollier.nasapictures.models.modelsModule
import com.nmarsollier.nasapictures.ui.koinViewModelModule
import org.koin.core.context.startKoin

@Composable
fun KoinPreview(composable: @Composable () -> Unit) {
    startKoin {
        modules(modelsModule, koinViewModelModule)
    }

    composable()
}

package com.nmarsollier.nasapictures

import coil3.ImageLoader
import coil3.compose.LocalPlatformContext
import coil3.disk.DiskCache
import coil3.memory.MemoryCache
import coil3.request.ImageRequest
import com.nmarsollier.nasapictures.models.modelsModule
import com.nmarsollier.nasapictures.ui.koinViewModelModule
import okio.FileSystem
import org.koin.core.context.startKoin
import org.koin.dsl.module

val iosModule = module {
    single { getDatabaseBuilder() }

    factory<ImageRequest.Builder> {
        ImageRequest.Builder(context = get())
    }

    single<ImageLoader> {
        ImageLoader.Builder(get())
            .memoryCache {
                MemoryCache.Builder().maxSizePercent(get(), 0.50).build()
            }
            .diskCache {
                DiskCache.Builder().directory(FileSystem.SYSTEM_TEMPORARY_DIRECTORY / "image_cache")
                    .maxSizeBytes(100 * 1024 * 1024)
                    .build()
            }
            .build()
    }
}

fun doInitKoin() = startKoin {
    modules(
        iosModule,
        modelsModule,
        koinViewModelModule
    )
}
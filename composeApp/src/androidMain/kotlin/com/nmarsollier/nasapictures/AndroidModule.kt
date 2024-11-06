package com.nmarsollier.nasapictures

import coil3.ImageLoader
import coil3.disk.DiskCache
import coil3.memory.MemoryCache
import coil3.request.ImageRequest
import coil3.request.allowHardware
import okio.FileSystem
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val androidModule = module {
    factory<ImageRequest.Builder> {
        ImageRequest.Builder(androidContext()).allowHardware(false)
    }

    single { getDatabaseBuilder(get()) }

    single<ImageLoader> {
        ImageLoader.Builder(androidContext())
            .memoryCache {
                MemoryCache.Builder().maxSizePercent(androidContext(), 0.50).build()
            }
            .diskCache {
                DiskCache.Builder().directory(FileSystem.SYSTEM_TEMPORARY_DIRECTORY / "image_cache")
                    .maxSizeBytes(100 * 1024 * 1024)
                    .build()
            }
            .build()
    }
}
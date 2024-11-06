package com.nmarsollier.nasapictures.models

import com.nmarsollier.nasapictures.common.ui.CoilUtils
import com.nmarsollier.nasapictures.models.api.dates.DatesApi
import com.nmarsollier.nasapictures.models.api.images.ImagesApi
import com.nmarsollier.nasapictures.models.database.config.AppDatabase
import com.nmarsollier.nasapictures.models.database.config.getRoomDatabase
import com.nmarsollier.nasapictures.models.useCases.FetchDatesUseCase
import com.nmarsollier.nasapictures.models.useCases.FetchImagesUseCase
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val modelsModule = module {
    single {
        getRoomDatabase(get())
    }

    single {
        val database: AppDatabase = get()
        database.datesDao()
    }

    single {
        val database: AppDatabase = get()
        database.imageDao()
    }

    singleOf(::FetchImagesUseCase)
    singleOf(::FetchDatesUseCase)

    single(named("nasaBaseUrl")) { "https://epic.gsfc.nasa.gov/" }

    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
        }
    }

    single { DatesApi(get(), get(named("nasaBaseUrl"))) }
    single { ImagesApi(get(), get(named("nasaBaseUrl"))) }

    singleOf(::CoilUtils)

}
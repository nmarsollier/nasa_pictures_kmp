package com.nmarsollier.nasapictures.models.api.dates

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class DatesApi(
    private val client: HttpClient,
    private val baseUrl: String,
) {
    suspend fun listDates(): List<DateValue> {
        return client.get("${baseUrl}api/enhanced/all").body()
    }
}

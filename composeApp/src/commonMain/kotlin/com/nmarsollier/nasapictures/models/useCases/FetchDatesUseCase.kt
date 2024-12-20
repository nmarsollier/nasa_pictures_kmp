package com.nmarsollier.nasapictures.models.useCases

import androidx.compose.runtime.Stable
import com.nmarsollier.nasapictures.common.utils.StateEventObject
import com.nmarsollier.nasapictures.models.api.dates.DateValue
import com.nmarsollier.nasapictures.models.api.dates.DatesApi
import com.nmarsollier.nasapictures.models.api.dates.asDateEntity
import com.nmarsollier.nasapictures.models.api.dates.asDateValue
import com.nmarsollier.nasapictures.models.database.dates.DatesEntityDao
import com.nmarsollier.nasapictures.models.database.dates.storeIn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime

@Stable
enum class FetchDatesState {
    Updated
}

class FetchDatesUseCase(
    private val datesApi: DatesApi, private val dateDao: DatesEntityDao,
) : StateEventObject<FetchDatesState>() {
    init {
        MainScope().launch(Dispatchers.IO) {
            syncDates()
        }
    }

    private suspend fun syncDates() {
        val date = lastDateFromDatabase()
        when {
            date == null -> syncRemoteDates()
            date.parsedDate <
                    Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date.minus(
                        1,
                        DateTimeUnit.DAY
                    )
            -> syncRemoteDates()
        }
    }

    private suspend fun lastDateFromDatabase(): DateValue? {
        return dateDao.findLast()?.date?.asDateValue
    }

    private suspend fun syncRemoteDates() {
        try {
            datesApi.listDates().map { it.asDateEntity }.toList().storeIn(dateDao)
            FetchDatesState.Updated.sendToEvent()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

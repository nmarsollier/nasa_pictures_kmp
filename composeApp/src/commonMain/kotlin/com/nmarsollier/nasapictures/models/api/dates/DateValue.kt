package com.nmarsollier.nasapictures.models.api.dates

import com.nmarsollier.nasapictures.common.ui.CoilUtils
import com.nmarsollier.nasapictures.models.database.dates.DatesEntity
import com.nmarsollier.nasapictures.models.extendedDate.ExtendedDateValue
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DateValue(
    @SerialName("date") val date: String,
) {
    val parsedDate: LocalDate
        get() = date.toLocalDate()
}

suspend fun DateValue.asExtendedDateValue(coilUtils: CoilUtils): ExtendedDateValue {
    return coilUtils.toExtendedData(this)
}

suspend fun ExtendedDateValue.refresh(coilUtils: CoilUtils): ExtendedDateValue {
    return this.date.asDateValue.asExtendedDateValue(coilUtils)
}

val String.asDateValue
    get() = DateValue(this)

val DateValue.asDateEntity
    get() = DatesEntity(this.date)
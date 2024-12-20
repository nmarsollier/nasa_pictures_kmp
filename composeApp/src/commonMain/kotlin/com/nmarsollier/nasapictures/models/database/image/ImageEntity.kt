package com.nmarsollier.nasapictures.models.database.image

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nmarsollier.nasapictures.models.api.images.CoordinatesValue
import com.nmarsollier.nasapictures.models.api.images.ImageValue

@Entity(tableName = "images")
data class ImageEntity(
    @PrimaryKey val identifier: String,
    @ColumnInfo(name = "date") var date: String = "",
    @ColumnInfo(name = "day") var day: String = "",
    @ColumnInfo(name = "image") var image: String = "",
    @ColumnInfo(name = "url") var url: String = "",
    @ColumnInfo(name = "lat") var lat: Double,
    @ColumnInfo(name = "lon") var lon: Double,
) {
    fun toImage(): ImageValue {
        return ImageValue(
            identifier = this.identifier,
            date = this.date,
            image = this.image,
            coordinates = CoordinatesValue(
                this.lat, this.lon
            )
        )
    }
}

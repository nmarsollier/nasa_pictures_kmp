package com.nmarsollier.nasapictures

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nmarsollier.nasapictures.models.database.config.AppDatabase
import com.nmarsollier.nasapictures.models.database.config.dbFileName

fun getDatabaseBuilder(ctx: Context): RoomDatabase.Builder<AppDatabase> {
    val appContext = ctx.applicationContext
    val dbFile = appContext.getDatabasePath(dbFileName)
    return Room.databaseBuilder<AppDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}
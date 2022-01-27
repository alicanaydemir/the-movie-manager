package com.kuka.app.tmm.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kuka.app.tmm.data.model.AppFaqCachedData
import com.kuka.app.tmm.data.source.local.dao.AppDao

@Database(
    entities = [AppFaqCachedData::class],
    version = 1
)
abstract class TmmDatabase : RoomDatabase() {

    abstract fun appDao(): AppDao
}
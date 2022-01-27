package com.kuka.app.tmm.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kuka.app.tmm.data.model.AppFaqCachedData
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorites(data: List<AppFaqCachedData>)

    @Query("SELECT * FROM table_favorites")
    fun getFavorites(): Flow<List<AppFaqCachedData>>
}
package com.local.mypi.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.local.mypi.models.WatchListItem
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchListDao {

    @Query("SELECT * FROM watchlist_table ORDER BY name ASC")
    fun getWatchList(): Flow<List<WatchListItem>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(watchItem: WatchListItem)

    @Query("DELETE FROM watchlist_table")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteWatchListItem(item: WatchListItem)
}
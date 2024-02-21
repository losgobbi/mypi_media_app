package com.local.mypi.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.local.mypi.models.WatchListItem

@Database(entities = arrayOf(WatchListItem::class), version = 1, exportSchema = false)
public abstract class WatchListDatabase : RoomDatabase() {
    abstract fun watchListDao(): WatchListDao
    companion object {
        @Volatile
        private var INSTANCE: WatchListDatabase? = null
        fun getDatabase(context: Context): WatchListDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WatchListDatabase::class.java,
                    "watchlist_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
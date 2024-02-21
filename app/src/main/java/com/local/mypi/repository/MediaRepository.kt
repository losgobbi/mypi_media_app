package com.local.mypi.repository

import androidx.annotation.WorkerThread
import com.local.mypi.dao.WatchListDao
import com.local.mypi.models.MediaFile
import com.local.mypi.models.WatchListItem
import com.local.mypi.services.EventListener
import com.local.mypi.services.SaasStore
import kotlinx.coroutines.flow.Flow

class MediaRepository(private var backend: SaasStore,
                      private val watchListDao: WatchListDao) {

    val watchList: Flow<List<WatchListItem>> = watchListDao.getWatchList()

    suspend fun getDownloadsRunning() : List<MediaFile> {
        return backend.getDownloadsRunning()
    }

    suspend fun getDownloasFinished() : List<MediaFile> {
        return backend.getDownloadsFinished()
    }

    suspend fun addMediaRequest(file: MediaFile) {
        return backend.addDownloadRequest(file)
    }

    suspend fun monitorDownloadsRunning(listener: EventListener<List<MediaFile>>) {
        return backend.monitorDownloadsRunning(listener)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun addWatchListItem(item: WatchListItem) {
        watchListDao.insert(item)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteWatchListItem(item: WatchListItem) {
        watchListDao.deleteWatchListItem(item)
    }
}
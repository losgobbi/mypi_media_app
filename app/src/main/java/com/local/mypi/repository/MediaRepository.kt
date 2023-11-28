package com.local.mypi.repository

import com.local.mypi.models.MediaFile
import com.local.mypi.services.EventListener
import com.local.mypi.services.SaasStore

class MediaRepository(var backend: SaasStore) {

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
}
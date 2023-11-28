package com.local.mypi.services

import com.local.mypi.models.MediaFile

interface EventListener<T> {
    fun onChanged(value: T, error: Int)
}

interface SaasStore {
    suspend fun getDownloadsRunning(): List<MediaFile>
    suspend fun getDownloadsFinished(): List<MediaFile>
    suspend fun addDownloadRequest(file: MediaFile)
    suspend fun monitorDownloadsRunning(listener: EventListener<List<MediaFile>>)
}
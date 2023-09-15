package com.local.mypi.repository

import android.util.Log
import com.local.mypi.models.DownloadType
import com.local.mypi.models.MediaFile
import com.local.mypi.models.MediaType

class MediaRepository(var id: String) {

    fun getMedias() : List<MediaFile> {
        return listOf(
            MediaFile(
                "Senhor dos Anéis",
                media_type = MediaType.MOVIE,
                download_type = DownloadType.MANUAL),
            MediaFile(
                "Matrix",
                media_type = MediaType.MOVIE,
                download_type = DownloadType.MANUAL),
            MediaFile(
                "Guardiões da Galáxia",
                media_type = MediaType.MOVIE,
                download_type = DownloadType.MANUAL))
    }

    fun addMediaRequest() {

    }
}
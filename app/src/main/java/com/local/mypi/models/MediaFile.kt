package com.local.mypi.models

enum class MediaType {
    MOVIE,
    SERIES,
    OTHER
}

enum class DownloadType {
    MANUAL,
    AUTO
}

data class MediaFile(val name: String,
                     val media_type: MediaType = MediaType.OTHER,
                     val download_link: String = "",
                     val download_type: DownloadType = DownloadType.AUTO)

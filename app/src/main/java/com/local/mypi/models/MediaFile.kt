package com.local.mypi.models

import android.util.Log

enum class MediaType {
    UNKNOWN,
    MOVIE,
    SERIES,
    OTHER
}

enum class DownloadType(val type: String) {
    AUTO("pyload_req"),
    MANUAL("manual");

    companion object {
        fun fromValue(type: String): DownloadType =
            when (type) {
                "manual"  -> MANUAL
                else -> AUTO
        }
    }
}

data class MediaFile(
    var name: String,
    var media_type: MediaType = MediaType.UNKNOWN,
    var download_link: String = "",
    var download_type: DownloadType = DownloadType.AUTO,
    var expected_size: String = "",
    var progress: Long = 0,
    var timeout: Long = 0,
    var statusMsg: String = "")

fun mapBackendToApp(values: MutableMap<String, Any>): MediaFile {
    val name = values["name"] as String?
    val type = DownloadType.fromValue(values["type"] as String? ?: "")
    val progress = values["progress"] as Long

    when (type) {
        DownloadType.MANUAL -> {
            val timeout = values["timeout"] as Long
            return MediaFile(
                name ?: "no_name",
                progress = progress,
                download_type = type,
                timeout = timeout
            )
        }
        DownloadType.AUTO -> {
            val pyload_links = values["pyload_links"] as ArrayList<Map<String, Any>>
            var url = ""
            var statusMsg = ""
            if (pyload_links.size > 0) {
                url = pyload_links.first()["url"] as String
                statusMsg = pyload_links.first()["statusmsg"] as String
            }
            return MediaFile(name ?: "no_name",
                            download_link = url,
                            progress = progress,
                            download_type = type,
                            statusMsg = statusMsg)
        }
    }
}

fun mapAppToBackend(file:MediaFile): MutableMap<String, Any> {
    var request = mutableMapOf<String, Any>(
        "name" to file.name,
        "links" to listOf<String>(file.download_link))
    when (file.download_type) {
        DownloadType.MANUAL -> {
            request["type"] = "manual"
            request["expected_size"] = file.expected_size
        }
        DownloadType.AUTO -> {
            request["type"] = "pyload_req"
        }
    }
    Log.d("mapAppToBackend", request.toString())
    return request
}
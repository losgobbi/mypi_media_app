package com.local.mypi

import android.app.Application
import com.local.mypi.repository.MediaRepository

class MyApplication : Application() {

    private var repoInstance = MediaRepository("watch")

    override fun onCreate() {
        super.onCreate()
    }

    fun getMediaRepository(): MediaRepository {
        return repoInstance
    }
}
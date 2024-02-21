package com.local.mypi

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp
import com.local.mypi.dao.WatchListDatabase
import com.local.mypi.repository.MediaRepository
import com.local.mypi.services.GCPFireStore
import com.local.mypi.services.SaasStore

class MyApplication : Application() {

    private val database by lazy { WatchListDatabase.getDatabase(this) }
    private val backend: SaasStore by lazy { GCPFireStore() }
    private val repoInstance: MediaRepository by lazy { MediaRepository(backend, database.watchListDao())}

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(applicationContext)
    }

    fun getMediaRepository(): MediaRepository {
        return repoInstance
    }
}
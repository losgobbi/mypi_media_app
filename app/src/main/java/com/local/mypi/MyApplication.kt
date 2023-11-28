package com.local.mypi

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp
import com.local.mypi.repository.MediaRepository
import com.local.mypi.services.GCPFireStore
import com.local.mypi.services.SaasStore

class MyApplication : Application() {

    private lateinit var backend: SaasStore
    private lateinit var repoInstance: MediaRepository

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(applicationContext)
        backend = GCPFireStore()
        repoInstance = MediaRepository(backend)
    }

    fun getMediaRepository(): MediaRepository {
        return repoInstance
    }
}
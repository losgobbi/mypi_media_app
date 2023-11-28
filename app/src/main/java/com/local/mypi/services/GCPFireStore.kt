package com.local.mypi.services

import android.util.Log
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.local.mypi.models.MediaFile
import com.local.mypi.models.mapBackendToApp
import com.local.mypi.models.mapAppToBackend
import kotlinx.coroutines.tasks.await

class GCPFireStore : SaasStore {

    private val db = Firebase.firestore
    private val TAG = "GCPFireStore"
    private val COLLECTION_DWN_RUNNING = "download_running"
    private val COLLECTION_DWN_FINISHED = "download_finished"
    private val COLLECTION_REQUESTS = "request_queue"
    private var _listener: ListenerRegistration? = null

    override suspend fun getDownloadsRunning(): List<MediaFile> {
        var medias: List<MediaFile> = listOf()
        db.collection(COLLECTION_DWN_RUNNING)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    medias += mapBackendToApp(document.data)
                }
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error getting documents.", exception)
            }.await()

        return medias
    }

    override suspend fun getDownloadsFinished(): List<MediaFile> {
        var medias: List<MediaFile> = listOf()
        db.collection(COLLECTION_DWN_FINISHED)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    medias += mapBackendToApp(document.data)
                }
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error getting documents.", exception)
            }.await()

        return medias
    }

    override suspend fun addDownloadRequest(file: MediaFile) {
        db.collection(COLLECTION_REQUESTS).add(mapAppToBackend(file)).await()
    }

    override suspend fun monitorDownloadsRunning(listener: EventListener<List<MediaFile>>) {
        if (_listener == null)
            _listener = db.collection(COLLECTION_DWN_RUNNING)
               .addSnapshotListener { value, e ->
                    if (e != null) {
                        Log.e(TAG, "Listen failed.", e)
                        listener.onChanged(listOf(), e.code.value())
                        return@addSnapshotListener
                    }
                   var medias: List<MediaFile> = listOf()
                   for (dc in value!!.documents) {
                       Log.d(TAG, "doc : ${dc.data}")
                       medias += mapBackendToApp(dc.data as MutableMap<String, Any>)
                   }
                   /*for (dc in value!!.documentChanges) {
                        when (dc.type) {
                            DocumentChange.Type.MODIFIED -> {
                                Log.d(TAG, "Modified : ${dc.document.data}")
                                medias += mapBackendToApp(dc.document.data)
                            }
                            else -> { }
                        }
                   }*/
                   listener.onChanged(medias, 0)
                }
    }
}
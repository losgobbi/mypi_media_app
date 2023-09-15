package com.local.mypi.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.local.mypi.models.MediaFile
import com.local.mypi.repository.MediaRepository

class MediaViewModel(private val mediaRepo: MediaRepository) : ViewModel() {

    init {
        Log.d("MediaViewModel", "viewmodel created!")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("MediaViewModel", "viewmodel destroyed!")
    }

    private val _medias = MutableLiveData<List<MediaFile>>().apply {
        value = mediaRepo.getMedias()
    }
    val medias: LiveData<List<MediaFile>> = _medias

    fun addMedia() {
        mediaRepo.addMediaRequest()
    }

    companion object {
        fun Factory(repository: MediaRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
            ): T {
                return MediaViewModel(repository) as T
            }
        }
    }
}
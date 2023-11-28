package com.local.mypi.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.local.mypi.models.MediaFile
import com.local.mypi.repository.MediaRepository
import com.local.mypi.services.EventListener
import kotlinx.coroutines.launch

class MediaViewModel(private val mediaRepo: MediaRepository) : ViewModel() {

    init {
        Log.d("MediaViewModel", "viewmodel created!!!")
    }

    var medias = MutableLiveData<List<MediaFile>>().apply {
        viewModelScope.launch {
            monitorDownloadsRunning()
            value = mediaRepo.getDownloasFinished()
        }
    }

    private var _monitoredDownloads = MutableLiveData<List<MediaFile>>()
    private var _filterMedia : FilterMedia = FilterMedia.DOWNLOADING

    fun changeMediaList() {

        when (_filterMedia) {
            FilterMedia.DOWNLOADING -> {
                viewModelScope.launch {
                    //medias.value = mediaRepo.getDownloadsRunning()
                    medias.value = _monitoredDownloads.value
                }
                _filterMedia = FilterMedia.FINISHED
            }
            FilterMedia.FINISHED -> {
                viewModelScope.launch {
                    medias.value = mediaRepo.getDownloasFinished()
                }
                _filterMedia = FilterMedia.DOWNLOADING
            }
        }
    }

    suspend fun addMedia(file: MediaFile) {
        mediaRepo.addMediaRequest(file)
    }

    private suspend fun monitorDownloadsRunning() {
        mediaRepo.monitorDownloadsRunning(object : EventListener<List<MediaFile>> {
            override fun onChanged(mediasChanged: List<MediaFile>, error: Int) {
                for (media in mediasChanged) {
                    Log.d("MediaViewModel", "media ${media}")
                }
                _monitoredDownloads.value = mediasChanged
            }
        })
    }

    companion object {
        enum class FilterMedia {
            DOWNLOADING,
            FINISHED,
        }

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
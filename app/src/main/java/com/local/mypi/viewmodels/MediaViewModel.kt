package com.local.mypi.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.local.mypi.models.MediaFile
import com.local.mypi.models.WatchListItem
import com.local.mypi.repository.MediaRepository
import com.local.mypi.services.EventListener
import kotlinx.coroutines.launch

class MediaViewModel(private val mediaRepo: MediaRepository) : ViewModel() {

    val watchList: LiveData<List<WatchListItem>> = mediaRepo.watchList.asLiveData()

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
    var filterMedia : FilterMedia = FilterMedia.DOWNLOADING

    fun changeMediaList() {

        when (filterMedia) {
            FilterMedia.DOWNLOADING -> {
                filterMedia = FilterMedia.FINISHED
                viewModelScope.launch {
                    //medias.value = mediaRepo.getDownloadsRunning()
                    medias.value = _monitoredDownloads.value
                }
            }
            FilterMedia.FINISHED -> {
                filterMedia = FilterMedia.DOWNLOADING
                viewModelScope.launch {
                    medias.value = mediaRepo.getDownloasFinished()
                }
            }
        }
    }

    suspend fun addMedia(file: MediaFile) {
        mediaRepo.addMediaRequest(file)
    }

    suspend fun addWatchListItem(item: WatchListItem) {
        mediaRepo.addWatchListItem(item)
    }

    suspend fun deleteWatchListItem(item: WatchListItem) {
        mediaRepo.deleteWatchListItem(item)
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
package com.mustafa.giphy.ui.activities.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mustafa.giphy.model.data_models.responses.Data
import com.mustafa.giphy.model.repository.DatabaseRepository
import com.mustafa.giphy.model.services.FilesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var databaseRepository: DatabaseRepository

    @Inject
    lateinit var filesManager: FilesManager

    private var job: Job? = null

    private val _searchQuery = MutableLiveData<String>()
    var searchQuery: LiveData<String> = _searchQuery

    private val _removeFromFavourite = MutableLiveData<Data>()
    var removeFromFavourite: LiveData<Data> = _removeFromFavourite

    private val _downloadFailed = MutableLiveData<Data>()
    var downloadFailed: LiveData<Data> = _downloadFailed

    fun setSearchQuery(query: String) {
        _searchQuery.postValue(query)
    }

    fun addToFavourite(data: Data) {
        job = viewModelScope.launch(Dispatchers.IO) {
            if (data.images?.original?.url != null && data.id != null) {
                databaseRepository.addToFavourite(data)
                filesManager.downloadFile(data)
            } else {
                _downloadFailed.postValue(data)
            }
        }
    }

    fun removeFromFavourite(data: Data, notifyObservers: Boolean) {
        if (notifyObservers) _removeFromFavourite.postValue(data)
        job = viewModelScope.launch(Dispatchers.IO) {
            databaseRepository.removeFromFavourite(data)
            filesManager.deleteFile(data)
        }
    }


    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}
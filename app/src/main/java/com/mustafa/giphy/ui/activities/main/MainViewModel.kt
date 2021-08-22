package com.mustafa.giphy.ui.activities.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mustafa.giphy.model.data_models.responses.Data
import com.mustafa.giphy.model.repository.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var databaseRepository: DatabaseRepository

    private var job: Job? = null

    private val _searchQuery = MutableLiveData<String>()
    var searchQuery: LiveData<String> = _searchQuery

    private val _removeFromFavourite = MutableLiveData<Data>()
    var removeFromFavourite: LiveData<Data> = _removeFromFavourite

    fun setSearchQuery(query: String) {
        _searchQuery.postValue(query)
    }

    fun addToFavourite(data: Data) {
        job = viewModelScope.launch(Dispatchers.IO) {
            databaseRepository.addToFavourite(data)
        }
    }

    fun removeFromFavourite(data: Data, notifyObservers: Boolean) {
        if (notifyObservers) _removeFromFavourite.postValue(data)
        job = viewModelScope.launch(Dispatchers.IO) {
            databaseRepository.removeFromFavourite(data)
        }
    }


    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}
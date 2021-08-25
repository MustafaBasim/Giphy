package com.mustafa.giphy.ui.fragments.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mustafa.giphy.model.data_models.Results
import com.mustafa.giphy.model.data_models.responses.DataResponse
import com.mustafa.giphy.model.repository.DatabaseRepository
import com.mustafa.giphy.model.repository.MainRepository
import com.mustafa.giphy.utilities.loading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var mainRepository: MainRepository

    @Inject
    lateinit var databaseRepository: DatabaseRepository

    private var job: Job? = null

    private var currentOffset: Int = 0

    private val _gifsData = MutableLiveData<Results<DataResponse>>()
    val gifsData: LiveData<Results<DataResponse>> = _gifsData

    private var searchQuery: String = ""

    fun setSearchQuery(query: String) {
        searchQuery = query
        getGifs(resetPage = true)
    }

    fun getGifs(resetPage: Boolean = false) {
        if (resetPage) currentOffset = 0
        job?.cancel()
        _gifsData.loading()
        job = viewModelScope.launch(Dispatchers.IO) {
            val gifsResponse = if (searchQuery.isBlank()) {
                mainRepository.getTrending(currentOffset)
            } else {
                mainRepository.search(searchQuery, currentOffset)
            }
            if (gifsResponse is Results.Success) {
                databaseRepository.getFavouriteGifsIds().forEach { favouriteGif ->
                    gifsResponse.data.data?.find { it.id == favouriteGif.id }?.isFavourite = true
                }
            }
            _gifsData.postValue(gifsResponse)
        }
    }

    fun setNewOffset(offset: Int) {
        currentOffset = offset
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}
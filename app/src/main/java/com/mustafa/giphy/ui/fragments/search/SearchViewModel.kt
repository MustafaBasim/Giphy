package com.mustafa.giphy.ui.fragments.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mustafa.giphy.model.data_models.Results
import com.mustafa.giphy.model.data_models.responses.DataId
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
class SearchViewModel @Inject constructor(databaseRepository: DatabaseRepository) : ViewModel() {

    @Inject
    lateinit var mainRepository: MainRepository

    // we need to read it once only in the app launch
    private var favouriteGifsIds: List<DataId>? = null

    private var job: Job? = null

    init {
        job = viewModelScope.launch(Dispatchers.IO) {
            favouriteGifsIds = databaseRepository.getFavouriteGifsIds()
        }
    }

    var currentOffset = 0 // -Constants.PAGE_LIMIT
    var totalCount = 1

    private val _gifsData = MutableLiveData<Results<DataResponse>>()
    val gifsData: LiveData<Results<DataResponse>> = _gifsData

    private var searchQuery: String = ""

    fun setSearchQuery(query: String) {
        searchQuery = query
        getGifs(resetPage = true)
    }

    fun getGifs(resetPage: Boolean = false) {
        if (resetPage) currentOffset = 0
        _gifsData.loading()
        job = viewModelScope.launch(Dispatchers.IO) {
            val gifsResponse = if (searchQuery.isBlank()) {
                mainRepository.getTrending(currentOffset)
            } else {
                mainRepository.search(searchQuery, currentOffset)
            }
            if (gifsResponse is Results.Success) {
                favouriteGifsIds?.forEach { favouriteGif ->
                    gifsResponse.data.data?.find { it.id == favouriteGif.id }?.isFavourite = true
                }
            }
            _gifsData.postValue(gifsResponse)
        }
    }

    fun isLoading() = currentOffset >= totalCount || gifsData.value is Results.Error

    fun hasLoadedAllItems() = currentOffset >= totalCount || gifsData.value is Results.Error

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}
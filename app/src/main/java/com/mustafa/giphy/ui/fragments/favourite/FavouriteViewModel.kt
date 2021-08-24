package com.mustafa.giphy.ui.fragments.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mustafa.giphy.model.database.entities.FavouriteGifsEntity
import com.mustafa.giphy.model.repository.DatabaseRepository
import com.mustafa.giphy.utilities.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(databaseRepository: DatabaseRepository) : ViewModel() {

//    @Inject
//    lateinit var databaseRepository: DatabaseRepository

    private var job: Job? = null

    var currentOffset = -Constants.PAGE_LIMIT
    var totalCount = 1

    var favouriteGifsData: LiveData<List<FavouriteGifsEntity>> = databaseRepository.getFavouriteGifs(20, 0)

    private var searchQuery: String = ""

    fun setSearchQuery(query: String) {
        searchQuery = query
        currentOffset = 0
        getGifs()
    }

    fun getGifs() {
//        _favouriteGifsData.loading()
        job = viewModelScope.launch(Dispatchers.IO) {

//            Log.d("ERROR", " test = ${test} ")
//            _favouriteGifsData.postValue(databaseRepository.getFavouriteGifs(20, 0))

//            _favouriteGifsData.postValue()
//            val x: List<FavouriteGifsEntity>? = databaseRepository.getFavouriteGifs(20, 0).value
//            _favouriteGifsData.postValue(x)
        }
    }

//    fun isLoading() = currentOffset >= totalCount || favouriteGifsData.value is Results.Error

//    fun hasLoadedAllItems() = currentOffset >= totalCount || favouriteGifsData.value is Results.Error

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}
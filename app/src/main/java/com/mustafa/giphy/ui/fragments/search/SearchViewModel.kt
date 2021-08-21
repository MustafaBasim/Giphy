package com.mustafa.giphy.ui.fragments.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mustafa.giphy.model.data_models.Results
import com.mustafa.giphy.model.data_models.responses.DataResponse
import com.mustafa.giphy.model.repository.MainRepository
import com.mustafa.giphy.ui.base.BaseViewModel
import com.mustafa.giphy.utilities.loading
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel : BaseViewModel() {

    private val mainRepository by lazy { MainRepository() }


    private val _gifsData = MutableLiveData<Results<DataResponse>>()
    val gifsData: LiveData<Results<DataResponse>> = _gifsData

    fun getTrending(offset: Int) {
        _gifsData.loading()
        viewModelScope.launch(Dispatchers.IO) {
            _gifsData.postValue(mainRepository.getTrending(offset))
        }
    }

    fun search(query: String, offset: Int) {
        _gifsData.loading()
        viewModelScope.launch(Dispatchers.IO) {
            _gifsData.postValue(mainRepository.search(query, offset))
        }
    }


}
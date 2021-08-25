package com.mustafa.giphy.ui.fragments.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mustafa.giphy.model.database.entities.FavouriteGifsEntity
import com.mustafa.giphy.model.repository.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(databaseRepository: DatabaseRepository) : ViewModel() {

    var favouriteGifsData: LiveData<List<FavouriteGifsEntity>> = databaseRepository.getFavouriteGifs()

}
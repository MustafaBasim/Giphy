package com.mustafa.giphy.model.repository

import com.mustafa.giphy.model.data_models.Results
import com.mustafa.giphy.model.data_models.responses.DataResponse
import com.mustafa.giphy.model.database.daos.FavouriteGifsDao
import com.mustafa.giphy.model.networking.MainAPIInterface
import javax.inject.Inject

/**
 * Created by: Mustafa Basim
 * E-mail: 96.mustafa.basim@gmail.com
 * Project name: FreshWorks Giphy
 * Package: com.mustafa.giphy.model.repository
 * Date: 8/21/2021
 */


class MainRepository @Inject constructor() : BaseRepository() {

    @Inject
    lateinit var mainAPI: MainAPIInterface

    suspend fun getTrending(offset: Int): Results<DataResponse> = call { mainAPI.trending(offset) }

    suspend fun search(query: String, offset: Int): Results<DataResponse> = call { mainAPI.search(query, offset) }

}


package com.mustafa.giphy.model.repository

import com.mustafa.giphy.model.data_models.Results
import com.mustafa.giphy.model.data_models.responses.DataResponse

/**
 * Created by: Mustafa Basim
 * E-mail: 96.mustafa.basim@gmail.com
 * Project name: FreshWorks Giphy
 * Package: com.mustafa.giphy.model.repository
 * Date: 8/21/2021
 */

class MainRepository : BaseRepository() {

    suspend fun getTrending(offset: Int): Results<DataResponse> = call { api.trending(offset) }

    suspend fun search(query: String, offset: Int): Results<DataResponse> = call { api.search(query, offset) }

}


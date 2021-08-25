package com.mustafa.giphy.model.networking

import com.mustafa.giphy.model.data_models.responses.DataResponse
import com.mustafa.giphy.utilities.Constants
import com.mustafa.giphy.utilities.Urls
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url


/**
 * Created by: Mustafa Basim
 * E-mail: 96.mustafa.basim@gmail.com
 * Project name: FreshWorks Giphy
 * Package: com.mustafa.giphy.model.networking
 * Date: 8/21/2021
 */

interface MainAPIInterface {

    @GET(Urls.TRENDING)
    suspend fun trending(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int = Constants.PAGE_LIMIT,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Response<DataResponse>

    @GET(Urls.SEARCH)
    suspend fun search(
        @Query("q") query: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int = Constants.PAGE_LIMIT,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Response<DataResponse>

}
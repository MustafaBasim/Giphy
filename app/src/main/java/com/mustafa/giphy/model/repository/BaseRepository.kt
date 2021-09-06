package com.mustafa.giphy.model.repository

import com.google.gson.Gson
import com.mustafa.giphy.model.data_models.Results
import com.mustafa.giphy.model.data_models.responses.DataResponse
import com.mustafa.giphy.utilities.Constants
import okhttp3.ResponseBody
import retrofit2.Response

/**
 * Created by: Mustafa Basim
 * E-mail: 96.mustafa.basim@gmail.com
 * Project name: Giphy
 * Package: com.mustafa.giphy.model.repository
 * Date: 8/21/2021
 */

open class BaseRepository {

    protected suspend fun <T> call(call: suspend () -> Response<T>): Results<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    return Results.Success(body)
                }
            }
            return error(response.errorBody())
        } catch (e: Exception) {
            return error(null)
        }
    }


    private fun <T> error(errorBodyJson: ResponseBody?): Results<T> {
        return if (errorBodyJson != null) {
            return try {
                val errorJson = errorBodyJson.string()
                val errorMessage: DataResponse = Gson().fromJson(errorJson, DataResponse::class.java)
                Results.Error(errorMessage)
            } catch (e: Exception) {
                Results.Error(DataResponse(message = Constants.API_NO_INTERNET))
            }
        } else {
            Results.Error(DataResponse(message = Constants.API_NO_INTERNET))
        }
    }
}
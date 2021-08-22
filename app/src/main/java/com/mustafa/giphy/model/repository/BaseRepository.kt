package com.mustafa.giphy.model.repository

import com.mustafa.giphy.model.data_models.Results
import com.mustafa.giphy.model.data_models.responses.DataResponse
import com.mustafa.giphy.utilities.Constants
import okhttp3.ResponseBody
import retrofit2.Response

/**
 * Created by: Mustafa Basim
 * E-mail: 96.mustafa.basim@gmail.com
 * Project name: FreshWorks Giphy
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
            return error(response.errorBody(), response.code())
        } catch (e: Exception) {
            return error(null)
        }
    }


    private fun <T> error(errorBodyJson: ResponseBody?, code: Int = 404): Results<T> {
        return if (errorBodyJson != null) {
            return try {
//                val gson = Gson()

//                val errorJson = errorBodyJson.string()
//                val type = object : TypeToken<ErrorMessage>() {}.type
//                val errorMessage: ErrorMessage = gson.fromJson(errorJson, type)
//                errorMessage.code = code

                Results.Error(DataResponse(message = Constants.API_NO_INTERNET))

            } catch (e: Exception) {
                Results.Error(DataResponse(message = Constants.API_NO_INTERNET))
            }
        } else {
            Results.Error(DataResponse(message = Constants.API_NO_INTERNET))
        }
    }

//    400 validation error ==> array
//    401 unauthenticated  , credentials error ==> message
//    403 disabled account ==> message
//    404 not found ==> message
}
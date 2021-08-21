package com.mustafa.giphy.model.data_models

import com.mustafa.giphy.model.data_models.responses.DataResponse


sealed class Results<out T> {
    data class Success<out T>(val data: T) : Results<T>()
    object Loading : Results<Nothing>()
    data class Error(val error: DataResponse) : Results<Nothing>()
}
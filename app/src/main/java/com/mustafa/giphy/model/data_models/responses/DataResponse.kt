package com.mustafa.giphy.model.data_models.responses


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class DataResponse(
    @SerializedName("data")
    val data: ArrayList<Data>? = null,
    @SerializedName("pagination")
    val pagination: Pagination? = null,
    @SerializedName("meta")
    val meta: Meta? = null,

    @SerializedName("message") // in case there was an error with the API_KEY
    val message: String? = null
)
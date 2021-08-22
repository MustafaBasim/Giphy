package com.mustafa.giphy.model.data_models.responses


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class DataId(
    @SerializedName("id")
    val id: String
)
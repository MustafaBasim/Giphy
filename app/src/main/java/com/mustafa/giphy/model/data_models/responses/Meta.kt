package com.mustafa.giphy.model.data_models.responses


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Meta(
    @SerializedName("status")
    val status: Int? = null,
    @SerializedName("msg")
    val msg: String? = null,
    @SerializedName("response_id")
    val responseId: String? = null
)
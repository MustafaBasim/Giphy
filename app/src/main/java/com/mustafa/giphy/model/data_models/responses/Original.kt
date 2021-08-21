package com.mustafa.giphy.model.data_models.responses


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Original(
    @SerializedName("height")
    val height: String? = null,
    @SerializedName("width")
    val width: String? = null,
    @SerializedName("size")
    val size: String? = null,
    @SerializedName("url")
    val url: String? = null,
    @SerializedName("hash")
    val hash: String? = null
)
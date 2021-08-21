package com.mustafa.giphy.model.data_models.responses


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Pagination(
    @SerializedName("total_count")
    val totalCount: Int? = null,
    @SerializedName("count")
    val count: Int? = null,
    @SerializedName("offset")
    val offset: Int? = null
)
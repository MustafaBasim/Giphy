package com.mustafa.giphy.model.data_models.responses


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Data(
    @SerializedName("type")
    var type: String? = null,
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("username")
    val username: String? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("images")
    val images: Images? = null,
    @SerializedName("user")
    val user: User? = null,

    var downloadId: Long? = null,
    var isAvailableOffline: Boolean = false,
    var isFavourite: Boolean = false
)
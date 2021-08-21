package com.mustafa.giphy.model.data_models.responses


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Images(
    @SerializedName("original")
    val original: Original? = null,
    @SerializedName("preview_gif")
    val previewGif: PreviewGif? = null
)
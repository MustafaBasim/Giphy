package com.mustafa.giphy.model.data_models.responses


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class User(
    @SerializedName("avatar_url")
    val avatarUrl: String? = null,
    @SerializedName("banner_image")
    val bannerImage: String? = null,
    @SerializedName("banner_url")
    val bannerUrl: String? = null,
    @SerializedName("profile_url")
    val profileUrl: String? = null,
    @SerializedName("username")
    val username: String? = null,
    @SerializedName("display_name")
    val displayName: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("instagram_url")
    val instagramUrl: String? = null,
    @SerializedName("website_url")
    val websiteUrl: String? = null,
    @SerializedName("is_verified")
    val isVerified: Boolean? = null
)
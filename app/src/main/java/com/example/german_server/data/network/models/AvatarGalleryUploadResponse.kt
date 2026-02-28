package com.example.german_server.data.network.models

import com.google.gson.annotations.SerializedName

data class AvatarGalleryUploadResponse(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("updated")
    val updated: Boolean,

    @SerializedName("message")
    val message: String,

    @SerializedName("active_gallery_avatar_url")
    val activeGalleryAvatarUrl: String?,          // BaseUser.avatarName

    @SerializedName("avatar_last_changed")
    val avatarLastChanged: Long,      // BaseUser.avatarLastChanged ?: 0L

    @SerializedName("user")
    val user: UserProfile
)
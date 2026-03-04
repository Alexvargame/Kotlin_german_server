package com.example.german_server.data.network.models

import com.google.gson.annotations.SerializedName

data class AvatarServerUploadResponse(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("updated")
    val updated: Boolean,

    @SerializedName("message")
    val message: String,

    @SerializedName("user")
    val user: UserProfile
)
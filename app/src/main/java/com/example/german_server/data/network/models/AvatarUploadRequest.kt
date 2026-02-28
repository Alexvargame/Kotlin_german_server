package com.example.german_server.data.network.models


import com.google.gson.annotations.SerializedName

data class AvatarUploadRequest(
    @SerializedName("uid")
    val serverUid: String?,           // BaseUser.serverUid

    @SerializedName("avatar_name")
    val avatarName: String?,          // BaseUser.avatarName

    @SerializedName("avatar_last_changed")
    val avatarLastChanged: Long       // BaseUser.avatarLastChanged ?: 0L
)

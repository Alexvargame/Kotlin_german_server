package com.example.german_server.data.network.models

import com.google.gson.annotations.SerializedName


data class SenderUser(
    @SerializedName("id") val id:Long,
    @SerializedName("uid") val serverUid: String,
    @SerializedName("username") val username: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("avatar_small_url") val avatarSmallUrl: String?,
    @SerializedName("avatar_name") val avatarName: String?,
    @SerializedName("is_staff") val is_admin: Boolean,
)
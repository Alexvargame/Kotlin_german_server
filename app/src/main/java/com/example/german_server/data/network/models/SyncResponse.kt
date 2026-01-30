package com.example.german_server.data.network.models

import com.google.gson.annotations.SerializedName

data class SyncResponse(
    @SerializedName("uid") val uid: String,
    @SerializedName("email") val email: String,
    @SerializedName("is_verified") val isVerified: Boolean,
    @SerializedName("login_token") val loginToken: String
)
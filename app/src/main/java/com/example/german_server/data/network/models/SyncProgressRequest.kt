package com.example.german_server.data.network.models


import com.google.gson.annotations.SerializedName

data class SyncProgressRequest(
    @SerializedName("uid")
    val serverUid: String?,           // BaseUser.serverUid

    @SerializedName("score")
    val score: Int?,                  // BaseUser.score

    @SerializedName("streak_days")
    val shockmodLong: Int,            // BaseUser.shockmodLong

    @SerializedName("last_session_date")
    val shockmodNow: Long?            // BaseUser.shockmodNow
)
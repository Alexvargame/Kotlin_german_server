package com.example.german_server.data.network.models

import com.google.gson.annotations.SerializedName

data class SyncProgressResponse(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("updated")
    val updated: Boolean,

    @SerializedName("message")
    val message: String,

    @SerializedName("score")
    val score: Int,                   // кладём в BaseUser.score

    @SerializedName("lifes")
    val lifes: Int,

    @SerializedName("streak_days")
    val streakDays: Int,              // кладём в BaseUser.shockmodLong

    @SerializedName("last_session_date")
    val lastSessionDate: Long,        // кладём в BaseUser.shockmodNow

    @SerializedName("user")
    val user: UserProfile
)
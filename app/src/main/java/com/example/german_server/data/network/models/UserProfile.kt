package com.example.german_server.data.network.models

import com.google.gson.annotations.SerializedName

data class UserProfile(
    @SerializedName("uid")
    val uid: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("username")
    val username: String?,

    @SerializedName("is_verified")
    val isVerified: Boolean,

    @SerializedName("streak_days")
    val shockmodLong: Int,            // BaseUser.shockmodLong

    @SerializedName("last_session_date")
    val shockmodNow: Long?



)
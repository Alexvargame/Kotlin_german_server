package com.example.german_server.data.network.models

import com.google.gson.annotations.SerializedName

data class ProfileResponse(

    @SerializedName("uid")
    val uid: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("username")
    val username: String?,

    @SerializedName("score")
    val score: Int,

    @SerializedName("streak_days")
    val streakDays: Int,

    @SerializedName("phone")
    val phone: String?,

    @SerializedName("is_verified")
    val isVerified: Boolean,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("last_session_date")
    val shockmodNow: Long?,

    @SerializedName("avatar_name")
    val avatarName: String?,

    @SerializedName("active_gallery_avatar_url")
    val activeGalleryAvatarUrl: String?,

    @SerializedName("avatar_last_changed")
    val avatarLastChanged: Long?,
    )
package com.example.german_server.data.network.models

import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    @SerializedName("uid")
    val serverUid: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("is_verified")
    val isVerified: Boolean, // Это ключевое поле для синхронизации!
    // Добавьте другие поля при необходимости, но это минимальный набор
)
package com.example.german_server.data.network.models
import com.google.gson.annotations.SerializedName


data class ResendVerificationRequest(
    @SerializedName("email")
    val email: String
)
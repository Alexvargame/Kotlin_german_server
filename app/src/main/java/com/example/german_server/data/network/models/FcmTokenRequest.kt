package com.example.german_server.data.network.models

data class FcmTokenRequest(
    val fcm_token: String,
    val device_name: String
)
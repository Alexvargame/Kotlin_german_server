package com.example.german_server.data.network.models

data class RegisterResponse(
    val uid: String,
    val login_token: String,
    val email: String
)
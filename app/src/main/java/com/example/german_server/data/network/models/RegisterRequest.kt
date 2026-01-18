package com.example.german_server.data.network.models

data class RegisterRequest(
    val email: String,
    val username: String,
    val password: String
)

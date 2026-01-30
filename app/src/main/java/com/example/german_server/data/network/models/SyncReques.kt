package com.example.german_server.data.network.models
import com.google.gson.annotations.SerializedName

data class SyncRequest(@SerializedName("email") val email: String)
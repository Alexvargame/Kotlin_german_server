package com.example.german_server.data.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "user_avatars",
    indices = [Index(value = ["userId"])]
)
data class UserAvatar(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: Long,
    val path: String,
    val isActive: Boolean = false
)
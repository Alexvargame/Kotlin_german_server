package com.example.german_server.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sentences")
data class SentenceEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val sentence: String,
    val translation: String? = null,
    val description: String? = null,
)
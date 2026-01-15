package com.example.german_server.data.entities


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words_article")
data class Article(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val description: String,
    val forms: String? = null // JSON храним как String
)

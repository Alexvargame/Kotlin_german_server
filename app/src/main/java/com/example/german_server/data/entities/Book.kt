package com.example.german_server.data.entities



import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words_book")
data class Book(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val description: String
)

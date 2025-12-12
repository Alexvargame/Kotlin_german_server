package com.example.german.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.german.data.entities.Book
import com.example.german.data.entities.WordType

@Dao
interface WordTypeDao {
    @Insert
    suspend fun insert(wordType: WordType): Long

    @Insert
    suspend fun insertAll(wordTypes: List<WordType>)

    @Query("SELECT * FROM words_wordtype")
    suspend fun getAll(): List<WordType>

    @Query("SELECT * FROM words_wordtype WHERE id = :id")
    suspend fun getById(id: Long): WordType?

    @Update
    suspend fun update(wordType: WordType)  // ← вот этот метод

    @Delete
    suspend fun delete(wordType: WordType)
}


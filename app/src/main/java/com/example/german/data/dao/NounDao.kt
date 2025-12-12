package com.example.german.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.german.data.entities.Book
import com.example.german.data.entities.Noun

@Dao
interface NounDao {
    @Insert
    suspend fun insert(noun: Noun)

    @Insert
    suspend fun insertAll(nouns: List<Noun>)

    @Query("SELECT * FROM words_noun")
    suspend fun getAll(): List<Noun>

    @Query("SELECT * FROM words_noun WHERE word_ptr_id = :id")
    suspend fun getById(id: Long): Noun?

    @Update
    suspend fun update(noun: Noun)  // ← вот этот метод

    @Delete
    suspend fun delete(noun: Noun)
}



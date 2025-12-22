package com.example.german.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.german.data.entities.Book
import com.example.german.data.entities.Pronoun

@Dao
interface PronounDao {
    @Insert
    suspend fun insert(pronoun: Pronoun)

    @Insert
    suspend fun insertAll(pronouns: List<Pronoun>)
    @Query("SELECT * FROM words_pronoun ORDER BY RANDOM() LIMIT :count")
    fun getRandomPronouns(count: Int): List<Pronoun>
    @Query("SELECT * FROM words_pronoun")
    suspend fun getAll(): List<Pronoun>

    @Query("SELECT * FROM words_pronoun WHERE word_ptr_id = :id")
    suspend fun getById(id: Long): Pronoun?

    @Update
    suspend fun update(pronoun: Pronoun)  // ← вот этот метод

    @Delete
    suspend fun delete(pronoun: Pronoun)
}



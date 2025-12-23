package com.example.german.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

import com.example.german.data.entities.Pronoun


@Dao
interface PronounDao {
    @Insert
    suspend fun insert(pronoun: Pronoun):Long

    @Insert
    suspend fun insertAll(pronouns: List<Pronoun>)

    @Query("SELECT * FROM words_pronoun")
    suspend fun getAll(): List<Pronoun>

    @Query("SELECT * FROM words_pronoun WHERE word_ptr_id = :id")
    suspend fun getById(id: Long): Pronoun?
    @Query("SELECT * FROM words_pronoun ORDER BY RANDOM() LIMIT :count")
    fun getRandomPronouns(count: Int): List<Pronoun>
    @Update
    suspend fun update(pronoun: Pronoun)  // ← вот этот метод

    @Delete
    suspend fun delete(pronoun: Pronoun)
}



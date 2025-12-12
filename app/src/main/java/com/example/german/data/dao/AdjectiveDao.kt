package com.example.german.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.german.data.entities.Adjective
import com.example.german.data.entities.Book

@Dao
interface AdjectiveDao {
    @Insert
    suspend fun insert(adjective: Adjective)

    @Insert
    suspend fun insertAll(adjectives: List<Adjective>)

    @Query("SELECT * FROM words_adjective")
    suspend fun getAll(): List<Adjective>

    @Query("SELECT * FROM words_adjective WHERE word_ptr_id = :id")
    suspend fun getById(id: Long): Adjective?

    @Update
    suspend fun update(adjective: Adjective)  // ← вот этот метод

    @Delete
    suspend fun delete(adjective: Adjective)

}



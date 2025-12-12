package com.example.german.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.german.data.entities.Book
import com.example.german.data.entities.OtherWord

@Dao
interface OtherWordDao {
    @Insert
    suspend fun insert(otherWord: OtherWord)

    @Insert
    suspend fun insertAll(otherWords: List<OtherWord>)

    @Query("SELECT * FROM words_otherwords")
    suspend fun getAll(): List<OtherWord>

    @Query("SELECT * FROM words_otherwords WHERE word_ptr_id = :id")
    suspend fun getById(id: Long): OtherWord?

    @Update
    suspend fun update(otherWord: OtherWord)  // ← вот этот метод

    @Delete
    suspend fun delete(otherWord: OtherWord)
}


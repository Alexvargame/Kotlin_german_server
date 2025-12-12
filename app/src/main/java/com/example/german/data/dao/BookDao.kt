package com.example.german.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.example.german.data.entities.Book

@Dao
interface BookDao {
    @Insert
    suspend fun insert(book: Book): Long

    @Query("SELECT * FROM words_book")
    suspend fun getAll(): List<Book>

    @Update
    suspend fun update(book: Book)  // ← вот этот метод

    @Delete
    suspend fun delete(book: Book)
}

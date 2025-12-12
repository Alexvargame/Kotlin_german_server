package com.example.german.data.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.example.german.data.entities.Lection

@Dao
interface LectionDao {
    @Insert
    suspend fun insert(lection: Lection): Long

    @Query("SELECT * FROM words_lection")
    suspend fun getAll(): List<Lection>

    @Update
    suspend fun update(lection: Lection)  // ← вот этот метод

    @Delete
    suspend fun delete(lection: Lection)
}

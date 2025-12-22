package com.example.german.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.german.data.entities.Word

@Dao
interface WordDao {
    @Insert
    suspend fun insert(word: Word):Long

    //@Insert
    //suspend fun insertAll(words: List<Word>)

    @Query("""
    SELECT * FROM words_word
    WHERE lection_id = :lectionId
    ORDER BY RANDOM()
    LIMIT :limit
""")
    suspend fun getRandomWords(
        lectionId: Long,
        limit: Int,
    ): List<Word>

    @Query("SELECT * FROM words_word WHERE id = :id")
    suspend fun getById(id: Long): Word?

    @Update
    suspend fun update(word: Word)  // ← вот этот метод

    @Delete
    suspend fun delete(word: Word)
}


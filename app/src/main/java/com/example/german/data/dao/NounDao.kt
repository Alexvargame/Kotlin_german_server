package com.example.german.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.ColumnInfo
import androidx.room.Embedded

import com.example.german.data.entities.Noun

data class NounWithWord(
    @Embedded val noun: Noun,
    @ColumnInfo(name = "lection_id") val lectionId: Long,
    @ColumnInfo(name = "word_type_id") val wordTypeId: Long
)


@Dao
interface NounDao {
    @Insert
    suspend fun insert(noun: Noun): Long

    @Insert
    suspend fun insertAll(nouns: List<Noun>)

    @Query("SELECT * FROM words_noun")
    suspend fun getAll(): List<Noun>
    @Query("SELECT * FROM words_noun ORDER BY RANDOM() LIMIT :count")
    fun getRandomNouns(count: Int): List<Noun>
    @Query("""
        SELECT n.*, w.lection_id, w.word_type_id
        FROM words_noun n
        INNER JOIN words_word w ON n.word_ptr_id = w.id
    """)
    suspend fun getAllWithWord(): List<NounWithWord>
    @Query("SELECT * FROM words_noun WHERE word_ptr_id = :id")
    suspend fun getById(id: Long): Noun?

    @Update
    suspend fun update(noun: Noun)  // ← вот этот метод

    @Delete
    suspend fun delete(noun: Noun)

    @Query("DELETE FROM words_noun")
    suspend fun deleteAll()


}


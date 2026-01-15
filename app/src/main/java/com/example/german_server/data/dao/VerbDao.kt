package com.example.german_server.data.dao

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.german_server.data.entities.Verb

data class VerbWithWord(
    @Embedded val noun: Verb,
    @ColumnInfo(name = "lection_id") val lectionId: Long,
    @ColumnInfo(name = "word_type_id") val wordTypeId: Long
)


@Dao
interface VerbDao {
    @Insert
    suspend fun insert(verb: Verb): Long

    @Insert
    suspend fun insertAll(verbs: List<Verb>)

    @Query("SELECT * FROM words_verb")
    suspend fun getAll(): List<Verb>

    @Query("SELECT * FROM words_verb ORDER BY RANDOM() LIMIT :count")
    fun getRandomVerbs(count: Int): List<Verb>

    @Query("SELECT * FROM words_verb WHERE word_ptr_id = :id")
    suspend fun getById(id: Long): Verb?

    @Update
    suspend fun update(verb: Verb)  // ← вот этот метод

    @Delete
    suspend fun delete(verb: Verb)
}

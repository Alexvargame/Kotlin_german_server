package com.example.german_server.data.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.german_server.data.entities.SentenceEntity
import com.example.german_server.data.entities.Verb

@Dao
interface SentenceDao {

    @Query("SELECT * FROM sentences ORDER BY RANDOM() LIMIT :count")
    suspend fun getRandomSentences(count: Int): List<SentenceEntity>

    @Query("SELECT * FROM sentences ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomSentence(): SentenceEntity?

    @Query("SELECT COUNT(*) FROM sentences")
    suspend fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(sentences: List<SentenceEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sentence: SentenceEntity)

    @Query("DELETE FROM sentences")
    suspend fun deleteAll()

}
package com.example.german.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.german.data.entities.CallbackSiteMessage


@Dao
interface CallbackSiteMessageDao {

    @Insert
    suspend fun insert(message: CallbackSiteMessage)

    @Insert
    suspend fun insertAll(messages: List<CallbackSiteMessage>)

    @Query("SELECT * FROM callback_sitemessage")
    suspend fun getAll(): List<CallbackSiteMessage>

    @Query("SELECT * FROM callback_sitemessage WHERE id = :id")
    suspend fun getById(id: Long): CallbackSiteMessage?

    @Query("SELECT * FROM callback_sitemessage WHERE is_answered = 0")
    suspend fun getUnanswered(): List<CallbackSiteMessage>

    @Update
    suspend fun update(message: CallbackSiteMessage)

    @Delete
    suspend fun delete(message: CallbackSiteMessage)
}


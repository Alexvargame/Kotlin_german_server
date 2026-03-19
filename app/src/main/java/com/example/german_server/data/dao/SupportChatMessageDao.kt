package com.example.german_server.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

import kotlinx.coroutines.flow.Flow
import com.example.german_server.data.entities.SupportChatMessage


@Dao
interface SupportChatMessageDao {

    // вставка одного сообщения
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: SupportChatMessage)

    // вставка списка сообщений
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessages(messages: List<SupportChatMessage>)

    // все сообщения чата
    @Query("""
        SELECT * FROM support_chat_messages
        ORDER BY created_at ASC
    """)
    fun getChatMessages(): Flow<List<SupportChatMessage>>

    // последний server_id для sync
    @Query("""
        SELECT MAX(server_id)
        FROM support_chat_messages
    """)
    suspend fun getLastServerId(): Long?

    // сообщения которые ещё не отправлены
    @Query("""
        SELECT * FROM support_chat_messages
        WHERE sync_status IN ('pending','error')
    """)
    suspend fun getPendingMessages(): List<SupportChatMessage>

    // обновить сообщение
    @Update
    suspend fun updateMessage(message: SupportChatMessage)

    // отметить прочитанными
    @Query("""
        UPDATE support_chat_messages
        SET is_read = 1
        WHERE server_id IN (:ids)
    """)
    suspend fun markMessagesRead(ids: List<Long>)

    @Delete
    suspend fun delete(message: SupportChatMessage)

    @Query("UPDATE support_chat_messages SET sync_status = :status WHERE id = :localId")
    suspend fun updateSyncStatus(localId: Long, status: String)

}


package com.example.german_server.data.repository.support_chat

import com.example.german_server.data.dao.SupportChatMessageDao

import com.example.german_server.data.dao.BaseUserDao
import com.example.german_server.data.network.ApiService
import com.example.german_server.data.network.models.SendSupportChatMessageRequest
import com.example.german_server.data.network.models.MarkReadSupportMessageRequest
import com.example.german_server.data.network.models.DeleteMessageRequest
import com.example.german_server.data.network.models.SupportChatMessageResponse
import com.example.german_server.data.network.models.SupportChatMessageResponseDTO
import com.example.german_server.data.entities.SupportChatMessage
import com.example.german_server.data.entities.SyncStatus
import android.util.Log
import kotlinx.coroutines.flow.Flow
import java.time.Instant


class SupportChatMessageRepository(private val supportChatMessageDao: SupportChatMessageDao,
                                 private val baseUserDao: BaseUserDao,
                                 private val apiService: ApiService) {

    val messages: Flow<List<SupportChatMessage>> = supportChatMessageDao.getChatMessages()

    var isSending = false



    fun isoToTimestamp(isoDate: String): Long {
        return Instant.parse(isoDate).toEpochMilli()
    }

    // Отправка локальных сообщений
    suspend fun sendPendingMessages(token: String?) {

        try {
            if (isSending) return
            isSending = true
            val pending = supportChatMessageDao.getPendingMessages()

            pending.forEach { message ->

                try {
                    Log.d("SupportChatRepo", "Отправка сообщения id=${message.id} текст='${message.text}'")
                    val response = apiService.sendMessage(
                        SendSupportChatMessageRequest(
                            receiverUid = message.receiverUid ?: return@forEach,
                            text = message.text,
                            replyToId = message.reply_to_id
                        ),
                        "Token $token"
                    )
                    Log.d("SupportChatRepo", "responsne=${response}'")
                    if (response.isSuccessful) {
                        response.body()?.let { dto ->
                            // Преобразование DTO в модель SupportChatMessage
                            val updatedMessage = SupportChatMessage(
                                id = message.id,
                                server_id = dto.serverId,
                                sender_id = dto.sender.id,          // id отправителя с сервера
                                receiver_id = dto.receiver.id,      // id получателя с сервера
                                senderUid = dto.sender.uid,         // uuid отправителя
                                receiverUid = dto.receiver.uid,     // uuid получателя
                                text = dto.text,
                                reply_to_id = message.reply_to_id,  // reply_to_id не возвращается, берём из запроса
                                created_at = isoToTimestamp(dto.createdAt),
                                is_read = dto.isRead,
                                sync_status = SyncStatus.SENT
                            )
                            Log.d("SupportChatRepo", "Сообщение отправлено успешно id=${dto.serverId}")
                            supportChatMessageDao.updateMessage(updatedMessage)
                        }
                    } else {
                        Log.d("SupportChatRepo", "Ошибка отправки сообщения id=${message.id} код=${response.code()}")
                        supportChatMessageDao.updateMessage(message.copy(sync_status = SyncStatus.ERROR))
                    }

                } catch (e: Exception) {
                    Log.d("SupportChatRepo", "Ошибка отправки сообщения id=${message.id} ${e.message}")
                    supportChatMessageDao.updateMessage(message.copy(sync_status = SyncStatus.ERROR))
                }
            }
        } finally {
            isSending = false
        }
    }

    // Получение новых сообщений с сервера
    suspend fun syncNewMessages(token: String?) {
        val lastId = supportChatMessageDao.getLastServerId() ?: 0L
        try {
            Log.d("SupportChatRepo", "Синхронизация сообщений с lastId=$lastId | $token")

            val response = apiService.syncMessages(lastId, "Token $token")
            Log.d("SupportChatRepo", "Response =$response ${response.isSuccessful}")
            if (response.isSuccessful) {
                val messages = response.body()?.map { body ->
                    Log.d("SupportChatRepo_DEBUG_BODY", "body = $body")   // <-- распечатываем весь объект
                    SupportChatMessage(
                        id = 0L, // локальный PK
                        server_id = body.serverId,
                        sender_id = body.senderId,
                        receiver_id = body.receiverId,
                        senderUid = body.senderUid,       // серверный UUID
                        receiverUid = body.receiverUid,
                        text = body.text,
                        reply_to_id = body.replyToId,
                        created_at = isoToTimestamp(body.createdAt),
                        is_read = body.isRead,
                        sync_status = SyncStatus.SENT
                    )
                } ?: emptyList()
                Log.d("SupportChatRepo", "Синхронизировано ${messages.size} новых сообщений")
                supportChatMessageDao.insertMessages(messages)
            } else {
                Log.d("SupportChatRepo", "Ошибка syncMessages код=${response.code()}")
            }
        } catch (e: Exception) {
            Log.d("SupportChatRepo", "Ошибка syncMessages: ${e.message}")
        }
    }

    // Пометка сообщений прочитанными
    suspend fun markAsRead(messageIds: List<Long>, token: String?) {
        try {
            Log.d("SupportChatRepo", "Пометка сообщений прочитанными: $messageIds")
            val request = MarkReadSupportMessageRequest(messageIds)
            val response = apiService.markMessagesRead(request, "Token $token")
            if (response.isSuccessful) {
                supportChatMessageDao.markMessagesRead(messageIds)
                Log.d("SupportChatRepo", "Сообщения успешно отмечены прочитанными")
            } else {
                Log.d("SupportChatRepo", "Ошибка markMessagesRead код=${response.code()}")
            }
        } catch (e: Exception) {
            Log.d("SupportChatRepo", "Ошибка markMessagesRead: ${e.message}")
        }
    }

    // Добавление нового сообщения локально
    suspend fun insertLocalMessage(message: SupportChatMessage) {
        Log.d("SupportChatRepo", "Добавление локального сообщения id=${message.id}")
        supportChatMessageDao.insertMessage(message)
    }

    suspend fun updateMessageStatus(message: SupportChatMessage, status: String) {
        supportChatMessageDao.updateSyncStatus(message.id, status)
    }
    suspend fun deleteLocalMessage(message: SupportChatMessage) {
        Log.d("SupportChatRepo_delete_local", "Delete сообщения id=${message.id}")
        supportChatMessageDao.delete(message)
    }
    suspend fun deleteMessageOnServer(message: SupportChatMessage, token: String?): Result<Unit> {
        Log.d("SupportChatRepo_delete_sercer", "Delete сообщения id=${message.server_id}")
        val request = DeleteMessageRequest(server_id = message.server_id!!)
        return try {
            apiService.deleteMessage(request, "Token $token")
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun mapToModel(dto: SupportChatMessageResponseDTO): SupportChatMessageResponse {
        return SupportChatMessageResponse(
            serverId = dto.serverId,
            senderId = dto.sender.id,
            senderUid = dto.sender.uid,
            receiverId = dto.receiver.id,
            receiverUid = dto.receiver.uid,
            text = dto.text,
            replyToId = null, // если reply_to_id не приходит, возможно нужно извлечь из replyToText или игнорировать
            createdAt = dto.createdAt,
            isRead = dto.isRead
        )
    }
}


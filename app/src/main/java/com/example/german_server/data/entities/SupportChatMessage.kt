package com.example.german_server.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "support_chat_messages",
    indices = [
        Index(value = ["server_id"]),
        Index(value = ["created_at"])
    ]
)
data class SupportChatMessage(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val server_id: Long?,        // id на сервере (null если сообщение ещё не отправлено)

    val sender_id: Long,

    val receiver_id: Long,

    val receiverUid: String?, // <-- добавить UUID сервера

    val senderUid: String?,

    val text: String,

    val reply_to_id: Long?,      // server_id сообщения, на которое отвечаем

    val created_at: Long,        // timestamp

    val is_read: Boolean,

    val sync_status: String      // pending / sent / error

)


object SyncStatus {

    const val PENDING = "pending"

    const val SENT = "sent"

    const val ERROR = "error"
}
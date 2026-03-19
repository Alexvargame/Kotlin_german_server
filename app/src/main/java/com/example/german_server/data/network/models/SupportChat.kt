package com.example.german_server.data.network.models
import com.google.gson.annotations.SerializedName
// Запрос на отправку
data class SendSupportChatMessageRequest(


    @SerializedName("receiver_uid")
    val receiverUid: String,            // receiver_id

    @SerializedName("text")
    val text: String,                // текст сообщения

    @SerializedName("reply_to")
    val replyToId: Long? = null      // reply_to_id
)
// Ответ сервера на сообщение
data class SupportChatMessageResponse(
    @SerializedName("id")
    val serverId: Long,

    @SerializedName("sender")
    val senderId: Long,

    @SerializedName("sender_uid")
    val senderUid: String,

    @SerializedName("receiver")
    val receiverId: Long,

    @SerializedName("receiver_uid")
    val receiverUid: String,

    @SerializedName("text")
    val text: String,             // текст сообщения

    @SerializedName("reply_to")
    val replyToId: Long?,         // reply_to_id

    @SerializedName("created_at")
    val createdAt: String,          // created_at

    @SerializedName("is_read")
    val isRead: Boolean           // is_read
)
// Запрос на пометку прочитанными
data class MarkReadSupportMessageRequest(
    @SerializedName("message_ids")
    val messageIds: List<Long>
)
data class DeleteMessageRequest(
    val server_id: Long
)

data class SupportChatMessageResponseDTO(
    @SerializedName("id") val serverId: Long,
    @SerializedName("sender") val sender: SenderDTO,
    @SerializedName("receiver") val receiver: ReceiverDTO,
    @SerializedName("text") val text: String,
    @SerializedName("reply_to_text") val replyToText: String?,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("is_read") val isRead: Boolean
) {
    data class SenderDTO(
        @SerializedName("id") val id: Long,
        @SerializedName("uid") val uid: String
    )
    data class ReceiverDTO(
        @SerializedName("id") val id: Long,
        @SerializedName("uid") val uid: String
    )
}
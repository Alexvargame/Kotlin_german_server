package com.example.german_server.data.ui.viewModel.support_chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import android.util.Log

import com.example.german_server.data.repository.support_chat.SupportChatMessageRepository
import com.example.german_server.data.entities.SupportChatMessage
import com.example.german_server.data.entities.SyncStatus
import com.example.german_server.data.entities.BaseUser

import com.example.german_server.data.network.models.SenderUser

class SupportChatViewModel(
    private val supportChatMessagerepository: SupportChatMessageRepository
) : ViewModel() {

    // StateFlow для UI
    val messages: StateFlow<List<SupportChatMessage>> = supportChatMessagerepository.messages
        .map { it.sortedBy { msg -> msg.created_at } } // сортировка по дате
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

//    private val _messages = MutableStateFlow<List<SupportChatMessage>>(emptyList())
//    fun setMessages(newMessages: List<SupportChatMessage>) {
//        _messages.value = newMessages
//    }


    // Отправка всех pending сообщений
    fun sendPendingMessages(token: String?) {
        viewModelScope.launch {
            Log.d("SupportChatVM", "Отправка pending сообщений")
            supportChatMessagerepository.sendPendingMessages(token)
        }
    }

    // Синхронизация новых сообщений с сервера
    fun syncNewMessages(token: String) {
        viewModelScope.launch {
            Log.d("SupportChatVM", "Синхронизация новых сообщений $token")
            supportChatMessagerepository.syncNewMessages(token)
           // sendPendingMessages(token)
        }
    }

    // Пометка сообщений прочитанными
//    fun markAsRead(messageIds: List<Long>) {
//        viewModelScope.launch {
//            Log.d("SupportChatVM", "Пометка прочитанными: $messageIds")
//            supportChatMessagerepository.markAsRead(messageIds)
//        }
//    }
    fun markAsRead(message: SupportChatMessage,token: String?) {
        viewModelScope.launch {
            val idForServer = message.server_id.takeIf { it != null && it != 0L } ?: message.id
            Log.d("SupportChatVM", "Помечаем сообщение: server_id=$idForServer")
            supportChatMessagerepository.markAsRead(listOf(idForServer), token)
        }
    }
//    fun sendMessage(message: SupportChatMessage,token: String?) {
//        viewModelScope.launch {
//            val idForServer = message.server_id.takeIf { it != null && it != 0L } ?: message.id
//            Log.d("SupportChatVM", "Помечаем сообщение: server_id=$idForServer")
//            supportChatMessagerepository.markAsRead(listOf(idForServer), token)
//        }
//    }
    fun sendMessage(
        text: String,
        receiver: SenderUser,              // объект получателя
        token: String,                   // токен текущего пользователя
        currentUser: BaseUser,           // текущий пользователь (отправитель)
        replyToMessage: SupportChatMessage? = null // если это ответ
    ) {
        viewModelScope.launch {

            // 1️⃣ Если это ответ — помечаем исходное сообщение прочитанным
            replyToMessage?.let { message ->
                val idForServer = message.server_id ?: message.id
                supportChatMessagerepository.markAsRead(listOf(idForServer), token)
            }

            // 2️⃣ Создаём локальное сообщение
            val newMessage = SupportChatMessage(
                id = 0, // локальный автоинкремент
                server_id = null,
                sender_id = currentUser.id,
                senderUid = currentUser.serverUid,
                receiver_id = receiver.id,
                receiverUid = receiver.serverUid,
                text = text,
                reply_to_id = replyToMessage?.server_id,
                created_at = System.currentTimeMillis(),
                is_read = false,
                sync_status = SyncStatus.PENDING
            )

            // 3️⃣ Сохраняем локально
            supportChatMessagerepository.insertLocalMessage(newMessage)

//                 4️⃣ Отправляем на сервер
            sendPendingMessages(token)
        }
    }
            // Добавление локального сообщения (например, перед отправкой)
//    fun insertLocalMessage(message: SupportChatMessage) {
//        viewModelScope.launch {
//            Log.d("SupportChatVM", "Добавление локального сообщения id=${message.id}")
//            supportChatMessagerepository.insertLocalMessage(message)
//        }
//    }

    fun deleteMessage(message: SupportChatMessage, token: String?) {
        viewModelScope.launch {
            Log.d("SupportChat_delete", "Dele te сообщения id=${message.id} " +
                    "${message.sync_status} -- ${message.server_id}")
            if (message.server_id == null) {
                supportChatMessagerepository.deleteLocalMessage(message)
                return@launch
            }

            val result = supportChatMessagerepository.deleteMessageOnServer(message, token)

            if (result.isSuccess) {
                supportChatMessagerepository.deleteLocalMessage(message)
            } else {
                supportChatMessagerepository.updateMessageStatus(message, SyncStatus.ERROR)
            }
        }

    }

//            fun deleteMessage(message: SupportChatMessage, token: String?) {
//                viewModelScope.launch {
//                    Log.d("SupportChat_delete", "Dele te сообщения id=${message.id} ${message.sync_status}")
//                    when (message.sync_status) {
//
//                        SyncStatus.PENDING -> {
//                            // сообщение ещё не ушло на сервер
//                            supportChatMessagerepository.deleteLocalMessage(message)
//                        }
//
//                        SyncStatus.SENT -> {
//                            // сообщение есть на сервере — пробуем удалить
//                            val result = supportChatMessagerepository.deleteMessageOnServer(message, token)
//                            if (result.isSuccess) {
//                                supportChatMessagerepository.deleteLocalMessage(message)
//                            } else {
//                                // не получилось удалить на сервере — ставим ERROR
//                                supportChatMessagerepository.updateMessageStatus(message, SyncStatus.ERROR)
//                            }
//                        }
//
//                        SyncStatus.ERROR -> {
//                            // сообщение не синхронизировано, либо не удалось удалить на сервере
//                            // можно повторить попытку удаления на сервере
//                            val result = supportChatMessagerepository.deleteMessageOnServer(message, token)
//                            Log.d("SupportChat_delete_error", "Dele te сообщения id=${result}")
//                            if (result.isSuccess) {
//                                supportChatMessagerepository.deleteLocalMessage(message)
//                            } else {
//                                supportChatMessagerepository.updateMessageStatus(message, SyncStatus.ERROR)
//                            }
//                        }
//                    }
//                }
//            }
}
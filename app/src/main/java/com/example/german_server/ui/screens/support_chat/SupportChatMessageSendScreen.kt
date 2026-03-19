package com.example.german_server.ui.screens.support_chat

import android.util.Log

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.navigation.NavController
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.runtime.getValue

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

import com.example.german_server.data.ui.viewModel.user_profile.UserViewModel
import com.example.german_server.data.ui.viewModel.support_chat.SupportChatViewModel

import com.example.german_server.data.network.models.SenderUser



@Composable
fun Support_chat_message_send_screen(
    userviewModel: UserViewModel,
    supportChatViewModel: SupportChatViewModel,
    navController: NavController,
) {
    val user = userviewModel.currentUser.value
    var replyText by remember { mutableStateOf("") }
    var admins by remember { mutableStateOf<List<SenderUser>>(emptyList()) }

    // Загружаем админов
    LaunchedEffect(userviewModel) {
        admins = userviewModel.fetchAllAdmin() ?: emptyList()
    }

    // Если нет пользователя, возвращаем на старт
    if (user == null) {
        LaunchedEffect(Unit) {
            navController.navigate("start_app_screen") { popUpTo(0) }
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Написать в саппорт",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White
        )

        Spacer(Modifier.height(16.dp))

        // Поле ввода сообщения
//        TextField(
//            value = replyText,
//            onValueChange = { replyText = it },
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(150.dp),
//            placeholder = { Text("Введите сообщение...") },
//            singleLine = false,
//            maxLines = 10
//        )
        TextField(
            value = replyText,
            onValueChange = { replyText = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp), // ИЗМЕНЕНО: небольшой отступ сверху/снизу
            placeholder = {
                Text(
                    "Введите ответ...",
                    color = Color.LightGray // ИЗМЕНЕНО: белый текст плейсхолдера
                )
            },
            singleLine = false,
            maxLines = 3,
            colors = TextFieldDefaults.colors( // ИЗМЕНЕНО: явная настройка цветов
                focusedContainerColor = Color.DarkGray,   // белый фон при фокусе
                unfocusedContainerColor = Color.DarkGray, // белый фон без фокуса
                disabledContainerColor = Color.White,  // если отключено
                focusedTextColor = Color.White,        // цвет вводимого текста (чёрный, чтобы было видно на белом)
                unfocusedTextColor = Color.White,
                cursorColor = Color.Black,             // цвет курсора
                focusedPlaceholderColor = Color.White, // плейсхолдер при фокусе (белый)
                unfocusedPlaceholderColor = Color.White, // плейсхолдер без фокуса (белый)
                focusedIndicatorColor = Color.Transparent, // убираем подчёркивание
                unfocusedIndicatorColor = Color.Transparent,
            ),
            shape = RoundedCornerShape(8.dp) // ИЗМЕНЕНО: скруглённые углы
        )

        Spacer(Modifier.height(16.dp))

        // Кнопка "Отправить"
        Button(
            onClick = {
                if (replyText.isNotBlank() && admins.isNotEmpty()) {
                    // Отправляем сообщение всем админам
                    val adminToSend = if (admins.size == 1) {
                        admins[0] // единственный админ
                    } else {
                        admins.random() // случайный админ
                    }

                    supportChatViewModel.sendMessage(
                        text = replyText,
                        receiver = adminToSend,
                        token = user.loginToken!!,
                        currentUser = user
                    )
                    replyText = "" // очищаем поле после отправки
                    navController.navigate("user_screen")
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = replyText.isNotBlank() && admins.isNotEmpty(),

        ) {
            Text("Отправить")
        }

        Spacer(Modifier.height(8.dp))

        // Кнопка "Назад"
        Button(
            onClick = { navController.navigate("user_screen") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Назад")
        }
    }
}
//@Composable
//fun Support_chat_message_send_screen(
//    userviewModel: UserViewModel,
//    supportChatViewModel: SupportChatViewModel,
//    navController: NavController,
//) {
//
//    val user = userviewModel.currentUser.value
//    val messages by supportChatViewModel.messages.collectAsState()
//    Log.d("MESSAGE_SCREEN_MESSAGE", "${messages}")
//
//
//
//
//    val unreadCount = messages.count {
//        Log.d("MESSAGE_SCREEN_SEND_RECEIV", "${it.receiverUid} /  ${user?.serverUid}")
//        Log.d("MESSAGE_SCREEN_equal", "${it.receiverUid ==  user?.serverUid}")
//        !it.is_read && it.receiverUid == user?.serverUid
//    }
//
//
//
//    var openedMessageId by remember { mutableStateOf<Long?>(null) }
//
//    Log.d("MESSAGE_SCREEN_UNREAD_MODEL1", "${unreadCount}")
//    Log.d("MESSAGE_SCREEN_User", "${user}")
//    // ⬇️⬇️⬇️ ПРОВЕРКА ВЕРИФИКАЦИИ ПРИ ЗАХОДЕ ⬇️⬇️⬇️
//    LaunchedEffect(user) {
//        user?.loginToken?.let { token ->
//            Log.d("MESSAGE_SCREEN_DEBUG", "Синхронизация, токен = $token")
//            supportChatViewModel.syncNewMessages(token)
//        }
//    }
//    var admins by remember { mutableStateOf<List<SenderUser>>(emptyList()) }
//    LaunchedEffect(userviewModel) {
//        admins = userviewModel.fetchAllAdmin() ?: emptyList()
//        Log.d("MESSAGE_SCREEN_ADMINS ", " admins = $admins")
//    }
//    // ⬆️⬆️⬆️ КОНЕЦ ПРОВЕРКИ ⬆️⬆️⬆️
//    if (user == null) {
//        // Если кто-то попал без логина — вернём на старт
//        LaunchedEffect(Unit) { navController.navigate("start_app_screen") { popUpTo(0) } }
//        return
//    }
//    // LazyColumn с нормальным списком сообщений
//    // BOX со списком сообщений
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//
//        Text(
//            text = "Сообщения",
//            style = MaterialTheme.typography.titleLarge
//        )
//
//        Spacer(Modifier.height(12.dp))
//
//        // BOX со списком сообщений
//        Box(
//            modifier = Modifier
//                .weight(1f)
//                .fillMaxWidth()
//                .border(1.dp, Color.Gray)
//                .padding(8.dp)
//        ) {
//
//            LazyColumn(
//                modifier = Modifier.fillMaxSize(),
//                verticalArrangement = Arrangement.spacedBy(6.dp)
//            ) {
//               // key = { it.server_id ?: it.hashCode().toLong() }
//                items(messages,key = { it.server_id ?: it.id }){ msg ->
//                    val messageKey = msg.server_id ?: msg.id
//
//                    // ⬇️ ПОЛУЧАЕМ отправителя из кэша
//                    SupportMessageSendCard(
//                        msg = msg,
//                        currentUserUid = user.serverUid,
//                        expanded = openedMessageId == messageKey,// msg.server_id,
//                        onClick = {
//
//                            openedMessageId =
//                                if (openedMessageId == messageKey) null
//                                else messageKey//msg.server_id
//
//                            if (!msg.is_read && msg.receiverUid == user.serverUid) {
//                                supportChatViewModel.markAsRead(msg, user.loginToken)
//                            }
//
//                        },
//                        viewModel = supportChatViewModel,  // передаём VM, чтобы карточка сама брала данные
//                        userviewModel = userviewModel,
//                        loginToken = user.loginToken,
//
//                    )
//
//                }
//
//            }
//
//        }
//
//        Spacer(Modifier.height(16.dp))
//
//        Button(
//            onClick = { navController.navigate("support_chat_message_send_screen") },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("Написать в саппорт")
//        }
//        Button(
//            onClick = { navController.navigate("user_screen") },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("Назад")
//        }
//
//    }
//}
//
//@Composable
//fun SupportMessageSendCard(
//    msg: SupportChatMessage,
//    currentUserUid: String?,
//    expanded: Boolean,
//    onClick: () -> Unit,
//    viewModel: SupportChatViewModel,
//    userviewModel: UserViewModel,
//    loginToken: String?,
//) {
//    var current by remember { mutableStateOf<BaseUser?>(null) }
//    var receiver by remember { mutableStateOf<SenderUser?>(null) }
//    //val isNew = !msg.is_read && msg.receiverUid == currentUserUid
//    val isNew = !msg.is_read && msg.receiverUid == currentUserUid && msg.senderUid != currentUserUid
//    var replyText by remember { mutableStateOf("") }
//    Log.d("MESSAGE_SCREEN_UID", "${currentUserUid}  / ${msg.senderUid}")
//    LaunchedEffect(userviewModel, currentUserUid) {
//        current = currentUserUid?.let { uid ->
//            userviewModel.getUserByServerUid(uid)
//        }
//    }
//
//
//    LaunchedEffect(userviewModel, msg.senderUid) {
//        if (msg.senderUid != null && loginToken != null) {
//            receiver = userviewModel.fetchSenderUser(msg.senderUid)
//        }
//    }
//    Card(
//        modifier = Modifier
//            .fillMaxWidth(),
//
//            elevation = CardDefaults.cardElevation(4.dp)
//    ) {
//
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(12.dp)
//                .clickable { onClick() },
//        ) {
//
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//
//                Text(
//                    text = "Сообщение от ${receiver} ",
//                    style = MaterialTheme.typography.labelLarge
//                )
//
//                if (isNew) {
//                    Text(
//                        text = "NEW",
//                        color = Color.Red,
//                        style = MaterialTheme.typography.labelMedium
//                    )
//                }
//
//            }
//
//            Spacer(Modifier.height(6.dp))
//
//            Text(
//                text = if (expanded) msg.text else msg.text.take(50) + if (msg.text.length > 50) "…" else "",
//                style = MaterialTheme.typography.bodyLarge
//            )
//
//            Spacer(Modifier.height(6.dp))
//
//            Text(
//                text = formatMessageDate(msg.created_at),
//                style = MaterialTheme.typography.labelSmall,
//                color = Color.Gray
//            )
//
//            if (expanded) {
//                Spacer(Modifier.height(12.dp))
//                val canReply = msg.senderUid != currentUserUid
//                if (canReply) {
//                    TextField(
//                        value = replyText,
//                        onValueChange = { replyText = it }, // изменение сразу в VM
//                        modifier = Modifier.fillMaxWidth(),
//                        placeholder = { Text("Введите ответ...") },
//                        singleLine = false,
//                        maxLines = 3
//                    )
//                }
//                Spacer(Modifier.height(8.dp))
//
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.spacedBy(8.dp)
//                ) {
//                    if (canReply) {
//                        Button(
//                            onClick = {
//                                val safeCurrent = current
//                                val safeReceiver = receiver
//                                Log.d("MESSAGE_SCREEN_SAFE", "получатель: ${safeReceiver} - польщователь: ${safeCurrent}")
//                                if (safeCurrent != null && safeReceiver != null && loginToken != null && replyText.isNotBlank()) {
//                                    viewModel.sendMessage(
//                                        text = replyText,
//                                        receiver = safeReceiver,
//                                        token = loginToken,
//                                        currentUser = safeCurrent,
//                                        replyToMessage = msg
//                                    )
//                                } else {
//                                    Log.d("SupportMessageCard", "Не удалось отправить сообщение: данные отсутствуют")
//                                }
//
//                            },
//                            modifier = Modifier.weight(1f),
//                            enabled = replyText.isNotBlank() // кнопка активна только если есть текст
//                        ) {
//                            Text("Ответить")
//                        }
//                    }
//
//
//                    Button(
//                        onClick = { viewModel.deleteMessage(msg, loginToken) },
//                        modifier = Modifier.weight(1f),
//
//                    ) {
//                        Text("Удалить",
//                            color = Color.Red)
//                    }
//                }
//            }
//        }
//
//    }
//}

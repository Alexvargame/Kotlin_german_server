package com.example.german_server

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val TAG = "FCM_DEBUG"

    override fun onMessageReceived(message: RemoteMessage) {
        Log.d(TAG, "onMessageReceived вызван")

        val userId = message.data["user_id"]
//        val currentUserId = getCurrentUserIdFromPrefs()
//
//        if (userId != null && userId == currentUserId) {
//            // Обновить счётчик в БД и обновить бейдж
//            updateBadge(currentUserId)
//        }

        // Логируем уведомление, если есть
        message.notification?.let {
            Log.d(TAG, "Заголовок: ${it.title}")
            Log.d(TAG, "Текст: ${it.body}")
        } ?: Log.d(TAG, "Нет поля notification")

        // Логируем data payload, если есть
        if (message.data.isNotEmpty()) {
            Log.d(TAG, "Data payload: ${message.data}")
        } else {
            Log.d(TAG, "Нет data payload")
        }

        val title = message.notification?.title ?: "Уведомление"
        val body = message.notification?.body ?: ""

        showNotification(title, body)
    }

    private fun showNotification(title: String, content: String) {
        Log.d(TAG, "showNotification: title=$title, content=$content")

        val channelId = "fcm_default_channel"
        val notificationId = (System.currentTimeMillis() % 10000).toInt()

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Уведомления FCM",
                NotificationManager.IMPORTANCE_HIGH
            )
            manager.createNotificationChannel(channel)
            Log.d(TAG, "Канал уведомлений создан/проверен")
        }

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(android.R.drawable.ic_dialog_info) // замените на свою иконку
            .setAutoCancel(true)
            .build()

        manager.notify(notificationId, notification)
        Log.d(TAG, "Уведомление показано, id=$notificationId")
    }
}
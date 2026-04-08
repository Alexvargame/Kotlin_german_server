package com.example.german_server.data.ui.components

import android.content.Context
import android.util.Log
import com.example.german_server.data.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ResetDailyQuests(private val context: Context) {

    fun resetFlags() {
        Log.d("TEST_DB_RESET_QUESTS", "Context ${context}")

        val db = AppDatabase.getInstance(context)
        Log.d("TEST_DB_RESET_QUESTS", "DB path: ${context.getDatabasePath("app.db")}")
        val baseUserDao = db.baseUserDao()

        CoroutineScope(Dispatchers.IO).launch {
            val users = baseUserDao.getAll()
            val today = getCurrentDate()

            users.forEach { user ->
                val lastLoginDate = user.last_login_date?.let { formatDate(it) } ?: ""
                Log.d("TEST_DB_RESET_QUESTS", "Заходил сегодня*?: ${lastLoginDate != today}/${lastLoginDate}/${today}")
                Log.d("TEST_DB_RESET_QUESTS_task", "Еесть задание*?: ${user.lastQuestReset}")
                if (lastLoginDate != today && user.lastQuestReset) {
                    // Пользователь заходил не сегодня, но флаг true → сбрасываем
                    baseUserDao.updateLastQuestReset(user.id, false)
                    Log.d("TEST_DB_RESET_QUESTS", "Сброшен флаг для user: ${user.id}, был true → false")
                }
//                else if (lastLoginDate == today && !user.lastQuestReset) {
//                    // Пользователь заходил сегодня, но флаг false → ставим true
//                    baseUserDao.updateLastQuestReset(user.id, true)
//                    Log.d("TEST_DB_RESET_QUESTS", "Установлен флаг для user: ${user.id}, был false → true")
//                }
            }
        }
    }

    private fun getCurrentDate(): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    }

    private fun formatDate(timestamp: Long): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(timestamp))
    }
}
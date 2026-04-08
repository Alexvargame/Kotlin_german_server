package com.example.german_server.test_add

import android.content.Context
import android.util.Log
import com.example.german_server.data.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Read_quest(private val context: Context) {


    fun readquests() {
   

        Log.d("TEST_DB_quests", " Context ${context}")
        //AppDatabase.resetInstance()
        //context.deleteDatabase("app_database_name.db")


        val db = AppDatabase.getInstance(context)
        Log.d("TEST_APP_DB_quests", "DB path: ${context.getDatabasePath("app.db")}")
        val questsDao = db.dailyQuestDao()

        CoroutineScope(Dispatchers.IO).launch {

            val quests = questsDao.getAllQuests()
            Log.d("TEST_DB_QUESTS", ": ${quests}")
            quests.forEach {
                Log.d("TEST_DB_QUEST", "Quest: ${it.id} / ${it.userId} " +
                        "/${it.questTitle} /${it.conditionType}")
            }
////             2. ПОТОМ УДАЛЯЕМ ВСЕ
//            questsDao.deleteAllQuests()
//            Log.d("TEST_DB_QUESTS", "🗑️ Все задания удалены")
//
//            // 3. ПОТОМ ПРОВЕРЯЕМ ЧТО ПУСТО
//            val afterDelete = questsDao.getAllQuests()
//            Log.d("TEST_DB_QUESTS_AFTER", "ПОСЛЕ УДАЛЕНИЯ: ${afterDelete.size} заданий")
        }
    }
}



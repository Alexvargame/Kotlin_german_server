package com.example.german_server.data.repository.daily_quests




import android.util.Log
import com.example.german_server.data.network.ApiService
import com.example.german_server.data.dao.BaseUserDao
import com.example.german_server.data.dao.DailyQuestDao
import com.example.german_server.data.entities.DailyQuestEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DailyQuestRepository(
    private val dailyQuestDao: DailyQuestDao,
    private val baseUserDao: BaseUserDao,
    private val apiService: ApiService,
) {




    suspend fun getTodayQuests(userId: Long): List<DailyQuestEntity> {
        val today = getCurrentDate()
        return dailyQuestDao.getTodayQuests(userId, today)
    }

    suspend fun generateDailyQuests(userId: Long): List<DailyQuestEntity> {
        val today = getCurrentDate()

        //dailyQuestDao.deleteOldQuests(userId, today)

        val quests = mutableListOf<DailyQuestEntity>()

        repeat(3) { index ->
            Log.d("DAILY_QUEST_REPO", "🔄 Генерация задания №${index + 1}")
            val quest = createRandomQuest(userId, today)
            Log.d("DAILY_QUEST_REPO", "✅ Создано задание: ${quest.conditionType}, target=${quest.target}")
            quests.add(quest)
        }

        // ✅ ЛОГ ПЕРЕД ВСТАВКОЙ
        Log.d("DAILY_QUEST_REPO", "📦 ПЕРЕД insertAll: размер списка = ${quests.size}")
        quests.forEachIndexed { i, q ->
            Log.d("DAILY_QUEST_REPO", "   [${i}] ${q.conditionType} target=${q.target}")
        }

        dailyQuestDao.insertAll(quests)

        // ✅ ЛОГ ПОСЛЕ ВСТАВКИ
        val afterInsert = dailyQuestDao.getTodayQuests(userId, today)
        Log.d("DAILY_QUEST_REPO", "📦 ПОСЛЕ insertAll: в БД = ${afterInsert.size} заданий")


        return quests
    }

    private fun createRandomQuest(userId: Long, date: String): DailyQuestEntity {
        val types = listOf("earn_score", "play_games", "win_games")
        val type = types.random()

        Log.d("DailyQuest", "🎲 Генерация задания типа: $type для userId: $userId")

        return when (type) {
            "earn_score" -> {
                val target = (10..20).random()
                val rewardScore = (20..100).random()
                val rewardCoins = (target / 10) * 2

                Log.d("DAILY_QUEST_REPO", "✅ earn_score: target=$target, rewardScore=$rewardScore, rewardCoins=$rewardCoins")

                DailyQuestEntity(
                    userId = userId,
                    questTitle = "Набрать очков $target",
                    conditionType = type,
                    target = target,
                    progress = 0,
                    isCompleted = false,
                    rewardScore = rewardScore,
                    rewardCoins = rewardCoins,
                    date = date
                )
            }

            "play_games" -> {
                val target = (1..5).random()
                val rewardScore = (15..50).random()
                val rewardCoins = target * 3

                Log.d("DAILY_QUEST_REPO", "✅ play_games: target=$target, rewardScore=$rewardScore, rewardCoins=$rewardCoins")

                DailyQuestEntity(
                    userId = userId,
                    questTitle = "Выполнить упражнений $target",
                    conditionType = type,
                    target = target,
                    progress = 0,
                    isCompleted = false,
                    rewardScore = rewardScore,
                    rewardCoins = rewardCoins,
                    date = date
                )
            }

            else -> {
                val target = (1..3).random()
                val rewardScore = (25..75).random()
                val rewardCoins = target * 5

                Log.d("DAILY_QUEST_REPO", "✅ win_games: target=$target, rewardScore=$rewardScore, rewardCoins=$rewardCoins")

                DailyQuestEntity(
                    userId = userId,
                    questTitle = "Упражнений без ошибок $target",
                    conditionType = type,
                    target = target,
                    progress = 0,
                    isCompleted = false,
                    rewardScore = rewardScore,
                    rewardCoins = rewardCoins,
                    date = date
                )
            }
        }
    }

    suspend fun hasQuestsForToday(userId: Long): Boolean {
        val today = getCurrentDate()
        val quests = dailyQuestDao.getTodayQuests(userId, today)
        return quests.isNotEmpty()
    }

    suspend fun updateProgressByType(
        userId: Long,
        conditionType: String,
        increment: Int? = null,
        value: Int? = null
    ): List<DailyQuestEntity> {  // возвращаем выполненные задания
        val today = getCurrentDate()
        val quests = dailyQuestDao.getTodayQuests(userId, today)
        val completedQuests = mutableListOf<DailyQuestEntity>()
        Log.d("PROGRESS","setUser -> $userId, cond: $conditionType, increment: $increment, value: $value")
        quests.filter { it.conditionType == conditionType && !it.isCompleted }
            .forEach { quest ->
                val addAmount = when {
                    value != null -> value
                    increment != null -> increment
                    else -> 1
                }
                val newProgress = quest.progress + addAmount

                if (newProgress >= quest.target) {
                    quest.isCompleted = true
                    quest.progress = quest.target
                    // 🆕 ВЫДАЧА НАГРАДЫ
                    baseUserDao.updateDailyQuestScore(userId, quest.rewardScore)
                    baseUserDao.updateDailyQuestCoins(userId, quest.rewardCoins)
                    Log.d("QUEST_REWARD", "Выдана награда за ${quest.questTitle}: +${quest.rewardScore} очков, +${quest.rewardCoins} монет")
                    completedQuests.add(quest)  // запоминаем
                } else {
                    quest.progress = newProgress
                }
                dailyQuestDao.update(quest)
            }

        return completedQuests  // возвращаем выполненные
    }

    private fun getCurrentDate(): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    }
}
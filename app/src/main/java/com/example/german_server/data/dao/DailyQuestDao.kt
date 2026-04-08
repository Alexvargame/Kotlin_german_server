package com.example.german_server.data.dao


import androidx.room.*
import com.example.german_server.data.entities.DailyQuestEntity
import com.example.german_server.data.entities.UserAvatar

@Dao
interface DailyQuestDao {
    @Query("SELECT * FROM daily_quests WHERE userId = :userId AND date = :date")
    suspend fun getTodayQuests(userId: Long, date: String): List<DailyQuestEntity>  // userId: Long

    @Query("SELECT * FROM daily_quests WHERE userId = :userId AND date = :date AND isCompleted = 0")
    suspend fun getTodayIncompleteQuests(userId: Long, date: String): List<DailyQuestEntity>  // userId: Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(quests: List<DailyQuestEntity>)

    @Update
    suspend fun update(quest: DailyQuestEntity)

    @Query("UPDATE daily_quests SET progress = :progress, isCompleted = :isCompleted WHERE id = :questId")
    suspend fun updateProgress(questId: Long, progress: Int, isCompleted: Boolean)  // questId: Long

    @Query("DELETE FROM daily_quests WHERE userId = :userId AND date < :date")
    suspend fun deleteOldQuests(userId: Long, date: String)  // userId: Long

    @Query("SELECT * FROM daily_quests")
    suspend fun getAllQuests(): List<DailyQuestEntity>

    @Query("DELETE FROM daily_quests")
    suspend fun deleteAllQuests()
}
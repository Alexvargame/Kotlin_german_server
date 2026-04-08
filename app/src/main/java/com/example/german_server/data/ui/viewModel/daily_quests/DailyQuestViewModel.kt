package com.example.german_server.data.ui.viewModel.daily_quests

import android.util.Log

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.german_server.data.entities.DailyQuestEntity
import com.example.german_server.data.repository.daily_quests.DailyQuestRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DailyQuestViewModel(
    private val dailyQuestRepository: DailyQuestRepository
) : ViewModel() {

    private val _quests = MutableStateFlow<List<DailyQuestEntity>>(emptyList())
    val quests: StateFlow<List<DailyQuestEntity>> = _quests.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    suspend fun checkHasQuests(userId: Long): Boolean {
        return dailyQuestRepository.hasQuestsForToday(userId)
    }

    fun loadTodayQuests(userId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            val todayQuests = dailyQuestRepository.getTodayQuests(userId)
            _quests.value = todayQuests
            _isLoading.value = false
        }
    }

    fun generateAndLoadQuests(userId: Long) {
        viewModelScope.launch {
            Log.d("DAILY_QUEST_USER", "$userId")
            _isLoading.value = true
            dailyQuestRepository.generateDailyQuests(userId)
            val todayQuests = dailyQuestRepository.getTodayQuests(userId)
            _quests.value = todayQuests
            _isLoading.value = false
        }
        Log.d("DAILY_QUEST_EXIST", "$_isLoading")
    }

//    fun updateQuestProgress(questId: Long, progress: Int, isCompleted: Boolean) {
//        viewModelScope.launch {
//            dailyQuestRepository.updateProgress(questId, progress, isCompleted)
//            // Обновляем список
//            _quests.value = _quests.value.map { quest ->
//                if (quest.id == questId) {
//                    quest.copy(progress = progress, isCompleted = isCompleted)
//                } else {
//                    quest
//                }
//            }
//        }
//    }
}
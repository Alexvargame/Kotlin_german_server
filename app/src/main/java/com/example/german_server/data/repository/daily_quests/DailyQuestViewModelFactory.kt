package com.example.german_server.data.repository.daily_quests



import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.german_server.data.ui.viewModel.daily_quests.DailyQuestViewModel

class DailyQuestViewModelFactory(
    private val repo: DailyQuestRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DailyQuestViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DailyQuestViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
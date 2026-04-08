package com.example.german_server.data.ui.components



import android.util.Log
import com.example.german_server.data.repository.daily_quests.DailyQuestRepository
import com.example.german_server.data.repository.user_profile.UserProfileRepository
import javax.inject.Inject

class UpdateQuestsAfterExerciseUseCase(
    private val questRepo: DailyQuestRepository
) {
    suspend fun execute(userId: Long, correct: Int, wrong: Int) {
        Log.d("PROGRESS","setUser -> $userId, $correct")
        questRepo.updateProgressByType(userId, "play_games", increment = 1)
        if (wrong == 0) {
            questRepo.updateProgressByType(userId, "win_games", increment = 1)
        }
        if (correct > 0) {
            questRepo.updateProgressByType(userId, "earn_score", value = correct)
        }
    }
}

//class CompleteExerciseUseCase @Inject constructor(
//    private val userRepo: UserProfileRepository,
//    private val questRepo: DailyQuestRepository
//) {
//    suspend fun execute(userId: Long, correct: Int, wrong: Int) {
//        // 1. Жизни
//        if (wrong > 0) {
//            userRepo.decreaseLife(userId)
//        }
//
//        // 2. Очки
//        if (correct > 0) {
//            userRepo.addScore(userId, correct)
//        }
//
//        // 3. Квесты
//        if (correct > 0) {
//            questRepo.updateProgress(userId, "earn_score", value = correct)
//        }
//        questRepo.updateProgress(userId, "play_games", increment = 1)
//        if (wrong == 0) {
//            questRepo.updateProgress(userId, "win_games", increment = 1)
//        }
//    }
//}
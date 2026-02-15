package com.example.german_server.data.ui.viewModel

//import android.util.Log
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.german_server.data.network.models.LeaderboardResponse
//import com.example.german_server.data.network.models.LeaderboardUser
//import com.example.german_server.data.repository.leaderboardRepository
//import com.example.german_server.data.ui.viewModel.user_profile.UserViewModel
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch

//data class LeaderboardState(
//    val scoreTop: List<LeaderboardUser> = emptyList(),
//    val scoreMyRank: Int? = null,
//    val streakTop: List<LeaderboardUser> = emptyList(),
//    val streakMyRank: Int? = null,
//    val isLoading: Boolean = false,
//    val error: String? = null
//)

//class LeaderboardViewModel(
//    private val repository: LeaderboardRepository,
//    private val userViewModel: UserViewModel
//) : ViewModel() {
//
//    private val token: String?
//        get() = userViewModel.currentUser.value?.loginToken
//
//    private val _state = MutableStateFlow(LeaderboardState(isLoading = true))
//    val state: StateFlow<LeaderboardState> = _state
//
//    init {
//        Log.d("LeaderboardVM", "🏁 LeaderboardViewModel создан, запускаем loadLeaderboard()")
//        loadLeaderboard()
//    }
//
//    fun loadLeaderboard() {
//        viewModelScope.launch {
//            Log.d("LeaderboardVM", "🔄 loadLeaderboard() вызван")
//            _state.value = _state.value.copy(isLoading = true, error = null)
//            try {
//                val response = repository.loadRating(token)
//                if (response != null) {
//                    Log.d(
//                        "LeaderboardVM",
//                        "✅ Данные рейтинга получены: " +
//                                "scoreTop=${response.scoreRating.top.size}, " +
//                                "scoreMyRank=${response.scoreRating.currentUserRank}, " +
//                                "streakTop=${response.shockmodRating.top.size}, " +
//                                "streakMyRank=${response.shockmodRating.currentUserRank}"
//                    )
//
//                    _state.value = LeaderboardState(
//                        scoreTop = response.scoreRating.top,
//                        scoreMyRank = response.scoreRating.currentUserRank,
//                        streakTop = response.shockmodRating.top,
//                        streakMyRank = response.shockmodRating.currentUserRank,
//                        isLoading = false,
//                        error = null
//                    )
//                    Log.d("LeaderboardVM", "📊 StateFlow обновлён успешно")
//                } else {
//                    Log.e("LeaderboardVM", "❌ Рейтинг не получен (response null)")
//                    _state.value = _state.value.copy(
//                        isLoading = false,
//                        error = "Ошибка сервера: пустой ответ"
//                    )
//                }
//            } catch (e: Exception) {
//                Log.e("LeaderboardVM", "🔥 Ошибка при загрузке рейтинга: ${e.message}", e)
//                _state.value = _state.value.copy(
//                    isLoading = false,
//                    error = e.message ?: "Неизвестная ошибка"
//                )
//            }
//        }
//    }
//}
//
//class LeaderboardViewModel(
//    private val repository: leaderboardRepository,
//    private val userViewModel: UserViewModel
//) : ViewModel() {
//
//    // метод для загрузки рейтинга по кнопке
//    suspend fun loadLeaderboard(): LeaderboardState? {
//        val token = userViewModel.currentUser.value?.loginToken ?: return null
//
//        return try {
//            val response = repository.loadRating(token)
//            response?.let {
//                LeaderboardState(
//                    scoreTop = it.scoreRating.top,
//                    scoreMyRank = it.scoreRating.currentUserRank,
//                    streakTop = it.shockmodRating.top,
//                    streakMyRank = it.shockmodRating.currentUserRank,
//                    isLoading = false,
//                    error = null
//                )
//            }
//        } catch (e: Exception) {
//            LeaderboardState(error = e.message ?: "Ошибка сервера")
//        }
//    }
//}

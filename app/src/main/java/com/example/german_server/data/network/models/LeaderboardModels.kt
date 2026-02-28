package com.example.german_server.data.network.models

import com.google.gson.annotations.SerializedName

data class LeaderboardResponse(
    @SerializedName("score_rating") val scoreRating: Rating,
    @SerializedName("shockmod_rating") val shockmodRating: Rating
)

data class Rating(
    val type: String,
    val top: List<LeaderboardUser>,
    @SerializedName("current_user_rank") val currentUserRank: Int?
)

data class LeaderboardUser(

    val uid: String,
    val email: String,
    val username: String?,
    val score: Int,
    @SerializedName("streak_days") val streakDays: Int,
    val phone: String?,
    @SerializedName("is_verified") val isVerified: Boolean,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("last_session_date") val lastSessionDate: Long?,
    @SerializedName("avatar_name") val avatarName: String?,
    @SerializedName("avatar_path") val avatarPath: String?,


)

data class LeaderboardState(
    val scoreTop: List<LeaderboardUser> = emptyList(),
    val scoreMyRank: Int? = null,
    val streakTop: List<LeaderboardUser> = emptyList(),
    val streakMyRank: Int? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

enum class SortType {
    SCORE,   // сортировка по очкам
    DAYS     // сортировка по дням / серии / активности
}

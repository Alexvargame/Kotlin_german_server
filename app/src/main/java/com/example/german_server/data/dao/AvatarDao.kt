package com.example.german_server.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

import com.example.german_server.data.entities.UserAvatar

@Dao
interface UserAvatarDao {

    @Query("SELECT * FROM user_avatars WHERE userId = :userId")
    suspend fun getUserAvatars(userId: Long): List<UserAvatar>

//    @Query("SELECT * FROM user_avatars WHERE userId = :userId")
//    fun getUserAvatars(userId: Long): Flow<List<UserAvatar>>
    @Query("UPDATE user_avatars SET isActive = 0 WHERE userId = :userId")
    suspend fun deactivateAll(userId: Long)

    @Query("SELECT * FROM user_avatars")
    suspend fun getAllGalleryAvatars(): List<UserAvatar>

    @Insert
    suspend fun insertAvatar(avatar: UserAvatar):Long

//    @Query("UPDATE user_avatars SET isActive = 1 WHERE id = :avatarId")
//    suspend fun activateAvatar(avatarId: Long)
    @Query("UPDATE user_avatars SET isActive = 1 WHERE userId = :userId AND path = :path")
    suspend fun activateAvatar(userId: Long, path: String)
    @Query("SELECT * FROM user_avatars WHERE userId = :userId")
    fun getAvatarsForUserFlow(userId: String): Flow<List<UserAvatar>>
    @Query("SELECT * FROM user_avatars WHERE userId = :userId AND isActive = 1 LIMIT 1")
    suspend fun getActiveAvatar(userId: Long): UserAvatar?


    // 🔥 Новый метод: удаление аватара
    @Query("DELETE FROM user_avatars WHERE id = :avatarId")
    suspend fun deleteAvatar(avatarId: Long)

    @Query("SELECT * FROM user_avatars WHERE userId = :userId AND path = :path")
    suspend fun getAvatarByPath(userId: Long, path: String): UserAvatar?
}
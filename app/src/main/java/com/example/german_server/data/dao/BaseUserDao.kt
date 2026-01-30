package com.example.german_server.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.german_server.data.entities.BaseUser

@Dao
interface BaseUserDao {

    @Insert
    suspend fun insert(user: BaseUser):Long

    @Insert
    suspend fun insertAll(users: List<BaseUser>)

    @Query("SELECT * FROM users_baseuser")
    suspend fun getAll(): List<BaseUser>

    @Query("SELECT * FROM users_baseuser WHERE id = :id")
    suspend fun getById(id: Long): BaseUser?

    @Query("SELECT * FROM users_baseuser WHERE username = :username")
    suspend fun getByUsername(username: String): BaseUser?

    @Query("SELECT * FROM users_baseuser WHERE email = :email")
    suspend fun getByEmail(email: String): BaseUser?

    @Query("""
        SELECT * FROM users_baseuser 
        WHERE (username = :loginOrEmail OR email = :loginOrEmail) 
        AND password = :password 
        LIMIT 1
    """)
    suspend fun getUser(loginOrEmail: String, password: String): BaseUser?

    @Update
    suspend fun update(user: BaseUser)

    @Delete
    suspend fun delete(user: BaseUser)

    @Query("UPDATE users_baseuser SET avatar_path = :path WHERE id = :userId")
    fun updateAvatar(userId: Long, path: String?)  // Обновление аватарки

    @Query("UPDATE users_baseuser SET serverUid = :uid, loginToken = :token WHERE id = :userId")
    suspend fun updateServerData(userId: Long, uid: String?, token: String?)

    @Query("SELECT * FROM users_baseuser WHERE serverUid = :uid")
    suspend fun getByServerUid(uid: String): BaseUser?
}

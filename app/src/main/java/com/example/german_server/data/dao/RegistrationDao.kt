package com.example.german_server.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.german_server.data.entities.BaseUser
import androidx.room.OnConflictStrategy



@Dao
interface UserRegistrationDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: BaseUser)

    @Query("SELECT * FROM users_baseuser WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): BaseUser?

    @Query("SELECT * FROM users_baseuser WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername(username: String): BaseUser?
}

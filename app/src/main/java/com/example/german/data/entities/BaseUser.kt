package com.example.german.data.entities


import androidx.room.*


@Entity(
    tableName = "users_baseuser",
    foreignKeys = [
        ForeignKey(
            entity = UserRole::class,
            parentColumns = ["id"],
            childColumns = ["user_role_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["user_role_id"]),
        Index(value = ["registration_date"]),
        Index(value = ["username"], unique = true),
        Index(value = ["email"], unique = true)
    ]
)
data class BaseUser(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val password: String,
    val last_login: Long?, // хранить как timestamp
    val is_superuser: Boolean,
    val registration_date: Long, // timestamp
    val name: String?,
    val surname: String?,
    val username: String?,
    val email: String,
    val phone: String?,
    val last_login_date: Long, // timestamp
    val is_active: Boolean,
    val is_admin: Boolean,
    val user_bot_pass: String?,
    @ColumnInfo(name = "user_role_id") val userRoleId: Long,
    val lifes: Int?,
    val score: Int?,
    val last_life_update: Long, // timestamp
    val chat_id: Long?,
    val telegram_username: String?,
    val user_bot_id: Long?,

    @ColumnInfo(name = "avatar_path")
    val avatarPath: String? = null,

    // ⬇️ STREAK (shock mode)
    @ColumnInfo(name = "shockmod_begin")
    val shockmodBegin: Long? = null,   // дата начала серии

    @ColumnInfo(name = "shockmod_now")
    val shockmodNow: Long? = null,     // дата последнего выполнения

    @ColumnInfo(name = "shockmod_long")
    val shockmodLong: Int = 0          // длина серии (дни подряд)
)

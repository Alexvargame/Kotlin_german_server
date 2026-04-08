package com.example.german_server.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.ColumnInfo
import androidx.room.Index

@Entity(
    tableName = "daily_quests",
    foreignKeys = [
        ForeignKey(
            entity = BaseUser::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["userId", "date"], name = "idx_daily_quests_user_date")
    ]
)
data class DailyQuestEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val userId: Long,

    val questTitle: String,
    val conditionType: String,
    val target: Int,

    @ColumnInfo(defaultValue = "0")
    var progress: Int = 0,

    @ColumnInfo(defaultValue = "0")
    var isCompleted: Boolean = false,

    val rewardScore: Int,
    val rewardCoins: Int,

    val date: String
)

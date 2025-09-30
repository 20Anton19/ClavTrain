package com.example.clavtrain.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ExerciseStatistic(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val userId: String, //!!!!Новый столбец
    val exerciseId: Int,
    val mistakes: Int,
    val timeSpent: Long,
    val avgTime: Long,
    val isSuccessful: Boolean,
    val completedAt: Long
)
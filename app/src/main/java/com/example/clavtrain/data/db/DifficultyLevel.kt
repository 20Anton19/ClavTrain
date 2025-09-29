package com.example.clavtrain.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DifficultyLevel(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val maxSymbols: Int,
    val maxMistakes: Int,
    val maxPressTime: Long
)

package com.example.clavtrain.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Exercise(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val difficultyId: Int,
    val createdAt: Long,
    val text: String
)
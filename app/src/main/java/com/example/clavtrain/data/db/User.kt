package com.example.clavtrain.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

data class User(
    @PrimaryKey(autoGenerate = true)
    val id: String? = null,
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val email: String,
    val password: String,
)

package com.example.clavtrain.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Upsert

@Dao
interface Dao {
    @Upsert()
    suspend fun insertExercise(exercise: Exercise)

    @Delete()
    suspend fun deleteExercise(exercise: Exercise)

    @Upsert()
    suspend fun insertExerciseStatistic(exerciseStatistic: ExerciseStatistic)
}
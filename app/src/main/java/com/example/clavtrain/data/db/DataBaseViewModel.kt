package com.example.clavtrain.data.db

import androidx.lifecycle.ViewModel

class DataBaseViewModel(
    private val database: DataBase
): ViewModel() {
    private val dao = database.dao

    suspend fun insertExercise(exercise: Exercise) {
        dao.insertExercise(exercise)
    }

    suspend fun deleteExercise(exercise: Exercise) {
        dao.deleteExercise(exercise)
    }

    suspend fun insertExerciseStatistic(exerciseStatistic: ExerciseStatistic) {
        dao.insertExerciseStatistic(exerciseStatistic)
    }
}
package com.example.clavtrain.data.db

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DataBaseViewModel(
    private val database: DataBase
): ViewModel() {
    private val dao = database.dao

    init {
        loadAllDifficultyLevels()
    }
    private val _difficultyLevels = MutableStateFlow<List<DifficultyLevel>>(emptyList())
    val difficultyLevels: StateFlow<List<DifficultyLevel>> = _difficultyLevels.asStateFlow()

    suspend fun insertExercise(exercise: Exercise) {
        dao.insertExercise(exercise)
    }

    suspend fun deleteExercise(exercise: Exercise) {
        dao.deleteExercise(exercise)
    }

    suspend fun insertExerciseStatistic(exerciseStatistic: ExerciseStatistic) {
        dao.insertExerciseStatistic(exerciseStatistic)
    }

    suspend fun insertDifficultyLevel(difficultyLevel: DifficultyLevel) {
        dao.insertDifficultyLevel(difficultyLevel)
    }

    fun loadAllDifficultyLevels() {
        viewModelScope.launch {
            dao.getAllDifficultyLevels().collect { levels ->
                _difficultyLevels.value = levels
            }
        }
    }

    fun getExercisesByDifficultyId(difficultyId: Int): Flow<List<Exercise>> {
        return dao.getExercisesByDifficultyId(difficultyId)
    }

    fun getExerciseById(exerciseId: Int): Flow<Exercise?> {
        return dao.getExerciseById(exerciseId)
    }
}
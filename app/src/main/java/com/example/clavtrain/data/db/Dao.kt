package com.example.clavtrain.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Upsert()
    suspend fun insertExercise(exercise: Exercise)

    @Delete()
    suspend fun deleteExercise(exercise: Exercise)

    @Upsert()
    suspend fun insertDifficultyLevel(difficultyLevel: DifficultyLevel)

    //Получения всех уровней сложности по id
    @Query("SELECT * FROM DifficultyLevel ORDER BY id")
    fun getAllDifficultyLevels(): Flow<List<DifficultyLevel>>

    //Получение уровня сложности по id !Пока не понял зачем надо, мб убрать!
    @Query("SELECT * FROM DifficultyLevel WHERE id = :id")
    fun getDifficultyLevelById(id: Int): Flow<DifficultyLevel?>

    //Получение всех упражнений по уровню сложности
    @Query("SELECT * FROM Exercise WHERE difficultyId = :difficultyId")
    fun getExercisesByDifficultyId(difficultyId: Int): Flow<List<Exercise>>

    //Получение упражения по id
    @Query("SELECT * FROM Exercise WHERE id = :exerciseId")
    fun getExerciseById(exerciseId: Int): Flow<Exercise?>

    @Update
    suspend fun updateDifficultyLevel(difficultyLevel: DifficultyLevel)


    //СТАТИСТИКА
    @Upsert()
    suspend fun insertExerciseStatistic(exerciseStatistic: ExerciseStatistic)

    //Получения всех статистик по id упражнения
    @Query("SELECT * FROM ExerciseStatistic WHERE exerciseId = :exerciseId ORDER BY completedAt DESC")
    fun getStatisticsByExerciseId(exerciseId: Int): Flow<List<ExerciseStatistic>>


}


package com.example.clavtrain.ui.admin

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.clavtrain.data.db.DifficultyLevel
import com.example.clavtrain.data.db.Exercise
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ServerViewModel: ViewModel() {
    private val db = Firebase.firestore

    private val _allExercises = MutableStateFlow<List<Exercise>>(emptyList())
    val allExercises: StateFlow<List<Exercise>> = _allExercises.asStateFlow()
    private val _difficultyLevels = MutableStateFlow<List<DifficultyLevel>>(emptyList())
    val difficultyLevels: StateFlow<List<DifficultyLevel>> = _difficultyLevels.asStateFlow()

    private val _updateState = MutableStateFlow<UpdateState>(UpdateState.Idle)
    val updateState: StateFlow<UpdateState> = _updateState.asStateFlow()

    init {
        loadDifficultyLevels()
        loadAllExercises()
//        insertInitialExercises()
    }

    private fun loadDifficultyLevels() {
        db.collection("difficulty_levels")
            .orderBy("id")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val levels = mutableListOf<DifficultyLevel>()

                for (document in querySnapshot) {
                    try {
                        val data = document.data
                        val level = DifficultyLevel(
                            id = (data["id"] as? Long)?.toInt(),
                            name = data["name"] as? String ?: "",
                            maxSymbols = (data["maxSymbols"] as? Long)?.toInt() ?: 0,
                            maxMistakes = (data["maxMistakes"] as? Long)?.toInt() ?: 0,
                            maxPressTime = (data["maxPressTime"] as? Long) ?: 0
                        )
                        levels.add(level)
                    } catch (e: Exception) {
                        Log.e("ServerViewModel", "Error parsing document ${document.id}", e)
                    }
                }

                _difficultyLevels.value = levels
                Log.d("ServerViewModel", "Successfully loaded ${levels.size} difficulty levels")
            }
            .addOnFailureListener { exception ->
                Log.e("ServerViewModel", "Error loading difficulty levels", exception)
            }
    }

    fun updateDifficultyLevel(updatedLevel: DifficultyLevel) {
        val levelId = updatedLevel.id
        if (levelId == null) {
            _updateState.value = UpdateState.Error("Ошибка: ID уровня не найден")
            return
        }

        _updateState.value = UpdateState.Loading

        db.collection("difficulty_levels")
            .document(levelId.toString())
            .update(
                mapOf(
                    "maxSymbols" to updatedLevel.maxSymbols,
                    "maxMistakes" to updatedLevel.maxMistakes,
                    "maxPressTime" to updatedLevel.maxPressTime
                )
            )
            .addOnSuccessListener {
                _updateState.value = UpdateState.Success
                // Обновляем локальный список
                loadDifficultyLevels()
            }
            .addOnFailureListener { e ->
                _updateState.value = UpdateState.Error("Ошибка сохранения: ${e.message}")
            }
    }

    fun clearUpdateState() {
        _updateState.value = UpdateState.Idle
    }

    private fun loadAllExercises() {
        Log.e("ServerViewModel", "Зашел в loadAllExercises")
        db.collection("exercises")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val exercises = mutableListOf<Exercise>()
                for (document in querySnapshot) {
                    try {
                        Log.e("ServerViewModel", "Зашел в try")
                        val data = document.data
                        val exercise = Exercise(
                            id = (data["id"] as? Long)?.toInt(),
                            name = data["name"] as? String ?: "",
                            difficultyId = (data["difficultyId"] as? Long)?.toInt() ?: 0,
                            createdAt = (data["createdAt"] as? Long) ?: 0L,
                            text = data["text"] as? String ?: ""
                        )
                        exercises.add(exercise)
                    } catch (e: Exception) {
                        Log.e("ServerViewModel", "Error parsing exercise ${document.id}", e)
                    }
                }
                _allExercises.value = exercises
            }
            .addOnFailureListener { e ->
                Log.e("ServerViewModel", "Error loading exercises", e)
            }
    }

    fun deleteExercise(exerciseId: Int) {
        db.collection("exercises")
            .document(exerciseId.toString())
            .delete()
            .addOnSuccessListener {
                Log.d("ServerViewModel", "Exercise $exerciseId deleted")
                // Обновляем список
                loadAllExercises()
            }
            .addOnFailureListener { e ->
                Log.e("ServerViewModel", "Error deleting exercise $exerciseId", e)
            }
    }

//    fun insertInitialExercises() {
//        val currentTime = System.currentTimeMillis()
//
//        val exercises = listOf(
//            mapOf(
//                "id" to 1,
//                "name" to "Мое упражнение 1",
//                "difficultyId" to 1,
//                "createdAt" to currentTime,
//                "text" to "фбплвьмдывлкдлывадлсьмыжв"
//            ),
//            mapOf(
//                "id" to 2,
//                "name" to "Мое упражнение 2",
//                "difficultyId" to 1,
//                "createdAt" to currentTime,
//                "text" to "важдпрлывапдлтвпморэщшфоьхжз"
//            ),
//            mapOf(
//                "id" to 3,
//                "name" to "Мое упражнение 3",
//                "difficultyId" to 2,
//                "createdAt" to currentTime,
//                "text" to "яывпждлэпиджявмждлэоцуадлкпжфдл"
//            ),
//            mapOf(
//                "id" to 4,
//                "name" to "Мое упражнение 4",
//                "difficultyId" to 2,
//                "createdAt" to currentTime,
//                "text" to "япаяэмыэжадлаилявмывлмыаыждлаыжпдл"
//            ),
//            mapOf(
//                "id" to 5,
//                "name" to "Мое упражнение 5",
//                "difficultyId" to 3,
//                "createdAt" to currentTime,
//                "text" to "явдлапмоядмьыявдалмьяэджм"
//            ),
//            mapOf(
//                "id" to 6,
//                "name" to "Мое упражнение 6",
//                "difficultyId" to 3,
//                "createdAt" to currentTime,
//                "text" to "ядавлпояфжэалдывждлыфэждалыждалфажыа"
//            ),
//            mapOf(
//                "id" to 7,
//                "name" to "Мое упражнение 7",
//                "difficultyId" to 4,
//                "createdAt" to currentTime,
//                "text" to "фывждаплоэавждфыьажыфдлопваэждавоажыфдлопва"
//            ),
//            mapOf(
//                "id" to 8,
//                "name" to "Мое упражнение 8",
//                "difficultyId" to 4,
//                "createdAt" to currentTime,
//                "text" to "фывжапдлфэждпамоваэдлпофыэзпадфлофывафаажыфдлопва"
//            ),
//            mapOf(
//                "id" to 9,
//                "name" to "Мое упражнение 9",
//                "difficultyId" to 5,
//                "createdAt" to currentTime,
//                "text" to "фаыфждалваэдлыфоажволфыдвлпеоваэдалыажэдвыалофэжадлфдлж"
//            ),
//            mapOf(
//                "id" to 10,
//                "name" to "Упражнение Миши Осипова",
//                "difficultyId" to 5,
//                "createdAt" to currentTime,
//                "text" to "флдыжаопэдржлфыаэдлвапдэыфловапжрлыфважлвапжыуфдлвэждплыажэдл"
//            )
//        )
//
//        exercises.forEach { exercise ->
//            db.collection("exercises")
//                .document(exercise["id"].toString())
//                .set(exercise)
//                .addOnSuccessListener {
//                    Log.d("ServerViewModel", "Exercise ${exercise["name"]} added")
//                }
//                .addOnFailureListener { e ->
//                    Log.e("ServerViewModel", "Error adding exercise ${exercise["name"]}", e)
//                }
//        }
//    }

    sealed class UpdateState {
        object Idle : UpdateState()
        object Loading : UpdateState()
        object Success : UpdateState()
        data class Error(val message: String) : UpdateState()
    }
}
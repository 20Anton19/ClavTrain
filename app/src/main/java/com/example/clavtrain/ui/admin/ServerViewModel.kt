package com.example.clavtrain.ui.admin

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.clavtrain.data.db.DifficultyLevel
import com.example.clavtrain.data.db.Exercise
import com.example.clavtrain.data.db.User
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow

class ServerViewModel: ViewModel() {
    private val db = Firebase.firestore

    private val _allExercises = MutableStateFlow<List<Exercise>>(emptyList())
    val allExercises: StateFlow<List<Exercise>> = _allExercises.asStateFlow()
    private val _difficultyLevels = MutableStateFlow<List<DifficultyLevel>>(emptyList())
    val difficultyLevels: StateFlow<List<DifficultyLevel>> = _difficultyLevels.asStateFlow()

    private val _updateState = MutableStateFlow<UpdateState>(UpdateState.Idle)
    val updateState: StateFlow<UpdateState> = _updateState.asStateFlow()

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users.asStateFlow()

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

    fun updateExercise(exercise: Exercise) {
        val exerciseId = exercise.id
        if (exerciseId == null) {
            // Создание нового упражнения
            val newId = (allExercises.value.maxByOrNull { it.id ?: 0 }?.id ?: 0) + 1
            val newExercise = exercise.copy(id = newId)

            db.collection("exercises")
                .document(newId.toString())
                .set(mapOf(
                    "id" to newExercise.id,
                    "name" to newExercise.name,
                    "difficultyId" to newExercise.difficultyId,
                    "createdAt" to newExercise.createdAt,
                    "text" to newExercise.text
                ))
                .addOnSuccessListener {
                    Log.d("ServerViewModel", "Exercise created successfully")
                    loadAllExercises()
                }
                .addOnFailureListener { e ->
                    Log.e("ServerViewModel", "Error creating exercise", e)
                }
        } else {
            // Обновление существующего упражнения
            db.collection("exercises")
                .document(exerciseId.toString())
                .update(
                    mapOf(
                        "name" to exercise.name,
                        "difficultyId" to exercise.difficultyId,
                        "text" to exercise.text
                    )
                )
                .addOnSuccessListener {
                    Log.d("ServerViewModel", "Exercise $exerciseId updated successfully")
                    loadAllExercises()
                }
                .addOnFailureListener { e ->
                    Log.e("ServerViewModel", "Error updating exercise $exerciseId", e)
                }
        }
    }

    fun loadAllUsers() {
        db.collection("users")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val users = mutableListOf<User>()
                for (document in querySnapshot) {
                    try {
                        val data = document.data
                        val user = User(
                            id = data["userId"] as? String ?: "",
                            email = data["email"] as? String ?: "",
                            password = data["password"] as? String ?: "",
                            firstName = data["firstName"] as? String ?: "",
                            middleName = data["middleName"] as? String ?: "",
                            lastName = data["lastName"] as? String ?: ""
                        )
                        users.add(user)
                    } catch (e: Exception) {
                        Log.e("ServerViewModel", "Error parsing user ${document.id}", e)
                    }
                }
                _users.value = users
            }
            .addOnFailureListener { e ->
                Log.e("ServerViewModel", "Error loading users", e)
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
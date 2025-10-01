package com.example.clavtrain.ui.admin

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.clavtrain.data.db.DifficultyLevel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ServerViewModel: ViewModel() {
    private val db = Firebase.firestore

    private val _difficultyLevels = MutableStateFlow<List<DifficultyLevel>>(emptyList())
    val difficultyLevels: StateFlow<List<DifficultyLevel>> = _difficultyLevels.asStateFlow()

    private val _updateState = MutableStateFlow<UpdateState>(UpdateState.Idle)
    val updateState: StateFlow<UpdateState> = _updateState.asStateFlow()

    init {
        loadDifficultyLevels()
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

    sealed class UpdateState {
        object Idle : UpdateState()
        object Loading : UpdateState()
        object Success : UpdateState()
        data class Error(val message: String) : UpdateState()
    }
}
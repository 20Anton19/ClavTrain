package com.example.clavtrain.data.db

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class DataBaseViewModel(
    private val database: DataBase
): ViewModel() {
    private val dao = database.dao
    private val auth = Firebase.auth

    init {
        loadCurrentUser()
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

    // Для статистики
    private val _lastStatistic = MutableStateFlow<ExerciseStatistic?>(null)
    val lastStatistic: StateFlow<ExerciseStatistic?> = _lastStatistic.asStateFlow()

    suspend fun saveStatistic(statistic: ExerciseStatistic) {
        _lastStatistic.value = statistic
        dao.insertExerciseStatistic(statistic)
    }

    fun getStatisticsByExerciseId(exerciseId: Int): Flow<List<ExerciseStatistic>> {
        return dao.getStatisticsByExerciseId(exerciseId)
    }



    //FIRESTORE
    // State для текущего пользователя
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    private fun loadCurrentUser() {
        val firebaseUser = auth.currentUser
        if (firebaseUser != null) {
            // Пользователь авторизован, загружаем его данные
            fetchUserFromFirestore(firebaseUser.uid)
        } else {
            // Можно обработать случай когда пользователь не авторизован
            Log.e("DataBaseViewModel", "No authenticated user found")
        }
    }

    private fun fetchUserFromFirestore(userId: String) {
        viewModelScope.launch {
            try {
                val userDoc = Firebase.firestore.collection("users")
                    .document(userId)
                    .get()
                    .await()
                if (userDoc.exists()) {
                    val data = userDoc.data!!
                    val user = User(
                        id = userId,
                        firstName = data["firstName"] as? String ?: "",
                        middleName = data["middleName"] as? String ?: "",
                        lastName = data["lastName"] as? String ?: "",
                        email = data["email"] as? String ?: "",
                        password = data["password"] as? String ?: ""
                    )
                    _currentUser.value = user
                } else {
                    Log.e("DataBaseViewModel", "User document not found in Firestore")
                }
            } catch (e: Exception) {
                Log.e("DataBaseViewModel", "Error fetching user from Firestore", e)
            }
        }
    }

    suspend fun saveStatisticToFirebase(statistic: ExerciseStatistic) {
        Log.d("FirestoreSync", "Вызываю")
        syncStatisticToFirestore(statistic)
        Log.d("FirestoreSync", "Выполнилась")
    }

    private suspend fun syncStatisticToFirestore(statistic: ExerciseStatistic) {
        try {
            Log.d("FirestoreSync", "Saving to local DB")
            val statisticMap = mapOf(
                "exerciseId" to statistic.exerciseId,
                "userId" to statistic.userId,
                "mistakes" to statistic.mistakes,
                "timeSpent" to statistic.timeSpent,
                "avgTime" to statistic.avgTime,
                "isSuccessful" to statistic.isSuccessful,
                "completedAt" to statistic.completedAt,
                "localId" to statistic.id, // сохраняем локальный ID на всякий случай
                "syncedAt" to System.currentTimeMillis()
            )
            Log.d("FirestoreSync", "Syncing to Firestore")
            val documentId = "${statistic.userId}_${statistic.exerciseId}_${statistic.completedAt}"

            Firebase.firestore.collection("statistics")
                .document(documentId)
                .set(statisticMap)
                .await()
            Log.d("FirestoreSync", "Statistic synced successfully for user: ${statistic.userId}")
        } catch (e: Exception) {
            Log.e("FirestoreSync", "Error syncing statistic", e)
            // Можно добавить в очередь для повторной попытки
        }
    }

    suspend fun updateUserProfile(
        firstName: String,
        middleName: String,
        lastName: String,
        email: String
    ): Boolean {
        val currentUser = _currentUser.value
        val userId = currentUser?.id ?: return false // ← проверяем что id не null

        try {
            // Обновляем в Firestore
            val updates = mapOf(
                "firstName" to firstName,
                "middleName" to middleName,
                "lastName" to lastName,
                "email" to email
            )

            Firebase.firestore.collection("users")
                .document(userId)
                .update(updates)
                .await()

            // ОБНОВЛЯЕМ локальный currentUser
            val updatedUser = currentUser.copy(
                firstName = firstName,
                middleName = middleName,
                lastName = lastName,
                email = email
            )
            _currentUser.value = updatedUser

            Log.d("UserProfile", "User profile updated successfully")
            return true

        } catch (e: Exception) {
            Log.e("UserProfile", "Error updating user profile", e)
            return false
        }
    }

    suspend fun isEmailUniqueForCurrentUser(email: String): Boolean {
        val currentUser = _currentUser.value ?: return true

        return try {
            val query = Firebase.firestore.collection("users")
                .whereEqualTo("email", email.trim().lowercase())
                .get()
                .await()

            // Email уникальный если:
            // 1. Нет пользователей с таким email ИЛИ
            // 2. Есть пользователь с таким email, но это текущий пользователь
            query.isEmpty || query.documents.any { it.id == currentUser.id }
        } catch (e: Exception) {
            Log.e("EmailCheck", "Error checking email uniqueness", e)
            false
        }
    }

    suspend fun updatePassword(
        currentPassword: String,
        newPassword: String
    ): Boolean {
        val currentUser = _currentUser.value
        val userId = currentUser?.id ?: return false // ← проверяем что id не null
        Log.d("PasswordUpdate", "Текущий пароль ${currentUser.password}")
        try {
            // 1. Проверяем текущий пароль (сравниваем с тем что в Firestore)
            if (currentUser.password != currentPassword) {
                throw Exception("Неверный текущий пароль")
            }

            // 2. Проверяем что новый пароль не совпадает со старым
            if (currentPassword == newPassword) {
                throw Exception("Новый пароль должен отличаться от старого")
            }

            // 3. Проверяем длину пароля
            if (newPassword.length < 6) {
                throw Exception("Пароль должен содержать минимум 6 символов")
            }

            // 4. Обновляем пароль ТОЛЬКО в Firestore
            Firebase.firestore.collection("users")
                .document(currentUser.id)
                .update("password", newPassword)
                .await()

            // 5. Обновляем локального пользователя
            val updatedUser = currentUser.copy(password = newPassword)
            _currentUser.value = updatedUser

            Log.d("PasswordUpdate", "Password updated successfully in Firestore")
            return true

        } catch (e: Exception) {
            Log.e("PasswordUpdate", "Error updating password", e)
            throw e // Пробрасываем ошибку чтобы показать в UI
        }
    }
}
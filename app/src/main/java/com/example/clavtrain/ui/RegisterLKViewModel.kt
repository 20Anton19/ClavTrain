package com.example.clavtrain.ui

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RegisterLKViewModel: ViewModel() {
    private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val registerState: StateFlow<RegisterState> = _registerState.asStateFlow()

    val auth = Firebase.auth
    val db = Firebase.firestore

    fun setError(message: String) {
        _registerState.value = RegisterState.Error(message)
    }
    fun signUp(
        email: String,
        password: String,
        firstName: String,
        middleName: String,
        lastName: String
    ) {
        Log.d("MyRagisterCheck", "Попал в signUp")
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    val userId = auth.currentUser?.uid
                    if (userId != null) {
                        addUserToFirestore(userId, email, password, firstName, middleName, lastName)
                    } else {
                        _registerState.value = RegisterState.Error("Не удалось получить ID пользователя")
                    }
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    val errorMessage = when (task.exception) {
                        is FirebaseAuthWeakPasswordException -> "Пароль слишком слабый. Используйте от 4 до 10 символов"
                        is FirebaseAuthInvalidCredentialsException -> {
                            when {
                                task.exception?.message?.contains("email") == true -> "Неверный формат email"
                                else -> "Неверные учетные данные"
                            }
                        }
                        is FirebaseAuthUserCollisionException -> "Пользователь с таким email уже существует"
                        is FirebaseAuthEmailException -> "Неверный формат email"
                        is FirebaseNetworkException -> "Ошибка сети. Проверьте подключение к интернету"
                        else -> task.exception?.message ?: "Неизвестная ошибка при регистрации"
                    }
                    _registerState.value = RegisterState.Error(errorMessage)
                }
            }
    }

    private fun addUserToFirestore(
        userId: String,
        email: String,
        password: String,
        firstName: String,
        middleName: String,
        lastName: String
    ) {
        val user = hashMapOf(
            "firstName" to firstName,
            "middleName" to middleName,
            "lastName" to lastName,
            "email" to email,
            "password" to password,
            "createdAt" to System.currentTimeMillis(),
            "userId" to userId
        )

        db.collection("users")
            .document(userId) // Используем UID как ID документа
            .set(user)
            .addOnSuccessListener {
                Log.d(TAG, "User added to Firestore")
                _registerState.value = RegisterState.Success
            }
            .addOnFailureListener { e ->
                // Если не удалось добавить в Firestore, удаляем пользователя из Auth
                auth.currentUser?.delete()?.addOnCompleteListener {
                    _registerState.value =
                        RegisterState.Error("Ошибка сохранения данных пользователя: ${e.message}")
                }
            }
    }

    sealed class RegisterState {
        object Idle : RegisterState()
        object Loading : RegisterState()
        object Success : RegisterState()
        data class Error(val message: String) : RegisterState()
    }

    companion object {
        private const val TAG = "RegisterLKViewModel"
    }
}
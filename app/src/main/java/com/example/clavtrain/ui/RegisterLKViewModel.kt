package com.example.clavtrain.ui

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
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
                        // Добавляем пользователя в Firestore
                        addUserToFirestore(userId, email, password, firstName, middleName, lastName)
                    } else {
                        _registerState.value = RegisterState.Error("Не удалось получить ID пользователя")
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    _registerState.value = RegisterState.Error(task.exception?.message ?: "Unknown error")
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
                Log.w(TAG, "Error adding user to Firestore", e)
                _registerState.value = RegisterState.Error("Ошибка сохранения данных пользователя")
            }
    }

    sealed class RegisterState {
        object Idle : RegisterState()
        object Loading : RegisterState()
        object Success : RegisterState()
        data class Error(val message: String) : RegisterState()
    }
}
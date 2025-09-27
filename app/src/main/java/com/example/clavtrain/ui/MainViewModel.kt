package com.example.clavtrain.ui

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.clavtrain.data.UserRole
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel: ViewModel() {
    private val _userRole = MutableStateFlow<UserRole?>(null)
    var userRole: StateFlow<UserRole?> = _userRole.asStateFlow()

    private val auth = Firebase.auth
    private val db = Firebase.firestore

    fun checkUserRole() {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            Log.d("MyLog", "Пользователь не авторизован")
            _userRole.value = null
            return
        }

        Log.d("MyLog", "Проверка роли для: ${currentUser.email} (${currentUser.uid})")

        val uid = currentUser.uid

        db.collection("admin")
            .document(uid)
            .get()
            .addOnSuccessListener {
                val isAdmin = it.exists()
                Log.d("MyLog", "Документ существует - пользователь АДМИН: $isAdmin")

                _userRole.value = if (isAdmin) UserRole.ADMIN else UserRole.USER
            }
            .addOnFailureListener {
                Log.d("MyLog", "Не получилось получить данные, какая-то ошибка")
            }
    }
}
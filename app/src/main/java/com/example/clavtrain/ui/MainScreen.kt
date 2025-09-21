package com.example.clavtrain.ui

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.clavtrain.data.UserRole
import com.example.clavtrain.navigation.AdminNav
import com.example.clavtrain.navigation.UserNav
import com.example.clavtrain.ui.theme.ClavTrainTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

@Composable
fun MainScreen(
    onLoginClick: () -> Unit,
    onRoleDetermined: (UserRole?) -> Unit  // ← Колбэк для определения роли
) {
    var userRole by remember { mutableStateOf<UserRole?>(null) }

    LaunchedEffect(Unit) {
        checkUserRole { role ->
            userRole = role
            onRoleDetermined(role)  // ← Сообщаем наружу
        }
    }

    /*
    when (userRole) {
        UserRole.ADMIN -> AdminNav()
        UserRole.USER -> UserNav()
        null ->  {
            Log.d("MyLog", "role is null")
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Клавиатурный тренажёр")
                Button(
                    modifier = Modifier.padding(top = 16.dp),
                    onClick = onLoginClick
                ) {
                    Text("Войти")
                }
            }
        }
    }
*/
    if (userRole == null) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Клавиатурный тренажёр")
            Button(
                modifier = Modifier.padding(top = 16.dp),
                onClick = onLoginClick
            ) {
                Text("Войти")
            }
        }
    } else {
        // Пустое место - скоро переключимся на другой NavHost
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }


}

private fun checkUserRole(onResult: (UserRole?) -> Unit) {
    val auth = Firebase.auth
    val currentUser = auth.currentUser

    if (currentUser == null) {
        Log.d("MyLog", "Пользователь не авторизован")
        onResult(null)
        return
    }

    Log.d("MyLog", "Проверка роли для: ${currentUser.email} (${currentUser.uid})")

    val db = Firebase.firestore
    val uid = currentUser.uid

    db.collection("admin")
        .document(uid)
        .get()
        .addOnSuccessListener {
            val isAdmin = it.exists()
            Log.d("MyLog", "Документ существует - пользователь АДМИН: $isAdmin")

            onResult(if (isAdmin) UserRole.ADMIN else UserRole.USER)
        }
        .addOnFailureListener {
            Log.d("MyLog", "Не получилось получить данные, какая-то ошибка")
        }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    ClavTrainTheme { MainScreen(onLoginClick = {}, onRoleDetermined= {})}
} 
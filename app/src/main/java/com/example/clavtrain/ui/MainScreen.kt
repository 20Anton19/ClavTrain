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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.clavtrain.data.UserRole
import com.example.clavtrain.data.db.DataBaseViewModel
import com.example.clavtrain.data.db.DifficultyLevel
import com.example.clavtrain.data.db.Exercise
import com.example.clavtrain.navigation.AdminNav
import com.example.clavtrain.navigation.UserNav
import com.example.clavtrain.ui.theme.ClavTrainTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(
    onLoginClick: () -> Unit,
    onRoleDetermined: (UserRole?) -> Unit,  // ← Колбэк для определения роли
    viewModel: MainViewModel = koinViewModel(),
    dataBaseViewModel: DataBaseViewModel = koinViewModel()
) {
//    val currentTime = System.currentTimeMillis()
//    LaunchedEffect(Unit) {
//        dataBaseViewModel.insertDifficultyLevel(DifficultyLevel(1,"1", 1,1,1))
//        dataBaseViewModel.insertDifficultyLevel(DifficultyLevel(2,"2", 2,2,2))
//        dataBaseViewModel.insertDifficultyLevel(DifficultyLevel(3,"3", 3,3,3))
//        dataBaseViewModel.insertDifficultyLevel(DifficultyLevel(4,"4", 4,4,4))
//        dataBaseViewModel.insertDifficultyLevel(DifficultyLevel(5,"5", 5,5,5))
//
//    // Добавляем по 2 упражнения на каждый уровень сложности
//    dataBaseViewModel.insertExercise(Exercise(
//        name = "Упражнение 1-1",
//        difficultyId = 1,
//        createdAt = currentTime,
//        text = "Текст упражнения 1-1"
//    ))
//    dataBaseViewModel.insertExercise(Exercise(
//        name = "Упражнение 1-2",
//        difficultyId = 1,
//        createdAt = currentTime,
//        text = "Текст упражнения 1-2"
//    ))
//
//    dataBaseViewModel.insertExercise(Exercise(
//        name = "Упражнение 2-1",
//        difficultyId = 2,
//        createdAt = currentTime,
//        text = "Текст упражнения 2-1"
//    ))
//    dataBaseViewModel.insertExercise(Exercise(
//        name = "Упражнение 2-2",
//        difficultyId = 2,
//        createdAt = currentTime,
//        text = "Текст упражнения 2-2"
//    ))
//
//    dataBaseViewModel.insertExercise(Exercise(
//        name = "Упражнение 3-1",
//        difficultyId = 3,
//        createdAt = currentTime,
//        text = "Текст упражнения 3-1"
//    ))
//    dataBaseViewModel.insertExercise(Exercise(
//        name = "Упражнение 3-2",
//        difficultyId = 3,
//        createdAt = currentTime,
//        text = "Текст упражнения 3-2"
//    ))
//
//    dataBaseViewModel.insertExercise(Exercise(
//        name = "Упражнение 4-1",
//        difficultyId = 4,
//        createdAt = currentTime,
//        text = "Текст упражнения 4-1"
//    ))
//    dataBaseViewModel.insertExercise(Exercise(
//        name = "Упражнение 4-2",
//        difficultyId = 4,
//        createdAt = currentTime,
//        text = "Текст упражнения 4-2"
//    ))
//
//    dataBaseViewModel.insertExercise(Exercise(
//        name = "Упражнение 5-1",
//        difficultyId = 5,
//        createdAt = currentTime,
//        text = "Текст упражнения 5-1"
//    ))
//    dataBaseViewModel.insertExercise(Exercise(
//        name = "Упражнение 5-2",
//        difficultyId = 5,
//        createdAt = currentTime,
//        text = "Текст упражнения 5-2"
//        )
//    ) }

    val userRole by viewModel.userRole.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.checkUserRole()
    }

    LaunchedEffect(userRole) {
        onRoleDetermined(userRole)
    }
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

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    ClavTrainTheme { MainScreen(onLoginClick = {}, onRoleDetermined= {})}
} 
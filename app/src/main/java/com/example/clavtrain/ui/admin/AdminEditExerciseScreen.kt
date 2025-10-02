package com.example.clavtrain.ui.admin

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.androidx.compose.koinViewModel



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clavtrain.data.db.Exercise
import com.example.clavtrain.ui.theme.ClavTrainTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminEditExerciseScreen(
    exerciseId: Int,
    onBackClick: () -> Unit,
    serverViewModel: ServerViewModel = koinViewModel()
) {
    val levels by serverViewModel.difficultyLevels.collectAsState(initial = emptyList())
    val exercises by serverViewModel.allExercises.collectAsState(initial = emptyList())

    val currentExercise = remember(exercises, exerciseId) {
        exercises.find { it.id == exerciseId }
    }

    var selectedLevel by remember(currentExercise) {
        mutableStateOf(currentExercise?.difficultyId?.toString() ?: "")
    }
    var exerciseName by remember(currentExercise) {
        mutableStateOf(currentExercise?.name ?: "")
    }
    var symbolCount by remember { mutableStateOf("") } // Только для генерации
    var exerciseText by remember(currentExercise) {
        mutableStateOf(currentExercise?.text ?: "")
    }

    // Валидация полей
    val selectedDifficulty = levels.find { it.id.toString() == selectedLevel }
    val maxSymbolsForLevel = selectedDifficulty?.maxSymbols ?: 0
    val minSymbolsForLevel = 50 // ← ДОБАВИТЬ: минимальное количество символов

    val exerciseNameError = remember(exerciseName) {
        when {
            exerciseName.isEmpty() -> "Название обязательно"
            exerciseName.length > 30 -> "Максимум 30 символов"
            else -> null
        }
    }

    val symbolCountError = remember(symbolCount, maxSymbolsForLevel) {
        when {
            symbolCount.isNotEmpty() && symbolCount.toIntOrNull() == null -> "Должно быть числом"
            // ↓ ИЗМЕНИТЬ: добавить проверку на минимум 50 символов
            symbolCount.isNotEmpty() && symbolCount.toInt() !in minSymbolsForLevel..maxSymbolsForLevel -> "Должно быть от $minSymbolsForLevel до $maxSymbolsForLevel"
            else -> null
        }
    }

    val exerciseTextError = remember(exerciseText) {
        when {
            exerciseText.isEmpty() -> "Текст упражнения обязателен"
            !exerciseText.matches(Regex("^[а-яёА-ЯЁ\\s]*$")) -> "Только русские буквы и пробелы"
            // ↓ ИЗМЕНИТЬ: добавить проверку на минимум 50 символов
            exerciseText.length < minSymbolsForLevel -> "Минимум $minSymbolsForLevel символов"
            exerciseText.length > maxSymbolsForLevel -> "Максимум $maxSymbolsForLevel символов для выбранного уровня"
            else -> null
        }
    }

    val hasErrors = (exerciseNameError != null) ||
            (symbolCount.isNotEmpty() && symbolCountError != null) ||
            (exerciseTextError != null)
    val hasEmptyFields = exerciseName.isEmpty() || exerciseText.isEmpty()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Настройка упражнения",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            textAlign = TextAlign.Center,
            fontSize = 24.sp
        )

        // Показываем ошибки валидации
        if (hasErrors) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Red.copy(alpha = 0.1f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    listOfNotNull(exerciseNameError, symbolCountError, exerciseTextError).forEach { error ->
                        Text(
                            text = "• $error",
                            color = Color.Red,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }

        // Выпадающий список с уровнями сложности
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp)
        ) {
            Text(
                text = "Уровень сложности:",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(4.dp))

            var expanded by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                TextField(
                    value = levels.find { it.id.toString() == selectedLevel }?.name ?: "",
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    levels.forEach { level ->
                        DropdownMenuItem(
                            text = { Text("${level.name} (${minSymbolsForLevel}-${level.maxSymbols} симв.)") },
                                onClick = {
                                selectedLevel = level.id.toString()
                                expanded = false
                            }
                        )
                    }
                }
            }
        }

        // Поле названия упражнения
        ClearableTextField(
            value = exerciseName,
            onValueChange = { if (it.length <= 30) exerciseName = it },
            label = "Название упражнения:",
        )

        // Поле количества символов (только для генерации)
        ClearableTextField(
            value = symbolCount,
            onValueChange = { symbolCount = it },
            label = "Количество символов для генерации ($minSymbolsForLevel-$maxSymbolsForLevel):",
        )

        // Кнопка сгенерировать
        Button(
            onClick = {
                val count = symbolCount.toIntOrNull() ?: 0
                if (count in minSymbolsForLevel..maxSymbolsForLevel) {
                    exerciseText = generateRandomText(count)
                }
            },
            modifier = Modifier
                .width(250.dp)
                .padding(vertical = 8.dp),
            enabled = symbolCount.isNotEmpty() && symbolCountError == null
        ) {
            Text("Сгенерировать текст")
        }

        // Большое текстовое поле для текста упражнения
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp)
        ) {
            TextField(
                value = exerciseText,
                onValueChange = {
                    if (it.length <= maxSymbolsForLevel && it.matches(Regex("^[а-яёА-ЯЁ\\s]*$"))) {
                        exerciseText = it
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                placeholder = { Text("Введите текст упражнения...") },
                singleLine = false,
                maxLines = 10,
                isError = exerciseTextError != null && exerciseText.isNotEmpty()
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                val updatedExercise = currentExercise?.copy(
                    name = exerciseName,
                    difficultyId = selectedLevel.toIntOrNull() ?: 1,
                    text = exerciseText
                ) ?: Exercise(
                    name = exerciseName,
                    difficultyId = selectedLevel.toIntOrNull() ?: 1,
                    createdAt = System.currentTimeMillis(),
                    text = exerciseText
                )
                serverViewModel.updateExercise(updatedExercise)
                onBackClick()
            },
            modifier = Modifier
                .width(250.dp)
                .padding(vertical = 4.dp),
            enabled = !hasErrors && !hasEmptyFields
        ) {
            Text("Сохранить")
        }

        Button(
            onClick = onBackClick,
            modifier = Modifier
                .width(250.dp)
                .padding(vertical = 8.dp)
        ) {
            Text("Выйти")
        }
    }
}

// Функция для генерации случайного текста (только русские буквы)
private fun generateRandomText(length: Int): String {
    val russianChars = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя"

    return (1..length).map {
        russianChars.random()
    }.joinToString("")
}
package com.example.clavtrain.ui.admin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clavtrain.ui.theme.ClavTrainTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun AdminEditDifficultyScreen(
    levelIndex: Int,  // ← получаем индекс
    onBackClick: () -> Unit,
    serverViewModel: ServerViewModel = koinViewModel()
) {
    val levels by serverViewModel.difficultyLevels.collectAsState(initial = emptyList())
    val updateState by serverViewModel.updateState.collectAsState()

    // Безопасное получение уровня
    val currentLevel = remember(levels, levelIndex) {
        levels.getOrNull(levelIndex)
    }

    // Инициализация состояний с проверкой
    var maxSymbols by remember(currentLevel) {
        mutableStateOf(currentLevel?.maxSymbols?.toString() ?: "")
    }
    var maxMistakes by remember(currentLevel) {
        mutableStateOf(currentLevel?.maxMistakes?.toString() ?: "")
    }
    var maxPressTime by remember(currentLevel) {
        mutableStateOf(currentLevel?.maxPressTime?.toString() ?: "")
    }

    // Валидация полей
    val maxSymbolsError = remember(maxSymbols) {
        when {
            maxSymbols.isEmpty() -> "Поле обязательно"
            maxSymbols.toIntOrNull() == null -> "Должно быть числом"
            maxSymbols.toInt() !in 50..350 -> "Должно быть от 50 до 350"
            else -> null
        }
    }

    val maxMistakesError = remember(maxMistakes) {
        when {
            maxMistakes.isEmpty() -> "Поле обязательно"
            maxMistakes.toIntOrNull() == null -> "Должно быть числом"
            maxMistakes.toInt() !in 0..5 -> "Должно быть от 0 до 5"
            else -> null
        }
    }

    val maxPressTimeError = remember(maxPressTime) {
        when {
            maxPressTime.isEmpty() -> "Поле обязательно"
            maxPressTime.toLongOrNull() == null -> "Должно быть числом"
            maxPressTime.toLong() !in 300..1500 -> "Должно быть от 300 до 1500"
            else -> null
        }
    }

    val hasErrors = maxSymbolsError != null || maxMistakesError != null || maxPressTimeError != null
    val hasEmptyFields = maxSymbols.isEmpty() || maxMistakes.isEmpty() || maxPressTime.isEmpty()

    // Показываем успешное сообщение
    if (updateState is ServerViewModel.UpdateState.Success) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            onClick = { serverViewModel.clearUpdateState() },
            colors = CardDefaults.cardColors(containerColor = Color.Green.copy(alpha = 0.1f))
        ) {
            Text(
                text = "Изменения успешно сохранены!",
                modifier = Modifier.padding(16.dp),
                color = Color.Green
            )
        }
    }

    // Показываем ошибку
    if (updateState is ServerViewModel.UpdateState.Error) {
        val errorMessage = (updateState as ServerViewModel.UpdateState.Error).message
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Red.copy(alpha = 0.1f))
        ) {
            Text(
                text = errorMessage,
                modifier = Modifier.padding(16.dp),
                color = Color.Red
            )
        }
    }

    // Показываем загрузку
    if (updateState is ServerViewModel.UpdateState.Loading) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Blue.copy(alpha = 0.1f))
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Сохранение...",
                    color = Color.Blue
                )
            }
        }
    }
    if (hasEmptyFields) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Yellow.copy(alpha = 0.1f))
        ) {
            Text(
                text = "Все поля должны быть заполнены",
                modifier = Modifier.padding(16.dp),
                color = Color.DarkGray
            )
        }
    }
    // Показываем ошибки валидации
    if (hasErrors && !hasEmptyFields) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Red.copy(alpha = 0.1f))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                listOfNotNull(maxSymbolsError, maxMistakesError, maxPressTimeError).forEach { error ->
                    Text(
                        text = "• $error",
                        color = Color.Red,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }

    if(currentLevel != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Настройка уровня \n\"${currentLevel.name}\"",
                //style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                //fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 24.sp
            )
            // Поле 1
            ClearableTextField(
                value = maxSymbols,
                onValueChange = { maxSymbols = it },
                label = "Макс. кол. символов:"
            )

            // Поле 2
            ClearableTextField(
                value = maxMistakes,
                onValueChange = { maxMistakes = it },
                label = "Макс. кол. ошибок:"
            )

            // Поле 3
            ClearableTextField(
                value = maxPressTime,
                onValueChange = { maxPressTime = it },
                label = "Макс. время нажатия:"
            )

            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    if (maxSymbols.isEmpty() || maxMistakes.isEmpty() || maxPressTime.isEmpty()) {
                        return@Button
                    }

                    val updatedLevel = currentLevel.copy(
                        maxSymbols = maxSymbols.toIntOrNull() ?: currentLevel.maxSymbols,
                        maxMistakes = maxMistakes.toIntOrNull() ?: currentLevel.maxMistakes,
                        maxPressTime = maxPressTime.toLongOrNull() ?: currentLevel.maxPressTime
                    )
                    serverViewModel.updateDifficultyLevel(updatedLevel)
                },
                modifier = Modifier
                    .width(250.dp)
                    .padding(vertical = 4.dp),
                enabled = !hasErrors && !hasEmptyFields && updateState !is ServerViewModel.UpdateState.Loading
            ) {
                if (updateState is ServerViewModel.UpdateState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Сохранить")
                }
            }
            Button(
                onClick = onBackClick,
                modifier = Modifier
                    .width(250.dp)
                    .padding(vertical = 8.dp),
                enabled = updateState !is ServerViewModel.UpdateState.Loading
            ) {
                Text("Выйти")
            }
        }
    }
    else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Уровни не загрузились",
                //style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                //fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 24.sp
            )
        }
    }

}

@Composable
fun ClearableTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Text(
            text = label,
            fontSize = 14.sp, // Меньший размер
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(4.dp))

        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            trailingIcon = {
                if (value.isNotEmpty()) {
                    IconButton(
                        onClick = { onValueChange("") }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Очистить"
                        )
                    }
                }
            }
        )
    }
}
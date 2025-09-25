package com.example.clavtrain.ui.admin

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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


@Composable
fun AdminModeScreen(
    onViewExercises: () -> Unit,
    onViewDifficultyLevels: () -> Unit,
    onViewUserStatistics: () -> Unit,
    onViewStatisticsOfExerciseCompletion: () -> Unit,
    onExitApp: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Широкий заголовок
        Text(
            text = "Режим Администратора",
            //style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            //fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = onViewExercises,
            modifier = Modifier
                .width(250.dp)
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = "Просмотр упражнений",
                textAlign = TextAlign.Center
            )
        }
        Button(
            onClick = onViewDifficultyLevels,
            modifier = Modifier
                .width(250.dp)
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = "Просмотр уровней сложности",
                textAlign = TextAlign.Center
            )
        }
        Button(
            onClick = onViewUserStatistics,
            modifier = Modifier
                .width(250.dp)
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = "Просмотр статистики пользователей",
                textAlign = TextAlign.Center
            )
        }
        Button(
            onClick = onViewStatisticsOfExerciseCompletion,
            modifier = Modifier
                .width(250.dp)
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = "Просмотр статистики прохождения упражнений",
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = onExitApp,
            modifier = Modifier
                .width(250.dp)
                .padding(vertical = 8.dp)
        ) {
            Text("Выйти")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AdminModeScreenPreview() {
    ClavTrainTheme { AdminModeScreen(
        onViewExercises = {},
        onViewDifficultyLevels = {},
        onViewUserStatistics = {},
        onViewStatisticsOfExerciseCompletion = {},
        onExitApp = {}
    ) }
}
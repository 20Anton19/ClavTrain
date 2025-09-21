package com.example.clavtrain.ui.admin

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.clavtrain.ui.theme.ClavTrainTheme


@Composable
fun AdminModeScreen(
    onViewExercises: () -> Unit,
    onViewDifficultyLevels: () -> Unit,
    onViewUserStatistics: () -> Unit,
    onViewStatisticsOfExerciseCompletion: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(40.dp)
                .border(
                    width = 1.dp,
                    color = Color.Gray,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Это админ страница",
                modifier = Modifier
                    .padding(top = 30.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Left
            )
            OutlinedTextField(
                value = email,
                onValueChange = {email = it},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp),
                label = {Text("Value")},
                singleLine = true,
                shape = RoundedCornerShape(10.dp)
            )
            Text(
                text = "Пароль",
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Left
            )
            OutlinedTextField(
                value = password,
                onValueChange = {password = it},
                modifier = Modifier
                    .fillMaxWidth(),
                label = {Text("Value")},
                singleLine = true,
                shape = RoundedCornerShape(10.dp)
            )
            Button(
                onClick = onViewExercises,
                modifier = Modifier
                    .padding(vertical = 15.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                ),
                shape = RoundedCornerShape(7.dp)
            ) {
                Text("Войти")
            }
            Text(
                text = "Забыли пароль?",
                modifier = Modifier
                    .padding(bottom = 40.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Left
            )
        }

        Text(
            text = "*Вы новый пользователь?",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp),
            textAlign = TextAlign.Left
        )
        Button(
            onClick = onViewExercises,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black
            ),
            shape = RoundedCornerShape(7.dp)
        ) {
            Text("Регистрация")
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
        onViewStatisticsOfExerciseCompletion = {}
    ) }
}
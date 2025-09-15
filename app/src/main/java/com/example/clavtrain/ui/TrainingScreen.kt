package com.example.clavtrain.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.clavtrain.ui.theme.ClavTrainTheme

@Composable
fun TrainingScreen(onFinish: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Экран тренировки (заглушка)")
        Button(modifier = Modifier.padding(top = 16.dp), onClick = onFinish) {
            Text("Завершить")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TrainingScreenPreview() {
    ClavTrainTheme { TrainingScreen(onFinish = {}) }
} 
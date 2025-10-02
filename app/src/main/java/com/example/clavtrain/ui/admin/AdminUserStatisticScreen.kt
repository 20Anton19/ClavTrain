package com.example.clavtrain.ui.admin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun AdminUserStatisticScreen(
    userId: String,
    onViewAdminExerciseStatistic: () -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Admin User Statistic Screen")
        Button(
            onClick = onViewAdminExerciseStatistic
        ) {
            Text("View Exercise Statistics")
        }
        Button(
            onClick = onBackClick
        ) {
            Text("Back")
        }
    }
}
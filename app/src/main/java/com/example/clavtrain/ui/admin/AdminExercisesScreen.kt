package com.example.clavtrain.ui.admin

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clavtrain.data.db.Exercise
import com.example.clavtrain.ui.theme.ClavTrainTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.androidx.compose.koinViewModel

@Composable
fun AdminExercisesScreen(
    onEditExerciseScreen: (Int) -> Unit,
    onBackClick: () -> Unit,
    serverViewModel: ServerViewModel = koinViewModel()
) {
    val exercises by serverViewModel.allExercises.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Управление упражнениями",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            textAlign = TextAlign.Center,
            fontSize = 24.sp
        )

        LazyColumn {
            items(exercises) { exercise ->
                AdminExerciseCard(
                    exercise = exercise,
                    onEditClick = { onEditExerciseScreen(exercise.id ?: 0) }, //
                    onDeleteClick = { serverViewModel.deleteExercise(exercise.id ?: 0) }
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))
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

@Composable
fun AdminExerciseCard(
    exercise: Exercise,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        onClick = { /* можно оставить пустым или добавить просмотр */ },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xffe6d9e8)
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(vertical = 4.dp)
            .width(350.dp)
            .height(50.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = exercise.name,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp),
                fontSize = 17.sp,
                textAlign = TextAlign.Start
            )

            // Иконка настроек
            IconButton(
                onClick = onEditClick,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Редактировать упражнение",
                    tint = Color.Gray
                )
            }

            // Иконка удаления
            IconButton(
                onClick = onDeleteClick,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Удалить упражнение",
                    tint = Color.Red
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AdminExercisesScreenPreview() {
    ClavTrainTheme { AdminExercisesScreen(
        onEditExerciseScreen = {},
        onBackClick = {}
    ) }
}
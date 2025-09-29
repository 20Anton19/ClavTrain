package com.example.clavtrain.ui.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clavtrain.data.db.DataBaseViewModel
import com.example.clavtrain.ui.theme.ClavTrainTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun UserExercisesScreen(
    levelIndex: Int,  // ← получаем индекс
    onStartTraining: () -> Unit,
    onBackClick: () -> Unit,
    dataBaseViewModel: DataBaseViewModel = koinViewModel()
) {
    val exercisesAmount = 3// Здесь будет загрузка из бд и длина массива мб
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Список упражнений $levelIndex",
            //style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            //fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = 24.sp
        )
        LazyColumn {
            items(exercisesAmount) { item ->
                Button(
                    onClick = onStartTraining,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xffe6d9e8),
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .width(350.dp)
                        .height(50.dp)
                ) {
                    Text(
                        text = "Упражнение ${item+1}",
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 17.sp,
                        textAlign = TextAlign.Start
                    )
                }
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

//@Preview(showBackground = true)
//@Composable
//private fun UserExercisesPreview() {
//    ClavTrainTheme { UserExercisesScreen(onStartTraining = {}, onBackClick = {}) }
//}
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clavtrain.data.db.DataBaseViewModel
import com.example.clavtrain.ui.theme.ClavTrainTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun UserDifficultyScreen(
    onViewExcercises: (Int) -> Unit,
    onBackClick: () -> Unit,
    dataBaseViewModel: DataBaseViewModel = koinViewModel()
) {
    val levels by dataBaseViewModel.difficultyLevels.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Выбор сложности",
            //style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            //fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = 24.sp
        )
        LazyColumn {
            items(levels) { level ->
                Button(
                    onClick = {
                        val index = levels.indexOf(level)
                        onViewExcercises(index)  // ← передаем позицию в списке
                    },
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
                        text = level.name,
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

@Preview(showBackground = true)
@Composable
private fun UserDifficultyPreview() {
    ClavTrainTheme { UserDifficultyScreen(onViewExcercises = {}, onBackClick = {}) }
}
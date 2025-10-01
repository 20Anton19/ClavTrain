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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.androidx.compose.koinViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import com.example.clavtrain.data.db.DifficultyLevel

@Composable
fun AdminDifficultyScreen(
    onEditDifficultyScreen: () -> Unit,
    onBackClick: () -> Unit,
    serverViewModel: ServerViewModel = koinViewModel()
) {
    val levels by serverViewModel.difficultyLevels.collectAsState(initial = emptyList())
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
                Card(
                    onClick = { onEditDifficultyScreen() },
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
                            text = level.name,
                            modifier = Modifier.padding(start = 16.dp),
                            fontSize = 17.sp,
                            textAlign = TextAlign.Start
                        )

                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Редактировать",
                            tint = Color.Gray,
                            modifier = Modifier.padding(end = 16.dp)
                        )
                    }
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


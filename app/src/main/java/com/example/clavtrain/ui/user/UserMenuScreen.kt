package com.example.clavtrain.ui.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clavtrain.ui.theme.ClavTrainTheme

@Composable
fun UserMenuScreen(
    onViewDifficulty: () -> Unit,
    onViewUserLK: () -> Unit,
    onViewInfo: () -> Unit,
    onViewAboutDevelopers: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Меню",
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
            onClick = onViewDifficulty,
            modifier = Modifier
                .width(250.dp)
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = "Начать учиться",
                textAlign = TextAlign.Center
            )
        }
        Button(
            onClick = onViewUserLK,
            modifier = Modifier
                .width(250.dp)
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = "Личный кабинет",
                textAlign = TextAlign.Center
            )
        }
        Button(
            onClick = onViewInfo,
            modifier = Modifier
                .width(250.dp)
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = "Справка",
                textAlign = TextAlign.Center
            )
        }
        Button(
            onClick = onViewAboutDevelopers,
            modifier = Modifier
                .width(250.dp)
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = "О разработчиках",
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {},
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
    ClavTrainTheme { UserMenuScreen(
        onViewDifficulty = {},
        onViewUserLK = {},
        onViewInfo = {},
        onViewAboutDevelopers = {}
    ) }
}
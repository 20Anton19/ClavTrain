package com.example.clavtrain.ui.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clavtrain.ui.theme.ClavTrainTheme

@Composable
fun AboutDevelopersScreen(
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Справочная информация",
            //style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            //fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.weight(1f))
        Card(
            modifier = Modifier
                .padding(vertical = 4.dp)
                .width(350.dp)
                .wrapContentHeight(),
            colors = CardDefaults.cardColors(containerColor = Color(0xffe6d9e8))
        ) {
            Text(
                text = "Самарский университет \nКафедра программных систем \nКурсовой проект по дисциплине \n\"Программная инженерия\"",
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 15.sp
            )
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                text = "Разработчики - студенты группы 6302 \nНикитенко М.Ю. \nЛебедев А.С. \nОсипов М.А.",
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 15.sp
            )
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                text = "Самара, 2025",
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 15.sp
            )
        }
        Spacer(modifier = Modifier.weight(3f))
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
private fun AboutDevelopersScreenPreview() {
    ClavTrainTheme { AboutDevelopersScreen(
        onBackClick = {}
    ) }
}
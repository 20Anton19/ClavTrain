package com.example.clavtrain.ui.user

import androidx.compose.foundation.layout.Box
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
fun InfoScreen(
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Справочная информация о том, как пользоваться приложением",
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
                text = "Для того, чтобы начать выполнение упражнения,\n" +
                        "нажмите кнопку \"Начать учиться\".\n" +
                        "Далее выберите уровень сложности и\n" +
                        "упражнение. На открывшемся экране\n" +
                        "вводите предложенный текст. После того как\n" +
                        "весь текст будет введен, либо после превышения\n" +
                        "допустимого количества ошибок, вы будете\n" +
                        "пернаправлены в меню.\n" +
                        "Чтобы посмотреть статистику по упражнениям\n" +
                        "перейдите в личный кабинет из меню.",
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
private fun InfoScreenPreview() {
    ClavTrainTheme { InfoScreen(
        onBackClick = {}
    ) }
}
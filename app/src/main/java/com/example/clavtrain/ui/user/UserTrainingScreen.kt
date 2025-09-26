package com.example.clavtrain.ui.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clavtrain.ui.theme.ClavTrainTheme

@Composable
fun UserTrainingScreen(
    onViewStatistics: () -> Unit,
    onBackClick: () -> Unit
) {
    var presentLength by remember { mutableIntStateOf(0) }
    var presentMistakes by remember { mutableIntStateOf(0) }
    var presentTime by remember { mutableLongStateOf(0L) }

    var userInput by remember { mutableStateOf("") }
    val fullText = "adfgsdfhgfssdagasarg"

    // Проверяем совпадение на каждом символе
    val correctPart = fullText.take(userInput.length)
    val isCorrect = correctPart == userInput

    // Оставшийся текст (подсказка)
    val remainingText = fullText.drop(userInput.length)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        ) {
            Card(
                modifier = Modifier
                    .weight(3f)
                    .fillMaxHeight()
                    .padding(5.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xffe6d9e8))
            ) {
                Text(
                    text = "Длина",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
            }

            Card(
                modifier = Modifier
                    .weight(5f)
                    .fillMaxHeight()
                    .padding(5.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xffe6d9e8))
            ) {
                Text(
                    text = "Кол. ошибок",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
            }

            Card(
                modifier = Modifier
                    .weight(3f)
                    .fillMaxHeight()
                    .padding(5.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xffe6d9e8))
            ) {
                Text(
                    text = "Время",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
            }
        }
        // Введенный текст (черный)
        Text(
            text = userInput,
            color = if (isCorrect) Color.Black else Color.Red,
            fontSize = 24.sp
        )

        // Подсказка (серый) - уменьшается по мере ввода
        Text(
            text = remainingText,
            color = Color.Gray,
            fontSize = 24.sp
        )

        // Скрытое поле ввода
        OutlinedTextField(
            value = userInput,
            onValueChange = { newText ->
                if (newText.length <= fullText.length) {
                    userInput = newText
                }
            }
        )
        Text(
            text = "Вводите текст",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            textAlign = TextAlign.Center,
            fontSize = 24.sp
        )
//        Text(
//            text = mainAlg(50,10,10),
//            modifier = Modifier
//                .width(300.dp)
//                .padding(vertical = 24.dp),
//            textAlign = TextAlign.Center,
//            fontSize = 24.sp
//        )
    }
}


private fun mainAlg(
    simbolsAmount: Int,
    maxMistakes: Int,
    maxPressTime: Int
): String {
    var text = "";
    repeat (simbolsAmount) {
        val randomLetter = ('А'..'Я').random()
        text+=randomLetter
    }
    return text
}






@Preview(showBackground = true)
@Composable
private fun UserTrainingScreenPreview() {
    ClavTrainTheme { UserTrainingScreen(onViewStatistics = {}, onBackClick = {}) }
}
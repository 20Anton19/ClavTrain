package com.example.clavtrain.ui.user

import android.content.ContentValues.TAG
import android.util.Log
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clavtrain.data.db.DataBaseViewModel
import com.example.clavtrain.data.db.Exercise
import com.example.clavtrain.ui.theme.ClavTrainTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun UserTrainingScreen(
    onViewStatistics: () -> Unit,
    onBackClick: () -> Unit
) {
    var presentLength by remember { mutableIntStateOf(0) }
    var presentMistakes by remember { mutableIntStateOf(0) }
    var presentTime by remember { mutableLongStateOf(0L) }


    var userInput by remember { mutableStateOf("") }
    val fullText = "adfg"

    val correctPart = fullText.take(userInput.length)
    val isCorrect = correctPart == userInput
    Log.d("IsCorrect", "correctPart: $correctPart, userInput: $userInput, isCorrect: $isCorrect")

    var isCompleted by remember { mutableStateOf(false) }
    val remainingText = fullText.drop(userInput.length)

    // Фокус для клавиатуры
    val focusRequester = remember { FocusRequester() }
    // Автофокус при открытии
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    //Проверка БД
    val viewModel: DataBaseViewModel = koinViewModel()
    LaunchedEffect(isCompleted) {
        viewModel.insertExercise(Exercise(10,"Упражнение1", 20))
    }

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
                Text(
                    text = presentLength.toString(),
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
                Text(
                    text = presentMistakes.toString(),
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
        Text(
            text = "Вводите текст",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            textAlign = TextAlign.Center,
            fontSize = 24.sp
        )
        Text(
            text = isCompleted.toString(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            textAlign = TextAlign.Center,
            fontSize = 24.sp
        )
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
        BasicTextField(
            value = userInput,
            onValueChange = { newText ->
                if (newText.length <= fullText.length) {
                    val tempIsCorrect = fullText.take(newText.length) == newText
                    val isAddingCharacter = newText.length > userInput.length

                    userInput = newText
                    presentLength = newText.length

                    if (!tempIsCorrect and isAddingCharacter) {
                        presentMistakes+=1
                        Log.d("IsCorrect", "Не корректно, ошибок: $presentMistakes")
                    }

                    if ((newText.length == fullText.length) and tempIsCorrect) {
                        isCompleted = true // а зачем мне оно если я ухожу все равно
                        onViewStatistics()
                    }
                }

            },
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .alpha(0.01f)
                .focusRequester(focusRequester)
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = onBackClick,
            modifier = Modifier
                .width(250.dp)
                .padding(vertical = 8.dp)
        ) {
            Text("Выйти")
        }
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
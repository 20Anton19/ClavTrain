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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.clavtrain.data.db.DataBaseViewModel
import com.example.clavtrain.data.db.Exercise
import com.example.clavtrain.ui.RegisterLKViewModel
import com.example.clavtrain.ui.theme.ClavTrainTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun UserTrainingScreen(
    exerciseId: Int,
    onViewStatistics: () -> Unit,
    onBackClick: () -> Unit,
    dataBaseViewModel: DataBaseViewModel = koinViewModel(),
    userTrainingViewModel: UserTrainingViewModel = koinViewModel()
) {
    val userInput by userTrainingViewModel.userInput.collectAsStateWithLifecycle()
    val isCompleted by userTrainingViewModel.isCompleted.collectAsStateWithLifecycle()
    val isCorrect by userTrainingViewModel.isCorrect.collectAsStateWithLifecycle()
    val presentLength by userTrainingViewModel.presentLength.collectAsStateWithLifecycle()
    val presentMistakes by userTrainingViewModel.presentMistakes.collectAsStateWithLifecycle()
    val presentTime by userTrainingViewModel.presentTime.collectAsStateWithLifecycle()
    val remainingText by userTrainingViewModel.remainingText.collectAsStateWithLifecycle()


    // Фокус для клавиатуры
    val focusRequester = remember { FocusRequester() }
    // Автофокус при открытии
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    //Проверка БД
    LaunchedEffect(isCompleted) {
        if (isCompleted) {
            //dataBaseViewModel.insertExercise(Exercise(17,"Упражнение1", 20))
            onViewStatistics()
        }
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
            text = userInput,
            color = if (isCorrect) Color.Black else Color.Red,
            fontSize = 24.sp
        )
        Text(
            text = remainingText,
            color = Color.Gray,
            fontSize = 24.sp
        )

        // Скрытое поле ввода
        BasicTextField(
            value = userInput,
            onValueChange = { newText ->
                userTrainingViewModel.everyTextChange(newText)
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
    }
}

// Это жолжно быть не зесь а у админа в создании упражнения
//private fun mainAlg(
//    simbolsAmount: Int,
//    maxMistakes: Int,
//    maxPressTime: Long
//): String {
//    var text = "";
//    repeat (simbolsAmount) {
//        val randomLetter = ('А'..'Я').random()
//        text+=randomLetter
//    }
//    return text
//}






//@Preview(showBackground = true)
//@Composable
//private fun UserTrainingScreenPreview() {
//    ClavTrainTheme { UserTrainingScreen(onViewStatistics = {}, onBackClick = {}) }
//}
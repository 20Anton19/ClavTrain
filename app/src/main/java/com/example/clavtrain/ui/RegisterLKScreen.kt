package com.example.clavtrain.ui

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.clavtrain.ui.theme.ClavTrainTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterLKScreen(
    onContinueClick: () -> Unit,
    viewModel: RegisterLKViewModel = koinViewModel()
) {
    var firstName by remember { mutableStateOf("") }
    var middleName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }

    val registerState by viewModel.registerState.collectAsStateWithLifecycle()

    LaunchedEffect(registerState) {
        if (registerState is RegisterLKViewModel.RegisterState.Success) {
            onContinueClick()
        }
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "Клавиатурный тренажёр",
            fontSize = 25.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp),
            textAlign = TextAlign.Center
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Создайте новый аккаунт",
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                textAlign = TextAlign.Center
            )
            OutlinedTextField(
                value = firstName,
                onValueChange = {firstName = it},
                modifier = Modifier
                    .fillMaxWidth(),
                label = {Text("Фамилия")},
                singleLine = true,
                shape = RoundedCornerShape(10.dp)
            )
            OutlinedTextField(
                value = middleName,
                onValueChange = {middleName = it},
                modifier = Modifier
                    .fillMaxWidth(),
                label = {Text("Имя")},
                singleLine = true,
                shape = RoundedCornerShape(10.dp)
            )
            OutlinedTextField(
                value = lastName,
                onValueChange = {lastName = it},
                modifier = Modifier
                    .fillMaxWidth(),
                label = {Text("Отчество")},
                singleLine = true,
                shape = RoundedCornerShape(10.dp)
            )
            OutlinedTextField(
                value = email,
                onValueChange = {email = it},
                modifier = Modifier
                    .fillMaxWidth(),
                label = {Text("Emal")},
                singleLine = true,
                shape = RoundedCornerShape(10.dp)
            )
            OutlinedTextField(
                value = password,
                onValueChange = {password = it},
                modifier = Modifier
                    .fillMaxWidth(),
                label = {Text("Пароль")},
                singleLine = true,
                shape = RoundedCornerShape(10.dp)
            )
            OutlinedTextField(
                value = repeatPassword,
                onValueChange = {repeatPassword = it},
                modifier = Modifier
                    .fillMaxWidth(),
                label = {Text("Повторить пароль")},
                singleLine = true,
                shape = RoundedCornerShape(10.dp)
            )
            Button(
                onClick = //onContinueClick
                    {
                        Log.d("MyRagisterCheck", "Кликнул")
                        viewModel.signUp(email, password)
                    },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),// На глаз пока!!!!!!!!!!!!!!!!
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                ),
                shape = RoundedCornerShape(7.dp)
            ) {
                Text("Продолжить")
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun RegisterLKScreenPreview() {
    ClavTrainTheme { RegisterLKScreen(onContinueClick = {}) }
}
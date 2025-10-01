package com.example.clavtrain.ui

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
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
            // Показываем успешное сообщение
            if (registerState is RegisterLKViewModel.RegisterState.Success) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    onClick = { /* Можно добавить обработчик скрытия */ },
                    colors = CardDefaults.cardColors(containerColor = Color.Green.copy(alpha = 0.1f))
                ) {
                    Text(
                        text = "Регистрация успешна!",
                        modifier = Modifier.padding(16.dp),
                        color = Color.Green
                    )
                }
            }

            // Показываем ошибку
            if (registerState is RegisterLKViewModel.RegisterState.Error) {
                val errorMessage = (registerState as RegisterLKViewModel.RegisterState.Error).message
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.Red.copy(alpha = 0.1f))
                ) {
                    Text(
                        text = errorMessage,
                        modifier = Modifier.padding(16.dp),
                        color = Color.Red
                    )
                }
            }

            // Показываем загрузку
            if (registerState is RegisterLKViewModel.RegisterState.Loading) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.Blue.copy(alpha = 0.1f))
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Регистрация...",
                            color = Color.Blue
                        )
                    }
                }
            }
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
                onValueChange = {
                    if (it.length <= 20) firstName = it
                },
                modifier = Modifier
                    .fillMaxWidth(),
                label = { Text("Фамилия") },
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                isError = firstName.length > 20
            )
            if (firstName.length > 20) {
                Text(
                    text = "Максимум 20 символов",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.Start)
                )
            }

            OutlinedTextField(
                value = middleName,
                onValueChange = {
                    if (it.length <= 20) middleName = it
                },
                modifier = Modifier
                    .fillMaxWidth(),
                label = { Text("Имя") },
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                isError = middleName.length > 20
            )
            if (middleName.length > 20) {
                Text(
                    text = "Максимум 20 символов",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.Start)
                )
            }

            OutlinedTextField(
                value = lastName,
                onValueChange = {
                    if (it.length <= 20) lastName = it
                },
                modifier = Modifier
                    .fillMaxWidth(),
                label = { Text("Отчество") },
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                isError = lastName.length > 20
            )
            if (lastName.length > 20) {
                Text(
                    text = "Максимум 20 символов",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.Start)
                )
            }

            OutlinedTextField(
                value = email,
                onValueChange = {
                    if (it.length <= 15) email = it
                },
                modifier = Modifier
                    .fillMaxWidth(),
                label = { Text("Email (макс. 15 символов)") },
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                isError = email.length > 15
            )
            if (email.length > 15) {
                Text(
                    text = "Максимум 15 символов",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.Start)
                )
            }

            OutlinedTextField(
                value = password,
                onValueChange = {
                    if (it.length <= 10) password = it
                },
                modifier = Modifier
                    .fillMaxWidth(),
                label = { Text("Пароль (4-10 символов)") },
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                visualTransformation = PasswordVisualTransformation(),
                isError = password.isNotEmpty() && (password.length < 4 || password.length > 10)
            )
            if (password.isNotEmpty() && (password.length < 4 || password.length > 10)) {
                Text(
                    text = "Пароль должен быть от 4 до 10 символов",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.Start)
                )
            }

            OutlinedTextField(
                value = repeatPassword,
                onValueChange = {
                    if (it.length <= 10) repeatPassword = it
                },
                modifier = Modifier
                    .fillMaxWidth(),
                label = { Text("Повторить пароль") },
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                visualTransformation = PasswordVisualTransformation(),
                isError = repeatPassword.isNotEmpty() && (repeatPassword.length < 4 || repeatPassword.length > 10)
            )
            if (repeatPassword.isNotEmpty() && (repeatPassword.length < 4 || repeatPassword.length > 10)) {
                Text(
                    text = "Пароль должен быть от 4 до 10 символов",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.Start)
                )
            }
            Button(
                onClick = //onContinueClick
                    {
                        Log.d("MyRagisterCheck", "Кликнул")
                        // Базовая валидация перед отправкой
                        if (email.isEmpty() || firstName.isEmpty() || middleName.isEmpty() || lastName.isEmpty()) {
                            viewModel.setError("Все поля должны быть заполнены")
                            return@Button
                        }
                        if (email.length > 15) {
                            viewModel.setError("Email не должен превышать 15 символов")
                            return@Button
                        }
                        if (password.length < 4 || password.length > 10) {
                            viewModel.setError("Пароль должен быть от 4 до 10 символов")
                            return@Button
                        }
                        if (password != repeatPassword) {
                            viewModel.setError("Пароли не совпадают")
                            return@Button
                        }
                        if (firstName.length > 20 || middleName.length > 20 || lastName.length > 20) {
                            viewModel.setError("ФИО не должно превышать 20 символов")
                            return@Button
                        }
                        viewModel.signUp(email, password, firstName, middleName, lastName)
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
package com.example.clavtrain.ui.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clavtrain.data.db.DataBaseViewModel
import com.example.clavtrain.ui.theme.ClavTrainTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun ChangePasswordScreen(
    onBackClick: () -> Unit,
    dataBaseViewModel: DataBaseViewModel = koinViewModel()
) {
    val currentUser by dataBaseViewModel.currentUser.collectAsState()
    val scope = rememberCoroutineScope()

    // Локальное состояние
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var showSuccess by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Функция смены пароля
    fun changePassword() {
        if (currentUser == null) return

        // Валидация
        if (currentPassword.isBlank()) {
            errorMessage = "Введите текущий пароль"
            return
        }
        if (newPassword.isBlank()) {
            errorMessage = "Введите новый пароль"
            return
        }
        if (newPassword != confirmPassword) {
            errorMessage = "Пароли не совпадают"
            return
        }
        if (newPassword.length < 6) {
            errorMessage = "Пароль должен содержать минимум 6 символов"
            return
        }

        isLoading = true
        errorMessage = null

        scope.launch {
            try {
                val success = dataBaseViewModel.updatePassword(
                    currentPassword = currentPassword,
                    newPassword = newPassword
                )

                if (success) {
                    showSuccess = true
                    // Очищаем поля после успешного сохранения
                    currentPassword = ""
                    newPassword = ""
                    confirmPassword = ""

                    // Автоскрытие успешного сообщения
                    delay(2000)
                    showSuccess = false
                }
            } catch (e: Exception) {
                errorMessage = e.message
            } finally {
                isLoading = false
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Изменение пароля",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            textAlign = TextAlign.Center,
            fontSize = 24.sp
        )

        // Показываем загрузку
        if (isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }

        // Показываем успешное сообщение
        if (showSuccess) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                onClick = { showSuccess = false },
                colors = CardDefaults.cardColors(containerColor = Color.Green.copy(alpha = 0.1f))
            ) {
                Text(
                    text = "Пароль успешно изменен!",
                    modifier = Modifier.padding(16.dp),
                    color = Color.Green
                )
            }
        }

        // Показываем ошибку
        errorMessage?.let { message ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.Red.copy(alpha = 0.1f))
            ) {
                Text(
                    text = message,
                    modifier = Modifier.padding(16.dp),
                    color = Color.Red
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Поле текущего пароля
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Текущий пароль:",
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight.Bold
                )

                TextField(
                    value = currentPassword,
                    onValueChange = { currentPassword = it },
                    modifier = Modifier.weight(2f),
                    singleLine = true,
                    enabled = !isLoading,
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    placeholder = { Text("Введите текущий пароль") }
                )

                IconButton(
                    onClick = { isPasswordVisible = !isPasswordVisible }
                ) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = if (isPasswordVisible) "Скрыть пароль" else "Показать пароль"
                    )
                }
            }
        }

        // Поле нового пароля
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Новый пароль:",
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight.Bold
                )

                TextField(
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    modifier = Modifier.weight(2f),
                    singleLine = true,
                    enabled = !isLoading,
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    placeholder = { Text("Введите новый пароль") }
                )

                IconButton(
                    onClick = { isPasswordVisible = !isPasswordVisible }
                ) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = if (isPasswordVisible) "Скрыть пароль" else "Показать пароль"
                    )
                }
            }
        }

        // Подтверждение пароля
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Повторите пароль:",
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight.Bold
                )

                TextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    modifier = Modifier.weight(2f),
                    singleLine = true,
                    enabled = !isLoading,
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    placeholder = { Text("Повторите новый пароль") }
                )

                IconButton(
                    onClick = { isPasswordVisible = !isPasswordVisible }
                ) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = if (isPasswordVisible) "Скрыть пароль" else "Показать пароль"
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { changePassword() },
            modifier = Modifier
                .width(250.dp)
                .padding(vertical = 4.dp),
            enabled = !isLoading && currentUser != null &&
                    currentPassword.isNotBlank() &&
                    newPassword.isNotBlank() &&
                    confirmPassword.isNotBlank()
        ) {
            Text(if (isLoading) "Сохранение..." else "Сохранить")
        }

        Button(
            onClick = onBackClick,
            modifier = Modifier
                .width(250.dp)
                .padding(vertical = 8.dp),
            enabled = !isLoading
        ) {
            Text("Назад")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChangeUserDataScreenPreview() {
    ClavTrainTheme { ChangeUserDataScreen(
        onChangePassword = {},
        onBackClick = {}
    ) }
}
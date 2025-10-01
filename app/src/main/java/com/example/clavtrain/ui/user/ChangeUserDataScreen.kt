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
import androidx.compose.runtime.LaunchedEffect
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
fun ChangeUserDataScreen(
    onChangePassword: () -> Unit,
    onBackClick: () -> Unit,
    dataBaseViewModel: DataBaseViewModel = koinViewModel()
) {
    val currentUser by dataBaseViewModel.currentUser.collectAsState()

    // Локальное состояние для редактирования
    var firstName by remember { mutableStateOf(currentUser?.firstName ?: "") }
    var middleName by remember { mutableStateOf(currentUser?.middleName ?: "") }
    var lastName by remember { mutableStateOf(currentUser?.lastName ?: "") }
    var email by remember { mutableStateOf(currentUser?.email ?: "") }

    var isLoading by remember { mutableStateOf(false) }
    var showSuccess by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope() // ← получаем scope

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Изменение данных",
            //style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            //fontWeight = FontWeight.Bold,
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
                onClick = {showSuccess = false},
                colors = CardDefaults.cardColors(containerColor = Color.Green.copy(alpha = 0.1f))
            ) {
                Text(
                    text = "Данные успешно сохранены!",
                    modifier = Modifier.padding(16.dp),
                    color = Color.Green
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
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
                    text = "Фамилия:",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(10.dp))
                TextField(
                    value = middleName,
                    onValueChange = {middleName = it},
                    modifier = Modifier.weight(2f),
                    singleLine = true,
                )

                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Фамилия"
                )
            }
        }
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
                    text = "Имя:",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(10.dp))
                TextField(
                    value = firstName,
                    onValueChange = { firstName = it},
                    modifier = Modifier.weight(2f),
                    singleLine = true,
                )

                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Имя"
                )
            }
        }
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
                    text = "Отчество:",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(10.dp))
                TextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    modifier = Modifier.weight(2f),
                    singleLine = true,
                )

                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Отчество"
                )
            }
        }
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
                    text = "Email:",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(10.dp))
                TextField(
                    value = email,
                    onValueChange = { email = it},
                    modifier = Modifier.weight(2f),
                    singleLine = true,
                )

                Icon(
                    imageVector = Icons.Filled.Email,
                    contentDescription = "Email"
                )
            }
        }
        Button(
            onClick = onChangePassword,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 16.dp)
        ) {
            Text("Изменить пароль")
        }

        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {
                if (currentUser?.id != null) {
                    isLoading = true
                    scope.launch {
                        val isUnique = dataBaseViewModel.isEmailUniqueForCurrentUser(email)
                        if (isUnique) {
                            try {
                                dataBaseViewModel.updateUserProfile(
                                    firstName = firstName,
                                    middleName = middleName,
                                    lastName = lastName,
                                    email = email
                                )
                            } catch (e: Exception) {
                                // Обработка ошибки
                            } finally {
                                isLoading = false
                                showSuccess = true
                            }
                        }
                        else {
                            isLoading = false
                        }
                    }
                }
            },
            modifier = Modifier
                .width(250.dp)
                .padding(vertical = 4.dp),
            enabled = !isLoading && currentUser != null
        ) {
            Text(if (isLoading) "Сохранение..." else "Сохранить")
        }
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
private fun ChangeUserDataScreenPreview() {
    ClavTrainTheme { ChangeUserDataScreen(
        onChangePassword = {},
        onBackClick = {}
    ) }
}
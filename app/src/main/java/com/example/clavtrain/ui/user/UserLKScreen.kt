package com.example.clavtrain.ui.user

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import org.koin.androidx.compose.koinViewModel

@Composable
fun UserLKScreen(
    onViewUserStatistic: () -> Unit,
    onChangeUserData: () -> Unit,
    onBackClick: () -> Unit,
    dataBaseViewModel: DataBaseViewModel = koinViewModel()
) {
    val currentUser by dataBaseViewModel.currentUser.collectAsState()
    var isPasswordVisible by remember { mutableStateOf(false) }
    if (currentUser!=null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Личный кабинет",
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
                    Text(
                        text = currentUser!!.middleName,
                        modifier = Modifier.weight(2f),
                        color = Color.Gray
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
                    Text(
                        text = currentUser!!.firstName,
                        modifier = Modifier.weight(2f),
                        color = Color.Gray
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
                    Text(
                        text = currentUser!!.lastName,
                        modifier = Modifier.weight(2f),
                        color = Color.Gray
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
                    Text(
                        text = currentUser!!.email,
                        modifier = Modifier.weight(2f),
                        color = Color.Gray
                    )

                    Icon(
                        imageVector = Icons.Filled.Email,
                        contentDescription = "Email"
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
                        text = "Пароль:",
                        modifier = Modifier.weight(1f),
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = if (isPasswordVisible) currentUser!!.password else "••••••••••",
                        modifier = Modifier.weight(2f),
                        fontFamily = if (isPasswordVisible) FontFamily.Default else FontFamily.Monospace
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
                onClick = onViewUserStatistic,
                modifier = Modifier
                    .width(250.dp)
                    .padding(vertical = 4.dp)
            ) {
                Text("Статистика")
            }
            Button(
                onClick = onChangeUserData,
                modifier = Modifier
                    .width(250.dp)
                    .padding(vertical = 4.dp)
            ) {
                Text("Изменить данные")
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
    else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun UserLKScreenScreenPreview() {
    ClavTrainTheme { UserLKScreen(
        onViewUserStatistic = {},
        onChangeUserData = {},
        onBackClick = {}
    ) }
}
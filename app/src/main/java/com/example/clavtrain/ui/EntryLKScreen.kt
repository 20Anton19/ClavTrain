package com.example.clavtrain.ui

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.clavtrain.ui.theme.ClavTrainTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

@Composable
fun EntryLKScreen(onLoginClick: () -> Unit, onRegisterClick: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val auth = Firebase.auth
    val db = Firebase.firestore
    db.collection("try")
        .document()
        .set(mapOf("1" to "b"))

    LaunchedEffect(Unit) {

        val currentUser = auth.currentUser
        if (currentUser != null) {
            onLoginClick()
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(40.dp)
                .border(
                    width = 1.dp,
                    color = Color.Gray,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Email",
                modifier = Modifier
                    .padding(top = 30.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Left
            )
            OutlinedTextField(
                value = email,
                onValueChange = {email = it},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp),
                label = {Text("Value")},
                singleLine = true,
                shape = RoundedCornerShape(10.dp)
            )
            Text(
                text = "Пароль",
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Left
            )
            OutlinedTextField(
                value = password,
                onValueChange = {password = it},
                modifier = Modifier
                    .fillMaxWidth(),
                label = {Text("Value")},
                singleLine = true,
                shape = RoundedCornerShape(10.dp)
            )
            Button(
                onClick = //onContinueClick
                    {
                        signIn(auth, email, password, onLoginClick)
                    },
                modifier = Modifier
                    .padding(vertical = 15.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                ),
                shape = RoundedCornerShape(7.dp)
            ) {
                Text("Войти")
            }
        }
        Text(
            text = "*Вы новый пользователь?",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp),
            textAlign = TextAlign.Left
        )
        Button(
            onClick = onRegisterClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black
            ),
            shape = RoundedCornerShape(7.dp)
        ) {
            Text("Регистрация")
        }
    }
}

private fun signIn(auth: FirebaseAuth, email: String, pass: String, onLoginClick: () -> Unit) {
    auth.signInWithEmailAndPassword(email, pass)
        .addOnCompleteListener() { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "signInWithEmail:success")
                onLoginClick()
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInWithEmail:failure", task.exception)
            }
        }
}

@Preview(showBackground = true)
@Composable
private fun EntryLKScreenPreview() {
    ClavTrainTheme { EntryLKScreen(onLoginClick = {}, onRegisterClick = {}) }
}
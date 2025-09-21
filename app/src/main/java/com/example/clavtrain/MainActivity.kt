package com.example.clavtrain

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.clavtrain.data.AppNav
import com.example.clavtrain.data.UserRole
import com.example.clavtrain.navigation.AdminNav
import com.example.clavtrain.navigation.AuthNav
import com.example.clavtrain.navigation.UserNav
import com.example.clavtrain.ui.theme.ClavTrainTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ClavTrainTheme {
                var currentNav by remember { mutableStateOf<AppNav>(AppNav.AUTH) }
                when (currentNav) {
                    AppNav.AUTH -> AuthNav(
                        onRoleDetermined = { role ->
                            currentNav = when (role) {
                                UserRole.ADMIN -> AppNav.ADMIN
                                UserRole.USER -> AppNav.USER
                                null -> AppNav.AUTH
                            }
                        }
                    )
                    AppNav.ADMIN -> AdminNav()
                    AppNav.USER -> UserNav()
                }
            }
        }
    }
}
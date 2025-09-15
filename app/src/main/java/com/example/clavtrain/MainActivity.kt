package com.example.clavtrain

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.clavtrain.navigation.AppNav
import com.example.clavtrain.ui.theme.ClavTrainTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ClavTrainTheme {
                AppNav()
            }
        }
    }
}
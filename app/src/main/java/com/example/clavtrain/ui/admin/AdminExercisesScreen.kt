package com.example.clavtrain.ui.admin

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.clavtrain.ui.theme.ClavTrainTheme

@Composable
fun AdminExercisesScreen(
    onEditExerciseScreen: () -> Unit,
    onBackClick: () -> Unit
) {

}

@Preview(showBackground = true)
@Composable
private fun AdminExercisesScreenPreview() {
    ClavTrainTheme { AdminExercisesScreen(
        onEditExerciseScreen = {},
        onBackClick = {}
    ) }
}
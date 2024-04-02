package com.example.applicationquizforstudents.presentation.ui.screens.quiz.menu

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape

@Composable
fun StartButtons(
    modifier: Modifier = Modifier,
    onStartClick: () -> Unit
) {
    Button(
        modifier = modifier.fillMaxWidth(0.8f),
        onClick = { onStartClick() },
        shape = RectangleShape
    ) {
        Text("Начать тест")
    }
}

@Composable
fun InfoButtons(
    modifier: Modifier = Modifier,
    onInfoClick: () -> Unit
) {

    Button(
        modifier = modifier.fillMaxWidth(0.8f),
        onClick = {
            onInfoClick()

        },
        ) {
        Text("Информация")
    }
}

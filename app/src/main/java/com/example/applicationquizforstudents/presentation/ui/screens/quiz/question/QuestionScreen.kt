package com.example.applicationquizforstudents.presentation.ui.screens.quiz.question

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun QuestionScreen(modifier: Modifier = Modifier,onFinisClick:()->Unit) {
    val viewModel: QuestionScreenViewModel = viewModel()
    var index by remember { mutableStateOf(0) }
    var progress by remember { mutableStateOf(0) }
    var enabledNext by remember { mutableStateOf(false) }
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProgressIndicator(viewModel.images.size, progress = progress+1)
        viewModel.getStackImages(index).forEach { image ->
            QuestionsLayout(images = image) {
                viewModel.setInactiveStateForImages(index)
                image.selected.value = true
                enabledNext = true
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
                Button(
                    enabled = index!=0,
                    onClick = { if (progress != 0) progress = --index }
                ) {
                    Text(text = "Back")
                }

            if (index == viewModel.images.size - 1) {
                Button(
                    enabled = !(viewModel.getStackImages(index).all { !it.selected.value }),
                    onClick = { onFinisClick()}
                ) {
                    Text(text = "Finish")
                }
            } else {
                Button(
                    enabled = !(viewModel.getStackImages(index).all { !it.selected.value }),
                    onClick = { progress = ++index }
                ) {
                    Text(text = "Next")
                }
            }
        }
    }
}

@Composable
fun ProgressIndicator(totalQuestions: Int, progress: Int) {
    LinearProgressIndicator(
        progress = progress.toFloat() / totalQuestions,
        modifier = Modifier.padding(vertical = 16.dp)
    )
    Text(text = "Вопросы: $progress / $totalQuestions")

}



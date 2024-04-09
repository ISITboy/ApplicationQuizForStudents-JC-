package com.example.applicationquizforstudents.presentation.ui.screens.quiz.question

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.applicationquizforstudents.domain.models.UserRole
import com.example.applicationquizforstudents.domain.state.RealtimeCRUDState
import com.example.applicationquizforstudents.presentation.ui.Utils.CURRENT_USER_ROLE
import com.example.applicationquizforstudents.presentation.ui.Utils.listResultAnswer
import com.example.applicationquizforstudents.presentation.ui.screens.common.ProgressIndicator

@Composable
fun QuestionScreen(modifier: Modifier = Modifier,onFinisClick:()->Unit, onEditImage:(Int)->Unit) {

    val viewModel: QuestionScreenViewModel = viewModel()
    var index by remember { mutableStateOf(0) }
    var progress by remember { mutableStateOf(0) }
    var enabledNext by remember { mutableStateOf(false) }
    var visibleEditImage by remember { mutableStateOf(false) }
    var count by remember {
        mutableStateOf(0)
    }

    when(viewModel.stateForImages.collectAsStateWithLifecycle().value){
        is RealtimeCRUDState.SuccessForImages->{
            val dataUser = (viewModel.stateForImages.collectAsState().value as RealtimeCRUDState.SuccessForImages).images
            if(count ==0) {
                viewModel.updateBaseImages(dataUser)
                count++
            }
        }
        is RealtimeCRUDState.Error->{

        }
        else -> {
            ProgressIndicator()
        }

    }

    when(viewModel.getSate.collectAsStateWithLifecycle().value){
        is RealtimeCRUDState.Success->{
            val dataUser = (viewModel.getSate.collectAsState().value as RealtimeCRUDState.Success).user
            CURRENT_USER_ROLE = dataUser.role
            visibleEditImage = dataUser.role == UserRole.ADMIN
        }
        is RealtimeCRUDState.Error->{

        }
        else -> {
            ProgressIndicator()
        }

    }


    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProgressIndicatorForQuiz(viewModel.images.value.size, progress = progress+1)
        viewModel.getStackImages(index)?.let{list->
            list.forEach {image->
                QuestionsLayout(
                    images = image,
                    visibleEditImage = visibleEditImage,
                    onSelectedImage = {
                        viewModel.setInactiveStateForImages(index)

                        image.selected.value = true
                        enabledNext = true
                    },
                    onEditImageClick = {imageId->
//                        onEditImage(viewModel.baseImages.value.find { it.id==imageId }?.url?:"")
                        onEditImage(viewModel.baseImages.value.find { it.id==imageId }?.id ?:0)
                    }
                )
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
            if (index == viewModel.images.value.size - 1) {
                Button(
                    enabled = !(viewModel.getStackImages(index)?.all { !it.selected.value })!!,
                    onClick = {
                        listResultAnswer = viewModel.images.value.flatten()
                        onFinisClick()
                    }
                ) {
                    Text(text = "Finish")
                }
            } else {
                Button(
                    enabled = if (viewModel.getStackImages(index) == null) false else  !(viewModel.getStackImages(index)?.all { !it.selected.value })!!,
                    onClick = { progress = ++index }
                ) {
                    Text(text = "Next")
                }
            }
        }
        if(CURRENT_USER_ROLE == UserRole.ADMIN){
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(onClick = {
                    onEditImage(-1)
                }){
                    Text("AddImage")
                }
            }
        }
    }
}

@Composable
fun ProgressIndicatorForQuiz(totalQuestions: Int, progress: Int) {
    LinearProgressIndicator(
        progress = progress.toFloat() / totalQuestions,
        modifier = Modifier.padding(vertical = 16.dp)
    )
    Text(text = "Вопросы: $progress / $totalQuestions")
}



package com.example.applicationquizforstudents.presentation.ui.screens.quiz.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.dp
import com.example.applicationquizforstudents.R
import com.example.applicationquizforstudents.presentation.ui.screens.quiz.question.QuestionScreen

@Composable
fun MenuScreen(
    modifier: Modifier=Modifier,
    onEvent:(MenuEvent)->Unit
){
    val openDialog = remember { mutableStateOf(false)  }
    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
        horizontalArrangement = Arrangement.Center
    ){
        Image(painter = painterResource(id = R.drawable.feu_logo1), contentDescription = null, contentScale = ContentScale.Crop)
    }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        StartButtons{
            onEvent(MenuEvent.ToStartTest)
        }
        Spacer(modifier = Modifier.height(10.dp))
        InfoButtons {
            openDialog.value = true
            onEvent(MenuEvent.ToShowInfo)
        }
    }

    if (openDialog.value) {

        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = "Информация")
            },
            text = {
                Text("Данный квиз поможет Вам определиться с выбором специальности на факультете экономики и управления")
            },
            confirmButton = {
                Button(
                    onClick = {
                        openDialog.value = false
                    }) {
                    Text("Close")
                }
            }
        )
    }
}

package com.example.applicationquizforstudents.presentation.ui.screens.quiz.result

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.applicationquizforstudents.R

@Composable
fun ResultScreen(
    onMenuClick:()->Unit
){
    Row(
        modifier=  Modifier.fillMaxWidth(0.6f),
        horizontalArrangement = Arrangement.Center
    ){
        Image(
            modifier = Modifier.fillMaxWidth(),
            contentScale= ContentScale.FillWidth,
            painter = painterResource(id = R.drawable.feu_logo1),
            contentDescription = null
        )
    }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text="Результат")
        Button(onClick = { onMenuClick() }) {
        }
    }
}
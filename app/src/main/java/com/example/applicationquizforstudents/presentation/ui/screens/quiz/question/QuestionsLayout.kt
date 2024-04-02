package com.example.applicationquizforstudents.presentation.ui.screens.quiz.question

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.applicationquizforstudents.domain.models.ImageModel


@Composable
fun QuestionsLayout(
    modifier: Modifier = Modifier,
    images: ImageModel,
    onSelectedImage:()->Unit
) {
        Image(
            modifier = modifier
                .size(width = 300.dp, 160.dp)
                .padding(10.dp)
                .border(width = if(images.selected.value) 2.dp else (-1).dp, color = Color.Green, shape = RectangleShape)
                .clickable{
                    onSelectedImage()
                },
            painter = painterResource(id = images.url),
            contentScale = ContentScale.FillWidth,
            contentDescription = null
        )

}
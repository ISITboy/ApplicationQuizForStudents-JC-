package com.example.applicationquizforstudents.presentation.ui.screens.quiz.question

import android.util.Log
import android.widget.ImageButton
import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import coil.compose.AsyncImage
import com.example.applicationquizforstudents.domain.models.ImageModel
import com.example.applicationquizforstudents.presentation.ui.screens.quiz.profile.EditButton


@Composable
fun QuestionsLayout(
    modifier: Modifier = Modifier,
    images: ImageModel,
    visibleEditImage:Boolean,
    onSelectedImage:()->Unit,
    onEditImageClick:(Int)->Unit
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ){
            Log.d("QuestionScreen","QuestionsLayout")
        AsyncImage(
            modifier = Modifier
                .size(width = 300.dp, 160.dp)
                .padding(10.dp)
                .border(
                    width = if (images.selected.value) 2.dp else (-1).dp,
                    color = Color.Green,
                    shape = RectangleShape
                )
                .clickable {
                    onSelectedImage()
                },
            model = images.url,
            contentScale = ContentScale.FillWidth,
            contentDescription = null
        )
        if(visibleEditImage){
            Spacer(modifier =Modifier.width(10.dp))
            Column {
                IconButton(
                    onClick = {onEditImageClick(images.id)}
                ){
                    Icon(imageVector = Icons.Default.Settings, contentDescription = null)
                }
            }
        }
    }
}
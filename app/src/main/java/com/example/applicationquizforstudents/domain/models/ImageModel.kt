package com.example.applicationquizforstudents.domain.models

import androidx.annotation.DrawableRes
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class ImageModel(
    val id :Int = 0,
    val url : String,
    val description:String,
    val selected : MutableState<Boolean> = mutableStateOf(false)
)

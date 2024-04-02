package com.example.applicationquizforstudents.presentation.ui.screens.authorization.signup

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.applicationquizforstudents.R

@Composable
fun RegistrationButton (modifier:Modifier = Modifier,onRegistration:()->Unit){
    Button(
        modifier = modifier.fillMaxWidth(0.8f),
        border = BorderStroke(1.dp, Color.Black),
        shape = RectangleShape,
        onClick = {onRegistration()}
    ) {
        Text(text = stringResource(id = R.string.header_registration_screen))
    }
}
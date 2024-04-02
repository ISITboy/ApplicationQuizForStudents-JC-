package com.example.applicationquizforstudents.presentation.ui.screens.quiz.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.applicationquizforstudents.presentation.ui.screens.authorization.components.EmailInputLayout
import com.example.applicationquizforstudents.presentation.ui.screens.authorization.components.PasswordInputLayout
import com.example.applicationquizforstudents.presentation.ui.screens.authorization.signup.NameInputLayout
import com.example.applicationquizforstudents.presentation.ui.screens.authorization.signup.SurnameInputLayout

@Composable
fun ProfileScreen(modifier: Modifier=Modifier, onEvent:(ProfileState)->Unit){
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val email = remember { mutableStateOf("email@mail.com") }
        val password = remember { mutableStateOf("woefjwpowe") }
        val invalidPassword = remember { mutableStateOf(false) }
        val invalidEmail = remember { mutableStateOf(false) }
        val invalidSurname = remember { mutableStateOf(false) }
        val name = remember { mutableStateOf("Михаил") }
        val surname = remember { mutableStateOf("Усик") }
        var enabled by remember { mutableStateOf(false) }
        var actionButton  by remember { mutableStateOf(true) }

        SurnameInputLayout(surname = surname, invalid = invalidSurname,enabled = enabled)
        Spacer(modifier = Modifier.height(15.dp))
        NameInputLayout(name = name,enabled = enabled)
        Spacer(modifier = Modifier.height(15.dp))
        EmailInputLayout(email = email, invalid = invalidEmail,enabled = enabled, onClear = {email.value = ""})
        Spacer(modifier = Modifier.height(15.dp))
        PasswordInputLayout(password = password, invalid = invalidPassword,enabled = enabled)
        Spacer(modifier = Modifier.height(15.dp))

        if(actionButton) EditButton(onEditClick = {
            actionButton = false
            enabled = true
            onEvent(ProfileState.EditState)
        })
        else SaveButton(onSaveClick ={
            actionButton = true
            enabled = false
            onEvent(ProfileState.SaveState)
        })
    }
}

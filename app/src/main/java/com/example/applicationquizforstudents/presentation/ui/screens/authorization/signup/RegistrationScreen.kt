package com.example.applicationquizforstudents.presentation.ui.screens.authorization.signup

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.applicationquizforstudents.R
import com.example.applicationquizforstudents.presentation.ui.screens.authorization.components.EmailInputLayout
import com.example.applicationquizforstudents.presentation.ui.screens.authorization.components.PasswordInputLayout
import com.example.applicationquizforstudents.presentation.ui.screens.authorization.components.isValidEmail
import com.example.applicationquizforstudents.presentation.ui.screens.authorization.components.isValidPassword

@Composable
fun RegistrationScreen(authData: String? = "",modifier:Modifier=Modifier){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(id = R.string.header_registration_screen), style = MaterialTheme.typography.headlineLarge)
    }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        val email = remember { mutableStateOf("${if(authData == "empty") "" else authData}") }
        val password = remember { mutableStateOf("") }
        val invalidPassword = remember { mutableStateOf(false) }
        val invalidEmail = remember { mutableStateOf(false) }
        val invalidSurname = remember { mutableStateOf(false) }
        val name = remember { mutableStateOf("") }
        val surname = remember { mutableStateOf("") }
        EmailInputLayout(email = email, invalid = invalidEmail, onClear = {email.value = "" })
        Spacer(modifier = Modifier.height(15.dp))
        PasswordInputLayout(password = password,invalid = invalidPassword)
        Spacer(modifier = Modifier.height(15.dp))
        SurnameInputLayout(surname = surname,invalid = invalidSurname)
        Spacer(modifier = Modifier.height(15.dp))
        NameInputLayout(name = name)
        Spacer(modifier = Modifier.height(15.dp))
        RegistrationButton(){
            if(isValidEmail(email.value) && isValidPassword(password.value)&& surname.value.isNotBlank()){
                Log.d("Usik", email.value)
            }else{
                if(!isValidEmail(email.value))invalidEmail.value = true
                if(!isValidPassword(password.value))invalidPassword.value= true
                if(surname.value.isBlank())invalidSurname.value = true
            }
        }
    }
}

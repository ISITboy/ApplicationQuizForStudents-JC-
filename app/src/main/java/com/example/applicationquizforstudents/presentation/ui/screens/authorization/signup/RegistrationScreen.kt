package com.example.applicationquizforstudents.presentation.ui.screens.authorization.signup

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.room.util.copy
import com.example.applicationquizforstudents.R
import com.example.applicationquizforstudents.domain.models.User
import com.example.applicationquizforstudents.domain.state.AuthResult
import com.example.applicationquizforstudents.presentation.ui.screens.authorization.components.EmailInputLayout
import com.example.applicationquizforstudents.presentation.ui.screens.authorization.components.PasswordInputLayout
import com.example.applicationquizforstudents.presentation.ui.screens.authorization.components.isValidEmail
import com.example.applicationquizforstudents.presentation.ui.screens.authorization.components.isValidPassword
import com.example.applicationquizforstudents.presentation.ui.screens.authorization.signin.SignInViewModel
import com.example.applicationquizforstudents.presentation.ui.screens.common.ProgressIndicator

@Composable
fun RegistrationScreen(
    authData: String? = "",
    modifier: Modifier = Modifier,
    viewModel: RegistrationViewModel = hiltViewModel(),
    backToAuthorizationScreen: () -> Unit
) {
    var errorMessage by remember {
        mutableStateOf("")
    }

    val email = viewModel.email.collectAsState()
    viewModel.updateEmail("${if (authData == "empty") email.value else authData}")
    val password = viewModel.password.collectAsState()
    val invalidPassword = remember { mutableStateOf(false) }
    val invalidEmail = remember { mutableStateOf(false) }
    val invalidSurname = remember { mutableStateOf(false) }
    val name = viewModel.name.collectAsState()
    val surname = viewModel.surname.collectAsState()

    when (viewModel.authState.collectAsState().value) {
        is AuthResult.Loading -> {
            ProgressIndicator()
        }

        is AuthResult.Nothing -> {}
        is AuthResult.Success -> {
            val result = viewModel.authState.collectAsState().value as AuthResult.Success
            viewModel.createUser(
                User.Base(
                    id = result.user.id,
                    email = result.user.email,
                    surname = surname.value,
                    name = viewModel.name.value,
                    password = viewModel.password.value
                )
            )
        }

        is AuthResult.Error -> {
            errorMessage =
                (viewModel.authState.collectAsState().value as AuthResult.Error).e.message.toString()
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.header_registration_screen),
            style = MaterialTheme.typography.headlineLarge
        )
    }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        EmailInputLayout(
            email = email,
            invalid = invalidEmail,
            onChangeEmail = { viewModel.updateEmail(it) })
        Spacer(modifier = Modifier.height(15.dp))
        PasswordInputLayout(
            password = password,
            invalid = invalidPassword,
            onChangePassword = { viewModel.updatePassword(it) })
        Spacer(modifier = Modifier.height(15.dp))
        SurnameInputLayout(surname = surname, invalid = invalidSurname, onChangeSurname = {viewModel.updateSurname(it)})
        Spacer(modifier = Modifier.height(15.dp))
        NameInputLayout(name = name, onChangeSurname = {viewModel.updateName(it)})
        Spacer(modifier = Modifier.height(15.dp))
        RegistrationButton() {
            if (isValidEmail(email.value) && isValidPassword(password.value) && surname.value.isNotBlank()) {
                viewModel.onSignUpClick(backToAuthorizationScreen)
            } else {
                if (!isValidEmail(email.value)) invalidEmail.value = true
                if (!isValidPassword(password.value)) invalidPassword.value = true
                if (surname.value.isBlank()) invalidSurname.value = true
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = errorMessage,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.error
        )
    }

}

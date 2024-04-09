package com.example.applicationquizforstudents.presentation.ui.screens.authorization.signin

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.internal.enableLiveLiterals
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.applicationquizforstudents.R
import com.example.applicationquizforstudents.domain.state.AuthResult
import com.example.applicationquizforstudents.presentation.ui.screens.authorization.components.EmailInputLayout
import com.example.applicationquizforstudents.presentation.ui.screens.authorization.components.PasswordInputLayout
import com.example.applicationquizforstudents.presentation.ui.screens.authorization.components.isValidEmail
import com.example.applicationquizforstudents.presentation.ui.screens.authorization.components.isValidPassword
import com.example.applicationquizforstudents.presentation.ui.screens.common.ProgressIndicator

@Composable
fun AuthorizationScreen(
    modifier : Modifier=Modifier,
    viewModel: SignInViewModel = hiltViewModel(),
    openAndPopUp: (String, String) -> Unit,
) {

    var errorMessage by remember {
        mutableStateOf("")
    }
    when (viewModel.authState.collectAsState().value) {
        is AuthResult.Loading -> {
            ProgressIndicator()
        }
        is AuthResult.Nothing, is AuthResult.Success -> {}
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
            text = stringResource(id = R.string.header_authorization_screen),
            style = MaterialTheme.typography.headlineLarge
        )
    }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val email = viewModel.email.collectAsState()
        val password = viewModel.password.collectAsState()
        val invalidPassword = remember { mutableStateOf(false) }
        val invalidEmail = remember { mutableStateOf(false) }
        EmailInputLayout(
            email = email,
            invalid = invalidEmail,
            onChangeEmail = { viewModel.updateEmail(it) })
        Spacer(modifier = Modifier.height(15.dp))
        PasswordInputLayout(
            password = password,
            invalid = invalidPassword,
            onChangePassword = { viewModel.updatePassword(it) })
        Spacer(modifier = Modifier.height(30.dp))
        SignInButton {
            if (isValidEmail(email.value) && isValidPassword(password.value)) {
                viewModel.onSignInClick(openAndPopUp)
            } else {
                if (!isValidEmail(email.value)) invalidEmail.value = true
                if (!isValidPassword(password.value)) invalidPassword.value = true
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        GoToRegistrationScreen {
            viewModel.onSignUpClick(openAndPopUp)
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = errorMessage,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.error
        )
    }
}






package com.example.applicationquizforstudents.presentation.ui.screens.quiz.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.applicationquizforstudents.domain.models.User
import com.example.applicationquizforstudents.domain.state.AuthResult
import com.example.applicationquizforstudents.domain.state.RealtimeCRUDState
import com.example.applicationquizforstudents.presentation.navgraph.ProfileDestination
import com.example.applicationquizforstudents.presentation.ui.screens.authorization.components.EmailInputLayout
import com.example.applicationquizforstudents.presentation.ui.screens.authorization.components.PasswordInputLayout
import com.example.applicationquizforstudents.presentation.ui.screens.authorization.signup.NameInputLayout
import com.example.applicationquizforstudents.presentation.ui.screens.authorization.signup.SurnameInputLayout
import com.example.applicationquizforstudents.presentation.ui.screens.common.ProgressIndicator
import com.example.applicationquizforstudents.presentation.ui.screens.quiz.components.TopAppBarLayout
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileScreenViewModel = hiltViewModel(),
    onEvent: (ProfileState) -> Unit
) {

    TopAppBarLayout(route = ProfileDestination.route) {
        viewModel.signOut()
    }

    var errorMessage by remember {
        mutableStateOf("")
    }
    val email = viewModel.email.collectAsState()
    val password = viewModel.password.collectAsState()
    val invalidPassword = remember { mutableStateOf(false) }
    val invalidEmail = remember { mutableStateOf(false) }
    val invalidSurname = remember { mutableStateOf(false) }
    val name = viewModel.name.collectAsState()
    val surname = viewModel.surname.collectAsState()
    var enabled by remember { mutableStateOf(false) }
    var actionButton by remember { mutableStateOf(true) }

    when(viewModel.getSate.collectAsState().value){
        is RealtimeCRUDState.Success->{
            val dataUser = (viewModel.getSate.collectAsState().value as RealtimeCRUDState.Success).user
            viewModel.updateEmail(dataUser.email)
            viewModel.updatePassword(dataUser.password)
            viewModel.updateName(dataUser.name)
            viewModel.updateSurname(dataUser.surname)
        }
        is RealtimeCRUDState.Error->{

        }
        else -> {
            ProgressIndicator()
        }

    }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SurnameInputLayout(
            surname = surname,
            invalid = invalidSurname,
            enabled = enabled,
            onChangeSurname = { viewModel.updateSurname(it) })
        Spacer(modifier = Modifier.height(15.dp))
        NameInputLayout(
            name = name,
            enabled = enabled,
            onChangeSurname = { viewModel.updateName(it) })
        Spacer(modifier = Modifier.height(15.dp))
        EmailInputLayout(
            email = email,
            invalid = invalidEmail,
            enabled = enabled,
            onChangeEmail = { viewModel.updateEmail(it) })
        Spacer(modifier = Modifier.height(15.dp))
        PasswordInputLayout(
            password = password,
            enabled = enabled,
            invalid = invalidPassword,
            onChangePassword = { viewModel.updatePassword(it) })
        Spacer(modifier = Modifier.height(15.dp))

        if (actionButton) EditButton(onEditClick = {
            actionButton = false
            enabled = true
            onEvent(ProfileState.EditState)
        })
        else SaveButton(onSaveClick = {
            actionButton = true
            enabled = false
            viewModel.updateUser(User.Base(
                id = viewModel.getUserId(),
                email = viewModel.email.value,
                surname = viewModel.surname.value,
                name = viewModel.name.value,
                password = viewModel.password.value
            ))
            viewModel.readUser()
        })
    }
}

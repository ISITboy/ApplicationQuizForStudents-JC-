package com.example.applicationquizforstudents.presentation.ui.screens.authorization.signin

import androidx.lifecycle.ViewModel
import com.example.applicationquizforstudents.domain.repository.AccountService
import com.example.applicationquizforstudents.presentation.navgraph.AuthorizationDestination
import com.example.applicationquizforstudents.presentation.navgraph.Destination
import com.example.applicationquizforstudents.presentation.navgraph.MenuDestination
import com.example.applicationquizforstudents.presentation.navgraph.RegistrationDestination
import com.example.applicationquizforstudents.presentation.ui.screens.NotesAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val accountService: AccountService
) : NotesAppViewModel() {
    // Backing properties to avoid state updates from other classes
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

    fun onSignInClick(openAndPopUp: (String, String) -> Unit) {
        launchCatching {
            accountService.signIn(_email.value, _password.value)
            openAndPopUp(MenuDestination.route, AuthorizationDestination.route)
        }
    }

    fun onSignUpClick(openAndPopUp: (String, String) -> Unit) {
        openAndPopUp(RegistrationDestination.route, AuthorizationDestination.route)
    }
}
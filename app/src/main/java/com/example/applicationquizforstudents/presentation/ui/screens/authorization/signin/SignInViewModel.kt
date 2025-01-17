package com.example.applicationquizforstudents.presentation.ui.screens.authorization.signin

import androidx.lifecycle.viewModelScope
import com.example.applicationquizforstudents.data.service.AccountService
import com.example.applicationquizforstudents.domain.state.AuthResult
import com.example.applicationquizforstudents.presentation.navgraph.AuthorizationDestination
import com.example.applicationquizforstudents.presentation.navgraph.MenuDestination
import com.example.applicationquizforstudents.presentation.navgraph.RegistrationDestination
import com.example.applicationquizforstudents.presentation.ui.screens.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val accountService: AccountService
) : BaseViewModel() {
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
        sendCredentials(email.value,password.value)
        viewModelScope.launch {
            authState.collect{
                if(it is AuthResult.Success)openAndPopUp(MenuDestination.route,AuthorizationDestination.route)
            }
        }
    }

    fun onSignUpClick(openAndPopUp: (String, String) -> Unit) {
        val email = this.email.value.ifBlank { "empty" }
        openAndPopUp("${RegistrationDestination.route}/${email}", AuthorizationDestination.route)
    }

    override val sendRequest: suspend (String, String) -> AuthResult =
        { email, password -> accountService.signIn(email, password) }
}
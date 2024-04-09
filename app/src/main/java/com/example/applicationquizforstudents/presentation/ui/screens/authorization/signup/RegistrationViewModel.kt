package com.example.applicationquizforstudents.presentation.ui.screens.authorization.signup

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.applicationquizforstudents.data.service.AccountService
import com.example.applicationquizforstudents.data.service.FirebaseService
import com.example.applicationquizforstudents.domain.models.User
import com.example.applicationquizforstudents.domain.state.AuthResult
import com.example.applicationquizforstudents.presentation.ui.screens.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val accountService: AccountService,
    private val firebaseService: FirebaseService
) : BaseViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _name = MutableStateFlow("")
    val name :StateFlow<String> = _name.asStateFlow()

    private val _surname = MutableStateFlow("")
    val surname: StateFlow<String> = _surname.asStateFlow()

    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

    fun updateName(newEmail: String) {
        _name.value = newEmail
    }

    fun updateSurname(newPassword: String) {
        _surname.value = newPassword
    }

    fun getState() = firebaseService.getState().asLiveData()

    fun createUser(user: User.Base) = viewModelScope.launch {
        firebaseService.createUser(user)
    }


    fun onSignUpClick(backToAuthorizationScreen: () -> Unit) {
        sendCredentials(email.value, password.value)
        viewModelScope.launch {
            authState.collect{
                if(it is AuthResult.Success) {
                    backToAuthorizationScreen()
                }
            }
        }
    }

    override val sendRequest: suspend (String, String) -> AuthResult =
        { email, password -> accountService.signUp(email, password) }
}
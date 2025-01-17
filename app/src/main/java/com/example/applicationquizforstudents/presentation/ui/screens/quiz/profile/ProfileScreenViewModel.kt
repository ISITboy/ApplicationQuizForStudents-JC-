package com.example.applicationquizforstudents.presentation.ui.screens.quiz.profile

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.applicationquizforstudents.data.service.AccountService
import com.example.applicationquizforstudents.data.service.FirebaseService
import com.example.applicationquizforstudents.domain.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val firebaseService: FirebaseService,
    private val accountService: AccountService
):ViewModel(){
    init {
        readUser()
    }

    fun signOut() =viewModelScope.launch {
        accountService.signOut()
    }
    fun getUserId() = accountService.currentUserId

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _name = MutableStateFlow("")
    val name : StateFlow<String> = _name.asStateFlow()

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

    fun readUser()=viewModelScope.launch {
        firebaseService.readUser()
    }

    val getSate = firebaseService.getState().asStateFlow()
    fun updateUser(user:User.Base) = viewModelScope.launch {
        firebaseService.updateUser(user)
    }
    fun onEvent(profileState: ProfileState){
        when(profileState){
            ProfileState.EditState->{}
            ProfileState.SaveState->{}
        }
    }
}
package com.example.applicationquizforstudents.presentation.ui.screens

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.applicationquizforstudents.domain.state.AuthResult
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    abstract val sendRequest: suspend (String, String) -> AuthResult

    private val _authState = MutableStateFlow<AuthResult>(AuthResult.Nothing)

    val authState: StateFlow<AuthResult> get() = _authState.asStateFlow()

    fun sendCredentials(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _authState.value =  AuthResult.Loading
            val result = sendRequest.invoke(email, password)
            Log.d("TTT","result =$result ")
            _authState.value =  result
        }
    }
}
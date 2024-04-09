package com.example.applicationquizforstudents.presentation

import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.applicationquizforstudents.data.service.AccountService
import com.example.applicationquizforstudents.domain.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val service: AccountService
):ViewModel() {
    private val _userState: MutableLiveData<User?> = MutableLiveData()
    val userState: LiveData<User?> = _userState

    fun getUserState() = viewModelScope.launch {
        service.currentUser.collect(){
            _userState.postValue(it)
        }
    }
}
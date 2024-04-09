package com.example.applicationquizforstudents.domain.state

import com.example.applicationquizforstudents.domain.models.User

sealed class AuthResult {

    class Success(val user: User) : AuthResult()

    class Error(val e: Exception) : AuthResult()

    object Loading : AuthResult()

    object Nothing : AuthResult()
}
package com.example.applicationquizforstudents.data.service

import com.example.applicationquizforstudents.domain.models.User
import com.example.applicationquizforstudents.domain.state.AuthResult
import kotlinx.coroutines.flow.Flow

interface AccountService {
    val currentUser: Flow<User?>
    val currentUserId: String
    fun hasUser(): Boolean
    suspend fun signIn(email: String, password: String): AuthResult
    suspend fun signUp(email: String, password: String): AuthResult
    suspend fun signOut()
    suspend fun deleteAccount()

    fun updateEmail(email:String)
    fun updatePassword(password:String)
    fun getEmail():String?
}
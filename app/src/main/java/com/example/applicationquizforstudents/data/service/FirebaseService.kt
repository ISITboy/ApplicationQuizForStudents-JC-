package com.example.applicationquizforstudents.data.service

import com.example.applicationquizforstudents.domain.models.Image
import com.example.applicationquizforstudents.domain.models.User
import com.example.applicationquizforstudents.domain.state.RealtimeCRUDState
import kotlinx.coroutines.flow.MutableStateFlow

interface FirebaseService {
    fun removeImage(id:Int)
    fun getImageById(id:Int)
    fun getStateImages():MutableStateFlow<RealtimeCRUDState>
    fun getImages()
    fun createImage(image: Image)
    fun getState(): MutableStateFlow<RealtimeCRUDState>
    suspend fun createUser(user: User.Base)
    suspend fun updateUser(user: User.Base)
    suspend fun readUser()
    fun updateImage(image: Image, id: Int)
}
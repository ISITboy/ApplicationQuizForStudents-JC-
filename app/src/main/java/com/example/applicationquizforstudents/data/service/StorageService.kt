package com.example.applicationquizforstudents.data.service

import android.net.Uri
import com.example.applicationquizforstudents.domain.models.User
import com.example.applicationquizforstudents.domain.state.UploadState
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface StorageService {
    val state: MutableStateFlow<UploadState>
    fun uploadImage(fileUri: Uri)
    fun getImages()
    fun removeImage(url:String)
}
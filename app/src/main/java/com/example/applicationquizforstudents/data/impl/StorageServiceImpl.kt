package com.example.applicationquizforstudents.data.impl

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.applicationquizforstudents.data.service.StorageService
import com.example.applicationquizforstudents.domain.state.RealtimeCRUDState
import com.example.applicationquizforstudents.domain.state.UploadState
import com.example.applicationquizforstudents.presentation.ui.screens.quiz.editimage.EditImageViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.storageMetadata
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class StorageServiceImpl @Inject constructor(
    private val firebaseStorage: FirebaseStorage
) : StorageService {

    private val storageRef = firebaseStorage.reference
    private val metadata = storageMetadata {
        contentType = "image/jpeg"
    }
    override val state = MutableStateFlow<UploadState>(UploadState.Nothing)

    override fun uploadImage(fileUri: Uri) {
        val fileReference = storageRef.child("images/${fileUri.lastPathSegment}")
        fileReference.putFile(fileUri, metadata).addOnProgressListener {
            state.value = UploadState.Loading(Pair(it.totalByteCount, it.bytesTransferred))
        }.addOnFailureListener { exception ->
            state.value = UploadState.Error(exception)
        }.addOnCompleteListener {task1->
            if (task1.isSuccessful) {
                fileReference.downloadUrl.addOnCompleteListener {task2->
                    if(task2.isSuccessful){
                        val photoUrl = task2.result.toString()
                        Log.d("TTT","photoUrl $photoUrl")
                        state.value = UploadState.Success(photoUrl)
                    }
                }
            }
        }
    }

    override fun getImages() {

    }

    override fun removeImage(url: String) {
        TODO("Not yet implemented")
    }
}
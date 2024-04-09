package com.example.applicationquizforstudents.presentation.ui.screens.quiz.editimage

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.applicationquizforstudents.data.service.FirebaseService
import com.example.applicationquizforstudents.data.service.StorageService
import com.example.applicationquizforstudents.domain.models.Image
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

@HiltViewModel
class EditImageViewModel @Inject constructor(
    private val storageService: StorageService,
    private val firebaseService: FirebaseService,
    @ApplicationContext val appContext: Context
) : ViewModel() {
    init {
        Log.d("TTT", "startViewModel")
    }

    private var startedDownloadImages = 0
    var imageModel = Image(0, "", "")

    val imageState= firebaseService.getStateImages().asStateFlow()


    fun getImageById(id: Int?) {
        if (startedDownloadImages == 0 && id != null && id !=-1) {
            Log.d("TTT", "startedDownloadImages $startedDownloadImages")
            firebaseService.getImageById(id)
            startedDownloadImages++
        }
    }
    fun removeImage(id:Int){
        firebaseService.removeImage(id)
    }

    fun getImageById(id: Int) {
        firebaseService.getImageById(id = id)
    }

    private val _photoUrl = MutableStateFlow("")
    val photoUrl: StateFlow<String> = _photoUrl.asStateFlow()

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description.asStateFlow()

    fun updatePhotoUrl(photoUrl: String) {
        _photoUrl.value = photoUrl
        Log.d("TTT", "photoUrl = ${this.photoUrl}")
    }

    fun updateDescription(description: String) {
        _description.value = description
        Log.d("TTT", "desc= ${this.description.value}")
    }

    val state = storageService.state.asStateFlow()

    fun uploadFile(fileUri: Uri) {
        storageService.uploadImage(fileUri)
    }

    fun updateImage(image: Image, id: Int) {
        firebaseService.updateImage(image, id)
    }

    fun createImage(url: String, description: String) {
        Log.d("TTQQ","url = $url")
        firebaseService.createImage(Image(url = url, description = description))
    }


}
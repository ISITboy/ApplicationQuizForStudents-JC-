package com.example.applicationquizforstudents.presentation.ui.screens.quiz.question

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.applicationquizforstudents.R
import com.example.applicationquizforstudents.data.service.FirebaseService
import com.example.applicationquizforstudents.domain.models.Image
import com.example.applicationquizforstudents.domain.models.ImageModel
import com.example.applicationquizforstudents.domain.models.UserRole
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionScreenViewModel @Inject constructor(
    private val firebaseService: FirebaseService,
):ViewModel() {
    init{
        Log.d("TTT","StartViewModel")
        readUser()
        firebaseService.getImages()
    }
    public val stateForImages = firebaseService.getStateImages().asStateFlow()


    private val _baseImages = MutableStateFlow(listOf<Image>())
    val baseImages: StateFlow<List<Image>> = _baseImages.asStateFlow()

    fun updateBaseImages(images:List<Image>){
        _baseImages.value = images
        this.images.value = baseImages.value.map { image-> ImageModel(id = image.id, description = image.description, url = image.url) } .chunked(4)
    }

    val images = MutableStateFlow(baseImages.value.map { image-> ImageModel(id = image.id,description = image.description,url = image.url) } .chunked(4))

    fun setInactiveStateForImages(index:Int){
        images.value[index].forEach {
            it.selected.value = false
        }
    }
    fun readUser()=viewModelScope.launch {
        firebaseService.readUser()
    }
    val getSate = firebaseService.getState().asStateFlow()

    fun getStackImages(index:Int):List<ImageModel>?{
        return if(images.value.isNotEmpty()){
            Log.d("TTQQ","${images.value[index].toString()}")
            images.value[index]
        }
        else{
            null
        }
    }
}
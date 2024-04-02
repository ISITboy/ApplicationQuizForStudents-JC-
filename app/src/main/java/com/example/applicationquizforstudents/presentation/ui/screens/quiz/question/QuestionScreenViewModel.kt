package com.example.applicationquizforstudents.presentation.ui.screens.quiz.question

import androidx.lifecycle.ViewModel
import com.example.applicationquizforstudents.R
import com.example.applicationquizforstudents.domain.models.ImageModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuestionScreenViewModel @Inject constructor():ViewModel() {
    val images = listOf(
        ImageModel(url = R.drawable.accounting),
        ImageModel(url = R.drawable.accounting1),
        ImageModel(url = R.drawable.economy),
        ImageModel(url = R.drawable.economy1),
        ImageModel(url = R.drawable.electronic_economy),
        ImageModel(url = R.drawable.electronic_economy1),
        ImageModel(url = R.drawable.accounting),
        ImageModel(url = R.drawable.accounting1),
        ImageModel(url = R.drawable.economy),
        ImageModel(url = R.drawable.economy1),
        ImageModel(url = R.drawable.economy1),
        ImageModel(url = R.drawable.electronic_economy),
        ImageModel(url = R.drawable.electronic_economy1)).chunked(4)

    fun setInactiveStateForImages(index:Int){
        images[index].forEach {
            it.selected.value = false
        }
    }


    fun getStackImages(index:Int) = images[index]

}
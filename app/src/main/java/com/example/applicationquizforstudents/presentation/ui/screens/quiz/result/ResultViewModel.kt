package com.example.applicationquizforstudents.presentation.ui.screens.quiz.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.applicationquizforstudents.data.service.AccountService
import com.example.applicationquizforstudents.data.service.FirebaseService
import com.example.applicationquizforstudents.domain.models.ImageModel
import com.example.applicationquizforstudents.domain.models.User
import com.example.applicationquizforstudents.presentation.ui.Utils.listResultAnswer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ResultViewModel @Inject constructor(
    private val firebaseService: FirebaseService,
    private val accountService: AccountService
):ViewModel() {
    fun getUserId() = accountService.currentUserId

    fun findResult(): Map<String, Int> {
        val groupedImages = listResultAnswer.groupBy { it.description }
        val countsByDescription = groupedImages.mapValues { (_, images) ->
            calculatePercent(images.count { it.selected.value },listResultAnswer.size)
        }
        return countsByDescription
    }
    val getSate = firebaseService.getState().asStateFlow()
    fun updateUser(user: User.Base) = viewModelScope.launch {
        firebaseService.updateUser(user)
    }


    fun calculatePercent(count:Int,fullCount:Int): Int {
        return (count*100)/2
    }
}
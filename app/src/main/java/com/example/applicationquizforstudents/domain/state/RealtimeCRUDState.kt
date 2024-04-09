package com.example.applicationquizforstudents.domain.state

import com.example.applicationquizforstudents.domain.models.Image
import com.example.applicationquizforstudents.domain.models.User

sealed interface RealtimeCRUDState {
    object Off : RealtimeCRUDState

    data class Error(val message:String) : RealtimeCRUDState
    object Loading:RealtimeCRUDState
    object Delete:RealtimeCRUDState
    object CreateImage:RealtimeCRUDState

    data class Success(val message:String = "loading...",val user: User.Base = User.Base()) : RealtimeCRUDState
    data class SuccessForImages(val images:List<Image>):RealtimeCRUDState
}
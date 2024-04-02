package com.example.applicationquizforstudents.presentation.ui.screens.quiz.profile

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor():ViewModel(){

    fun onEvent(profileState: ProfileState){
        when(profileState){
            ProfileState.EditState->{

            }
            ProfileState.SaveState->{

            }
        }
    }
}
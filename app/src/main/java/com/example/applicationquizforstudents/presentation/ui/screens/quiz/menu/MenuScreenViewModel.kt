package com.example.applicationquizforstudents.presentation.ui.screens.quiz.menu

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MenuScreenViewModel @Inject constructor(

):ViewModel() {
    fun onEvent(menuEvent:MenuEvent){
        when(menuEvent){
            MenuEvent.ToStartTest->{

            }
            MenuEvent.ToShowInfo->{
                Log.d("Usik","ToShowInfo")
            }
        }
    }
}
package com.example.applicationquizforstudents.presentation.ui.screens.splash

import com.example.applicationquizforstudents.data.service.AccountService
import com.example.applicationquizforstudents.domain.state.AuthResult
import com.example.applicationquizforstudents.presentation.navgraph.AuthorizationDestination
import com.example.applicationquizforstudents.presentation.navgraph.MenuDestination
import com.example.applicationquizforstudents.presentation.navgraph.SplashDestination
import com.example.applicationquizforstudents.presentation.ui.screens.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val accountService: AccountService
) : BaseViewModel() {

    fun onAppStart(openAndPopUp: (String, String) -> Unit) {
        if (accountService.hasUser()) openAndPopUp(MenuDestination.route, SplashDestination.route)
        else openAndPopUp(AuthorizationDestination.route, SplashDestination.route)
    }

    override val sendRequest: suspend (String, String) -> AuthResult
        get() = TODO("Not yet implemented")
}

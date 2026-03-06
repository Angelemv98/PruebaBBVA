package com.angelemv.android.pruebabbva.login.viewmodel

import androidx.lifecycle.ViewModel
import com.angelemv.android.pruebabbva.login.utils.LoginEvents
import com.angelemv.android.pruebabbva.login.utils.LoginUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class LoginViewModel() : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUIState())
    val uiState: StateFlow<LoginUIState> get() = _uiState.asStateFlow()

    fun onEvent(event: LoginEvents) {
        when (event) {
            is LoginEvents.EmailChanged -> {
                _uiState.update { it.copy(loginform = it.loginform.copy(email = event.email)) }
            }

            is LoginEvents.PasswordChanged -> {
                _uiState.update { it.copy(loginform = it.loginform.copy(password = event.password)) }
            }
            is LoginEvents.isDialogShowed -> {
                _uiState.update { it.copy(showDialog = event.showDialog) }
            }

            is LoginEvents.LoginButtonClicked -> {
                login()
            }
        }
    }


    private fun login() {
    }

}
package com.angelemv.android.pruebabbva.login.utils

data class LoginForm(
    val email: String ="",
    val password: String ="",
)

data class LoginUIState(
    val loginform: LoginForm = LoginForm(),
    val isLoading: Boolean = false,
    val errorMessages: String = "",
    val showDialog: Boolean = false
){
    val isButtonEnabled: Boolean
        get() = loginform.email.isNotBlank() && loginform.password.isNotBlank()
}
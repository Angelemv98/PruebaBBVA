package com.angelemv.android.pruebabbva.login.utils

sealed interface LoginEvents {
    data class EmailChanged(val email: String) : LoginEvents
    data class PasswordChanged(val password: String) : LoginEvents
    data class  isDialogShowed (val  showDialog: Boolean): LoginEvents
    object LoginButtonClicked : LoginEvents
}

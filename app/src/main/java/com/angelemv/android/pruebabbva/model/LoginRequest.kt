package com.angelemv.android.pruebabbva.model

data class LoginRequest(
    val username: String,
    val email: String,
    val password: String
)

data class LoginResponse(
    val name: String,
    val lastname: String,
    val id: String,
    val gender: String,
    val age: Int
)

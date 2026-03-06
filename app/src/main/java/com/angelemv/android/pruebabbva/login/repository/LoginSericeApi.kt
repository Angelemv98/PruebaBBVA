package com.angelemv.android.pruebabbva.login.repository

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginSericeApi {
    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>
}